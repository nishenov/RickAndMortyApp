package com.example.rickandmorty.ui.fragments.characters

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.rickandmorty.data.repositories.CartoonRepository
import com.example.rickandmorty.data.model.Character
import com.example.rickandmorty.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CartoonViewModel @Inject constructor(private val repository: CartoonRepository) : ViewModel() {

    private val _characters = MutableLiveData<Resource<List<Character>>>()
    val characters : LiveData<Resource<List<Character>>> get() = _characters

    init {
        fetchCharacters()
    }

    private fun fetchCharacters() {
        repository.getAllCharacters().observeForever{characters->
            _characters.postValue(characters)
        }
    }

}