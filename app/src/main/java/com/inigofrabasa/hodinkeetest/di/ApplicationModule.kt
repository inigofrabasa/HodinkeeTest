package com.inigofrabasa.hodinkeetest.di

import android.app.Application
import com.inigofrabasa.hodinkeetest.HodinkeeApplication
import com.inigofrabasa.hodinkeetest.repository.PostsRepository
import com.inigofrabasa.hodinkeetest.utils.API_BASE_URL
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class ApplicationModule(private val application: HodinkeeApplication) {

    @Provides
    @Singleton
    fun provideApplication(): Application = application

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
                .baseUrl(API_BASE_URL)
                .client(createClient())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .build()
    }

    private fun createClient(): OkHttpClient {
        val okHttpClientBuilder: OkHttpClient.Builder = OkHttpClient.Builder()
        return okHttpClientBuilder.build()
    }

    @Provides
    @Singleton
    fun providePostsRepository(dataSource: PostsRepository.DataProvider): PostsRepository = dataSource
}