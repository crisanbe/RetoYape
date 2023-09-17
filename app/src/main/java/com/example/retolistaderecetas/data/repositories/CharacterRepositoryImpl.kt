package com.example.retolistaderecetas.data.repositories

import com.example.retolistaderecetas.data.Result
import com.example.retolistaderecetas.data.source.remote.RecipeApi
import com.example.retolistaderecetas.data.source.remote.dto.toRecipe
import com.example.retolistaderecetas.data.source.remote.dto.toListCharacters
import com.example.retolistaderecetas.data.source.remote.local.model.characters.CachedRecipes
import com.example.retolistaderecetas.data.source.remote.mappers.RecipesMapper
import com.example.retolistaderecetas.data.source.remote.mappers.InfoMapper
import com.example.retolistaderecetas.domain.model.Recipe
import com.example.retolistaderecetas.domain.model.Recipes
import com.example.retolistaderecetas.domain.model.PaginatedRecipes
import com.example.retolistaderecetas.domain.repositories.RecipeRepository
import com.example.retolistaderecetas.util.ErrorEntity
import com.example.retolistaderecetas.util.Results
import com.example.retolistaderecetas.domain.model.info.Info
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class CharacterRepositoryImpl @Inject constructor(
    private val api: RecipeApi,
    private val cache: com.example.retolistaderecetas.data.source.remote.local.Cache,
    private val apiInfoMapper: InfoMapper,
    private val apiRecipeMapper: RecipesMapper
): RecipeRepository {

    override suspend fun getListRecipe(page: Int): Results<List<Recipes>> {

        val response = try {
            api.getRecipes(page).toListCharacters()

        } catch (e: HttpException) {
            return Results.Error(ErrorEntity.ApiError.UnKnown)
        } catch (e: IOException) {
            return Results.Error(ErrorEntity.ApiError.NotFound)
        }
        return Results.Success(response)
    }

    override fun getRecipes(input: String, limit: Int, offset: Int): Flow<List<Recipes>> = flow {
        cache.getRecipes(input, limit, offset).map { characterList ->
            characterList.map { it.toDomain() }
        }.collect { emit(it) }
        //emitAll(list)
    }


    override suspend fun getDetailRecipe(id: Int): Result<Recipe> {
        val response = try {
            api.getDetailRecipe(id)
        } catch (e: Exception) {
            return Result.Error("An unknown error occurred")
        }
        return Result.Success(response.toRecipe())
    }


    override suspend fun getSearchRecipes(
        pageToLoad: Int,
        name: String
    ): Results<Info> {
        val response = try {
            val (apiInfo, apiCharacters) = api.getSearchRecipes(pageToLoad, name)
            val paginatedCharacters = PaginatedRecipes(
                info = apiInfoMapper.mapToDomain(apiInfo),
                recipes = apiCharacters?.map { apiRecipeMapper.mapToDomain(it) }.orEmpty()
            )
            cache.storeRecipes(paginatedCharacters.recipes.map { CachedRecipes.fromDomain(it) })
            paginatedCharacters.info
        } catch (e: HttpException) {
            return Results.Error(ErrorEntity.ApiError.UnKnown)
        } catch (e: IOException) {
            return Results.Error(ErrorEntity.ApiError.NotFound)
        }
        return Results.Success(response)
    }

}
