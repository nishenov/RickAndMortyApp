package com.example.rickandmorty.data.model


import com.google.gson.annotations.SerializedName
import java.io.Serializable


data class Info(
    @SerializedName("count")
    val count: Int,
    @SerializedName("pages")
    val pages: Int,
    @SerializedName("next")
    val next: String? = null,
    @SerializedName("prev")
    val prev: String? = null
) : Serializable