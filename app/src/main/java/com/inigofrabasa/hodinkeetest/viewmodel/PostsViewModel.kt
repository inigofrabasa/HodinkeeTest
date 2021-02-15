package com.inigofrabasa.hodinkeetest.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.inigofrabasa.hodinkeetest.model.ArticleView
import com.inigofrabasa.hodinkeetest.model.ResponseHelper
import com.inigofrabasa.hodinkeetest.repository.PostsRepository
import javax.inject.Inject

class PostsViewModel
@Inject constructor(private val postsRepository: PostsRepository) : ViewModel() {

    val getHelperLiveData: LiveData<ResponseHelper>
        get() = postsRepository.getResponseHelper()

    private val posts : MutableLiveData<List<ArticleView>> = MutableLiveData()

    val postsLiveData : LiveData<List<ArticleView>>
        get() = posts

    fun getPosts(query : String){
        postsRepository.getPosts(query).observeForever { articles ->
            posts.value = articles.map { article -> article.transformToArticleView() }
        }
    }
}