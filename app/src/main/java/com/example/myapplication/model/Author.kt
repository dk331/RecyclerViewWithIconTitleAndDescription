package com.example.myapplication.model

import com.google.gson.annotations.SerializedName

data class Author(

    @SerializedName("id") var id: String? = null,
    @SerializedName("author") var author: String? = null,
    @SerializedName("width") var width: Int? = null,
    @SerializedName("height") var height: Int? = null,
    @SerializedName("url") var url: String? = null,
    @SerializedName("download_url") var downloadUrl: String? = null

)