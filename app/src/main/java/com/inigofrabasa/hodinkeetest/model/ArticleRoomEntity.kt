package com.inigofrabasa.hodinkeetest.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "posts_table")
data class ArticleRoomEntity(
    val author : String,
    val title : String,
    val description : String,
    val url : String,
    val urlToImage : String,
    val publishedAt : String,
    val content : String
){
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0

    fun transformToArticleEntity() : ArticleEntity{
        return ArticleEntity(
            source = null,
            author = this.author,
            title = this.title,
            description = this.description,
            url = this.url,
            urlToImage = this.urlToImage,
            publishedAt = this.publishedAt,
            content = this.content
        )
    }
}