package com.example.retolistaderecetas.data.source.remote.mappers

import com.example.retolistaderecetas.domain.model.Recipes
import javax.inject.Inject

class RecipesMapper @Inject constructor(
    private val apiLocationMapper: LocationMapper
): Mapper<Recipes?, com.example.retolistaderecetas.domain.model.Recipe> {
    override fun mapToDomain(apiEntity: Recipes?): com.example.retolistaderecetas.domain.model.Recipe {
        return com.example.retolistaderecetas.domain.model.Recipe(
            id = apiEntity?.id ?: throw MappingException("Result ID cannot be null"),
            name = apiEntity.name.orEmpty(),
            description = apiEntity.toString(),
            image = apiEntity.image.orEmpty(),
            location = apiLocationMapper.mapToDomain(apiEntity.location)
        )
    }
}