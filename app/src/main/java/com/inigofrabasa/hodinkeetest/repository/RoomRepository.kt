package com.inigofrabasa.hodinkeetest.repository

import androidx.lifecycle.LiveData
import com.inigofrabasa.hodinkeetest.database.AppDatabase
import com.inigofrabasa.hodinkeetest.model.ArticleRoomEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

/*
interface RoomRepository {

    fun getPosts() : LiveData<List<ArticleRoomEntity>>
    suspend fun insertAll(entries: List<ArticleRoomEntity>)

    class DataProvider
    @Inject constructor(private val appDatabase : AppDatabase)
        : RoomRepository {
        override fun getPosts(): LiveData<List<ArticleRoomEntity>> {
            return appDatabase.postsDao().getPosts()
        }

        override suspend fun insertAll(entries: List<ArticleRoomEntity>) {
            withContext(Dispatchers.IO) {
                appDatabase.postsDao().insertAll(entries)
            }
        }
    }
}*/