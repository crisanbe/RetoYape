package com.example.retolistaderecetas.data.source.remote

import com.example.retolistaderecetas.domain.model.Recipes
import com.google.gson.annotations.SerializedName

data class ApiPaginatedRecipes(
    val info: ApiInfo?,
    @SerializedName("results") val characters: List<Recipes>?
)