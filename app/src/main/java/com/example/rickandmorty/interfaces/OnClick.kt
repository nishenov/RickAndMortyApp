package com.example.rickandmorty.interfaces

import com.example.rickandmorty.data.model.Character

interface OnClick {
    fun onClick(character: Character)
}