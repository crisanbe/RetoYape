package com.example.retolistaderecetas.data.source.remote.local.daos

import androidx.room.*
import com.example.retolistaderecetas.data.source.remote.local.model.characters.CachedRecipes
import kotlinx.coroutines.flow.Flow

@Dao
interface RecipesDao {

    @Transaction
    @Query("SELECT * FROM recipes WHERE name LIKE '%' || :name || '%' LIMIT :limit OFFSET :offset")
    fun getRecipes(name: String, limit: Int, offset: Int): Flow<List<CachedRecipes>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecipe(character: List<CachedRecipes>)
}