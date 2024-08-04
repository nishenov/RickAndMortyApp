package com.example.rickandmorty.di

import com.example.rickandmorty.data.repositories.CartoonRepository
import org.koin.dsl.module

val repositoryModule = module {
    factory {
        CartoonRepository(get())
    }
}