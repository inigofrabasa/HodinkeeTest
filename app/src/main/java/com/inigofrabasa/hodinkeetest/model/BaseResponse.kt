package com.inigofrabasa.hodinkeetest.model

import com.google.gson.annotations.SerializedName

data class BaseResponse (
    @SerializedName("status") val status : String,
    @SerializedName("totalResults") val totalResults : Int,
    @SerializedName("articles") val articles : List<ArticleEntity>
)