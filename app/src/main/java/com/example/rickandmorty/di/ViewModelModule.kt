package com.example.rickandmorty.di

import com.example.rickandmorty.ui.fragments.characters.CartoonViewModel
import com.example.rickandmorty.ui.fragments.charactersDetailed.CharactersDetailedViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel {
        CartoonViewModel(get())
    }
    viewModel {
        CharactersDetailedViewModel(get())
    }
}