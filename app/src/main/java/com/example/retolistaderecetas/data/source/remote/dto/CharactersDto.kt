package com.example.retolistaderecetas.data.source.remote.dto

import com.example.retolistaderecetas.domain.model.Recipes

data class CharactersDto(
    val info: Info,
    val results: List<Result>
)

fun CharactersDto.toListCharacters(): List<Recipes> {
    val resultEntries = results.mapIndexed { _, entries ->
        Recipes(
            id = entries.id,
            name = entries.name,
            location = entries.location,
            image = entries.image
        )
    }
    return resultEntries
}
