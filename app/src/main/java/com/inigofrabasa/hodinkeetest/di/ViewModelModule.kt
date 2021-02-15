package com.inigofrabasa.hodinkeetest.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.inigofrabasa.hodinkeetest.viewmodel.PostsViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {
    @Binds
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelMapKey(PostsViewModel::class)
    abstract fun bindsPostsViewModel(postsViewModel: PostsViewModel): ViewModel
}