package com.inigofrabasa.hodinkeetest.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.inigofrabasa.hodinkeetest.model.*
import com.inigofrabasa.hodinkeetest.repository.PostsRepository
import javax.inject.Inject

class PostsViewModel
@Inject constructor(private val postsRepository: PostsRepository) : ViewModel() {

    private var articlesObserver : Observer<List<ItemBaseEntity>>

    init {
        articlesObserver = Observer{ articles ->
            val transformedArticles : MutableList<ItemBaseView> =
                    articles.map { article -> ((article as ArticleEntity).transformToArticleView()) as ItemBaseView } as MutableList<ItemBaseView>
            transformedArticles.add(LoadMoreView())
            posts.value = transformedArticles
        }
    }

    val getHelperLiveData: LiveData<ResponseHelper>
        get() = postsRepository.getResponseHelper()

    private val posts : MutableLiveData<MutableList<ItemBaseView>> = MutableLiveData()

    val postsLiveData : LiveData<MutableList<ItemBaseView>>
        get() = posts

    fun getPosts(query : String, page : Int){
        postsRepository.getPosts(query, page).observeForever(articlesObserver)
    }
}