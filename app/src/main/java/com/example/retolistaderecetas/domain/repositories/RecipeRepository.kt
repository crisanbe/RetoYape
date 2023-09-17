package com.example.retolistaderecetas.domain.repositories

import com.example.retolistaderecetas.data.Result
import com.example.retolistaderecetas.domain.model.Recipe
import com.example.retolistaderecetas.domain.model.Recipes
import com.example.retolistaderecetas.util.Results
import com.example.retolistaderecetas.domain.model.info.Info
import kotlinx.coroutines.flow.Flow

interface RecipeRepository {

    suspend fun getListRecipe(page: Int): Results<List<Recipes>>

    fun getRecipes(input: String, limit: Int, offset: Int): Flow<List<Recipes>>

    suspend fun getDetailRecipe(id: Int): Result<Recipe>

    suspend fun getSearchRecipes(pageToLoad: Int, name: String): Results<Info>
}