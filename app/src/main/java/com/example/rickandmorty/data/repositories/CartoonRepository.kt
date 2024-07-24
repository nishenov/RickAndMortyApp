package com.example.rickandmorty.data.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.rickandmorty.data.api.CartoonApiService
import com.example.rickandmorty.data.model.BaseResponse
import com.example.rickandmorty.data.model.Character
import com.example.rickandmorty.utils.Resource
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class CartoonRepository @Inject constructor(private val apiService: CartoonApiService) {
    fun getAllCharacters(): LiveData<Resource<List<Character>>> {
        val data = MutableLiveData<Resource<List<Character>>>()
        data.postValue(Resource.Loading())
        apiService.getAllCharacters().enqueue(object : Callback<BaseResponse> {
            override fun onResponse(p0: Call<BaseResponse>, p1: Response<BaseResponse>) {
                data.postValue(Resource.Success(p1.body()!!.characters))
            }

            override fun onFailure(p0: Call<BaseResponse>, p1: Throwable) {
                data.postValue(Resource.Error(p1.message!!))
            }

        })
        return data
    }
    fun getCharactersById(id: Int): LiveData<Resource<Character>> {
        val data = MutableLiveData<Resource<Character>>()

        data.postValue(Resource.Loading())

        apiService.getCharacterById(id).enqueue(object : Callback<Character> {
            override fun onResponse(call: Call<Character>, response: Response<Character>) {
                data.postValue(Resource.Success(response.body()!!))
            }

            override fun onFailure(call: Call<Character>, t: Throwable) {
                data.postValue(Resource.Error(t.message ?: "Unknown Error"))
            }
        })
        return data
    }
}