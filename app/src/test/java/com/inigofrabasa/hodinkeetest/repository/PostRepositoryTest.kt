package com.inigofrabasa.hodinkeetest.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.inigofrabasa.hodinkeetest.UnitTest
import com.inigofrabasa.hodinkeetest.api.PostsService
import com.inigofrabasa.hodinkeetest.cache.Cache
import com.inigofrabasa.hodinkeetest.database.AppDatabase
import com.inigofrabasa.hodinkeetest.model.ArticleEntity
import com.inigofrabasa.hodinkeetest.model.BaseResponse
import com.inigofrabasa.hodinkeetest.model.ItemBaseEntity
import com.inigofrabasa.hodinkeetest.utils.API_KEY
import io.mockk.coEvery
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import kotlinx.coroutines.*
import org.amshove.kluent.shouldEqual
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import retrofit2.Response

class PostRepositoryTest : UnitTest() {

    companion object {
        const val QUERY_STRING = "watches"
    }
    private lateinit var dataProviderRepository: PostsRepository.DataProvider
    private lateinit var articleListEntity : MutableLiveData<List<ItemBaseEntity>>

    @MockK private lateinit var service: PostsService
    @MockK private lateinit var cache: Cache
    @MockK private lateinit var appDatabase: AppDatabase

    @MockK private lateinit var postResponse: Response<BaseResponse>

    @Before
    fun setUp() {
        dataProviderRepository = PostsRepository.DataProvider(service, appDatabase, cache)
        articleListEntity = MutableLiveData()
    }

    @Rule @JvmField
    var rule: TestRule = InstantTaskExecutorRule()

    @Test
    fun `database is empty and should return posts list from service`() {

        val articleEntity = ArticleEntity(null, "Brad", "Selling Watches", "Description", "https://www.hodinkee.com", "", "", "")
        val baseResponse : BaseResponse? = BaseResponse("200", 1, listOf(articleEntity))

        every { postResponse.code() } returns 200
        every { postResponse.message() } returns "No error"
        every { postResponse.body() } returns baseResponse

        @Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
        every { postResponse.headers() } returns null
        every { postResponse.isSuccessful } returns true
        every { postResponse.errorBody() } returns null

        every { appDatabase.postsDao().getPosts() } returns mutableListOf()
        every { cache.articleListEntity } returns articleListEntity
        coEvery { service.getPosts(QUERY_STRING, API_KEY, 1) } returns postResponse

        val posts = dataProviderRepository.getPosts(QUERY_STRING, 1)

        //Updating observable
        runBlocking {
            delay(500L)
        }

        val articleEntityResult = ArticleEntity(null, "Brad", "Selling Watches", "Description", "https://www.hodinkee.com", "", "", "")
        val listResult : List<ItemBaseEntity> = listOf(articleEntityResult)
        posts.value shouldEqual listResult
        verify(exactly = 1) { runBlocking { service.getPosts(QUERY_STRING, API_KEY, 1) } }
    }
}