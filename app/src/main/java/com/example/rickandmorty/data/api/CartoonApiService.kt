package com.example.rickandmorty.data.api

import com.example.rickandmorty.data.model.BaseResponse
import com.example.rickandmorty.data.model.Character
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface CartoonApiService {
    @GET("character")
    fun getAllCharacters(): Call<BaseResponse>
    @GET("character/{id}")
    fun getCharacterById(@Path("id") id: Int): Call<Character>
}