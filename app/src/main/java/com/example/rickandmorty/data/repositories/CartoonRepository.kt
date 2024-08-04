package com.example.rickandmorty.data.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.example.rickandmorty.data.api.CartoonApiService
import com.example.rickandmorty.data.model.Character
import com.example.rickandmorty.utils.Resource
import kotlinx.coroutines.Dispatchers
import okio.IOException
import retrofit2.HttpException
import javax.inject.Inject

class CartoonRepository @Inject constructor(private val apiService: CartoonApiService) {
    fun getAllCharacters(): LiveData<Resource<List<Character>>> = liveData(Dispatchers.IO) {
        emit(Resource.Loading())
        try {
            val response = apiService.getAllCharacters()
            if (response.isSuccessful && response.body() != null) {
                emit(Resource.Success(response.body()!!.characters))
            }
        } catch (e: IOException) {
            emit(Resource.Error(e.localizedMessage ?: "Unknown error"))
        } catch (e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "Unknown error"))
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "Unknown error"))
        }
    }

    fun getCharactersById(id: Int): LiveData<Resource<Character>> = liveData(Dispatchers.IO) {
        emit(Resource.Loading())
        try {
            val response = apiService.getCharacterById(id)
            if (response.isSuccessful && response.body() != null) {
                emit((Resource.Success(response.body()!!)))
            }
        } catch (e: IOException) {
            emit(Resource.Error(e.localizedMessage ?: "Unknown error"))
        } catch (e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "Unknown error"))
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "Unknown error"))
        }
    }
}