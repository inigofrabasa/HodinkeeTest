package com.inigofrabasa.hodinkeetest.di

import com.inigofrabasa.hodinkeetest.HodinkeeApplication
import com.inigofrabasa.hodinkeetest.views.PostsActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ApplicationModule::class, ViewModelModule::class, DatabaseModule::class])
interface ApplicationComponent {
    fun inject(application: HodinkeeApplication)
    fun inject(postsActivity: PostsActivity)
}