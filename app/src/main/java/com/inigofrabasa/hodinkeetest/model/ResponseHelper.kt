package com.inigofrabasa.hodinkeetest.model

import okhttp3.Headers
import okhttp3.ResponseBody

data class ResponseHelper(
    val code : Int,
    val message : String,
    val headers : Headers?,
    val isSuccessful : Boolean,
    val responseBody : ResponseBody?
)