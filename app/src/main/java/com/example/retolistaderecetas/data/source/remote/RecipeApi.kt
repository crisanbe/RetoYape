package com.example.retolistaderecetas.data.source.remote

import com.example.retolistaderecetas.data.source.remote.dto.RecipeDto
import com.example.retolistaderecetas.data.source.remote.dto.CharactersDto
import com.example.retolistaderecetas.util.ID_PARAMETER
import com.example.retolistaderecetas.util.NAME_PARAMETER
import com.example.retolistaderecetas.util.PAGE_PARAMETER
import com.example.retolistaderecetas.util.RECIPE_DETAIL_ENDPOINT
import com.example.retolistaderecetas.util.RECIPE_ENDPOINT
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RecipeApi {

    @GET(RECIPE_ENDPOINT)
    suspend fun getRecipes(
        @Query(PAGE_PARAMETER) page: Int,
    ): CharactersDto

    @GET(RECIPE_DETAIL_ENDPOINT)
    suspend fun getDetailRecipe(
        @Path(ID_PARAMETER) id: Int
    ): RecipeDto

    @GET(RECIPE_ENDPOINT)
    suspend fun getSearchRecipes(
        @Query(PAGE_PARAMETER) id: Int,
        @Query(NAME_PARAMETER) name: String
    ): ApiPaginatedRecipes
}