package com.example.retolistaderecetas.domain.model

import com.example.retolistaderecetas.data.source.remote.dto.Location

data class Recipe(
    val id: Int,
    val name: String,
    val description: String,
    val location: Location,
    val image: String
)
