package com.example.rickandmorty.data.api

import com.example.rickandmorty.data.model.BaseResponse
import com.example.rickandmorty.data.model.Character
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface CartoonApiService {
    @GET("character")
    suspend fun getAllCharacters(): Response<BaseResponse>
    @GET("character/{id}")
    suspend fun getCharacterById(@Path("id") id: Int): Response<Character>
}