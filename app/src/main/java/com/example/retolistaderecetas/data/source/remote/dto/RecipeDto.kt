package com.example.retolistaderecetas.data.source.remote.dto

import com.example.retolistaderecetas.domain.model.Recipe

data class RecipeDto(
    val id: Int,
    val image: String,
    val location: Location,
    val name: String,
    val description: String,
)

fun RecipeDto.toRecipe(): Recipe {
    return Recipe(
        id = id,
        name = name,
        location = location,
        image = image,
        description = description,
    )
}