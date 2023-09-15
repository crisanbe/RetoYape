package com.example.retolistaderecetas.domain.model

import com.example.retolistaderecetas.data.source.remote.dto.Location

data class Recipes(
    val id: Int,
    val name: String,
    val image: String,
    val location: Location,
)
