package com.inigofrabasa.hodinkeetest.model

import com.google.gson.annotations.SerializedName

data class SourceEntity(
    @SerializedName("id") val id : String?,
    @SerializedName("name") val name : String?
)