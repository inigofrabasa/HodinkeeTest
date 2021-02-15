package com.inigofrabasa.hodinkeetest.api

import com.inigofrabasa.hodinkeetest.model.BaseResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

internal interface PostsApi {
    companion object {
        private const val GET_EVERYTHING = "everything"
        private const val QUERY = "q"
        private const val API_KEY = "apiKey"
        private const val PAGE = "page"
    }

    @GET(GET_EVERYTHING)
    suspend fun getPosts(@Query(QUERY) query : String, @Query(API_KEY) apiKey : String, @Query(PAGE) page : Int): Response<BaseResponse>
}