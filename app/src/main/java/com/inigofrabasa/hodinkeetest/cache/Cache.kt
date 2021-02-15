package com.inigofrabasa.hodinkeetest.cache

import androidx.lifecycle.MutableLiveData
import com.inigofrabasa.hodinkeetest.model.ArticleEntity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Cache
@Inject constructor(){
    var articleListEntity : MutableLiveData<List<ArticleEntity>> = MutableLiveData()
}