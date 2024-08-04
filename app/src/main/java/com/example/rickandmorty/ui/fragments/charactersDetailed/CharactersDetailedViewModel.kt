package com.example.rickandmorty.ui.fragments.charactersDetailed

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.rickandmorty.data.model.Character
import com.example.rickandmorty.data.repositories.CartoonRepository
import com.example.rickandmorty.utils.Resource



class CharactersDetailedViewModel (
    private val repository: CartoonRepository
) : ViewModel() {

    private val _charactersDetails = MutableLiveData<Resource<Character>>()
    val charactersDetails : LiveData<Resource<Character>> get() = _charactersDetails

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error

    private var characterId: Int? = null

    fun setCharacterId(id: Int) {
        characterId = id
        fetchCharacterDetails()
    }

    private fun fetchCharacterDetails() {
        characterId?.let { id ->
            repository.getCharactersById(id).observeForever { resource ->
                    _charactersDetails.postValue(resource)
            }
        }
    }

}