package com.inigofrabasa.hodinkeetest.model

data class ArticleView(
        val author : String,
        val title : String,
        val description : String,
        val url : String,
        val urlToImage : String,
        val publishedAt : String,
        val content : String
) : ItemBaseView() {
    override var baseName = ArticleView::javaClass.name
}