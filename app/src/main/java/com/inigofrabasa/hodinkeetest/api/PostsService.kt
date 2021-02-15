package com.inigofrabasa.hodinkeetest.api

import retrofit2.Retrofit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PostsService
@Inject constructor(retrofit: Retrofit) : PostsApi {
    private val newsApi by lazy { retrofit.create(PostsApi::class.java) }

    override suspend fun getPosts(query : String, apiKey : String, page : Int) = newsApi.getPosts(query, apiKey, page)
}