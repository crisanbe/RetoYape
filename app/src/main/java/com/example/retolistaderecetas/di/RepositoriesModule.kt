package com.example.retolistaderecetas.di

import com.example.retolistaderecetas.data.repositories.CharacterRepositoryImpl
import com.example.retolistaderecetas.domain.repositories.RecipeRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class RepositoriesModule {

    @Binds
    abstract fun bindCharacterRepository(impl: CharacterRepositoryImpl): RecipeRepository
}