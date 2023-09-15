package com.example.retolistaderecetas.domain.model

import com.example.retolistaderecetas.domain.model.info.Info

/*
Necesitamos paginar todos los resultados de la api en el objeto Recipes
 */

data class PaginatedRecipes(
    val info: Info,
    val recipes: List<Recipe>
)
