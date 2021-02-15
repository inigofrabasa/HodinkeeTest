package com.inigofrabasa.hodinkeetest.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.inigofrabasa.hodinkeetest.api.PostsService
import com.inigofrabasa.hodinkeetest.cache.Cache
import com.inigofrabasa.hodinkeetest.database.AppDatabase
import com.inigofrabasa.hodinkeetest.model.ArticleEntity
import com.inigofrabasa.hodinkeetest.model.ArticleRoomEntity
import com.inigofrabasa.hodinkeetest.model.ResponseHelper
import com.inigofrabasa.hodinkeetest.utils.API_KEY
import com.inigofrabasa.hodinkeetest.utils.STATUS_OK
import kotlinx.coroutines.*
import javax.inject.Inject

interface PostsRepository {
    fun getResponseHelper() : MutableLiveData<ResponseHelper>
    fun getPosts(query: String): LiveData<List<ArticleEntity>>

    class DataProvider
    @Inject constructor(
        private val postsService: PostsService,
        private val appDatabase : AppDatabase,
        private val cache : Cache)
    : PostsRepository{

        private var repoJob = Job()
        private var cScope = CoroutineScope(repoJob + Dispatchers.IO)

        private var responseHelper : MutableLiveData<ResponseHelper> = MutableLiveData()

        override fun getResponseHelper(): MutableLiveData<ResponseHelper> =
            responseHelper

        override fun getPosts(query: String): LiveData<List<ArticleEntity>> {

            //Fetch First in the local database, if empty fetch remote

            val listArticleRoomEntity : MutableList<ArticleRoomEntity> = mutableListOf()
            runBlocking {
                val job = cScope.async {
                    val items = appDatabase.postsDao().getPosts()
                    listArticleRoomEntity.addAll(items)
                }
                job.await()
            }

            cache.articleListEntity.value =
                listArticleRoomEntity.map { item -> item.transformToArticleEntity() }

            if(listArticleRoomEntity.isEmpty()){
                cScope.launch {
                    try {
                        val response = postsService.getPosts(query, API_KEY, 1)
                        val responseHelperValue = ResponseHelper(
                            code = response.code(),
                            message = response.message(),
                            headers = response.headers(),
                            isSuccessful = response.isSuccessful,
                            responseBody = response.errorBody())

                        responseHelper.postValue(responseHelperValue)

                        when(responseHelperValue.code){
                            STATUS_OK -> {
                                response.body()?.apply {
                                    cache.articleListEntity.postValue(this.articles)
                                    if(listArticleRoomEntity.isEmpty())
                                        insertPosts(this.articles)
                                }
                            }
                        }

                    } catch (t: Throwable) {
                        t.printStackTrace()
                    }
                }
            }

            return cache.articleListEntity
        }

        private suspend fun insertPosts(posts: List<ArticleEntity>) {
            val transformedPosts = posts.map { post -> post.transformToArticleRoomEntity() }
            withContext(Dispatchers.IO) {
                appDatabase.postsDao().insertAll(transformedPosts)
            }
        }
    }
}