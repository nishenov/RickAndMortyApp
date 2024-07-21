package com.example.rickandmorty.data.api

import com.example.rickandmorty.data.model.BaseResponse
import retrofit2.Call
import retrofit2.http.GET

interface CartoonApiService {
    @GET("character")
    fun getAllCharacters(): Call<BaseResponse>
}