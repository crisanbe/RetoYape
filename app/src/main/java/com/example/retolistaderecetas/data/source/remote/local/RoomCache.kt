package com.example.retolistaderecetas.data.source.remote.local

import com.example.retolistaderecetas.data.source.remote.local.daos.RecipesDao
import com.example.retolistaderecetas.data.source.remote.local.model.characters.CachedRecipes
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RoomCache @Inject constructor(
    private val charactersDao: RecipesDao
): Cache {
    override fun getRecipes(input: String, limit: Int, offset: Int): Flow<List<CachedRecipes>> {
        return charactersDao.getRecipes(input, limit, offset)
    }

    override suspend fun storeRecipes(recipes: List<CachedRecipes>) {
        return charactersDao.insertRecipe(recipes)
    }
}