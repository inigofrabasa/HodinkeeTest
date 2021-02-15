package com.inigofrabasa.hodinkeetest.model

import com.google.gson.annotations.SerializedName
import com.inigofrabasa.hodinkeetest.utils.empty

data class ArticleEntity(
    @SerializedName("source") val source : SourceEntity?,
    @SerializedName("author") val author : String?,
    @SerializedName("title") val title : String?,
    @SerializedName("description") val description : String?,
    @SerializedName("url") val url : String?,
    @SerializedName("urlToImage") val urlToImage : String?,
    @SerializedName("publishedAt") val publishedAt : String?,
    @SerializedName("content") val content : String?
) : ItemBaseEntity() {
    override var baseName = ArticleEntity::javaClass.name

    fun transformToArticleView() : ArticleView{
        return ArticleView(
            author = this.author?:String.empty(),
            title = this.title?:String.empty(),
            description = this.description?:String.empty(),
            url = this.url?:String.empty(),
            urlToImage = this.urlToImage?:String.empty(),
            publishedAt = this.publishedAt?:String.empty(),
            content = this.content?:String.empty()
        )
    }

    fun transformToArticleRoomEntity() : ArticleRoomEntity{
        return ArticleRoomEntity(
            author = this.author?:String.empty(),
            title = this.title?:String.empty(),
            description = this.description?:String.empty(),
            url = this.url?:String.empty(),
            urlToImage = this.urlToImage?:String.empty(),
            publishedAt = this.publishedAt?:String.empty(),
            content = this.content?:String.empty()
        )
    }
}