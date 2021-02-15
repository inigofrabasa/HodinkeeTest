package com.inigofrabasa.hodinkeetest.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.inigofrabasa.hodinkeetest.model.ArticleRoomEntity

@Dao
interface PostsDao {
    @Query("SELECT * FROM posts_table")
    fun getPosts(): List<ArticleRoomEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(entries: List<ArticleRoomEntity>)
}