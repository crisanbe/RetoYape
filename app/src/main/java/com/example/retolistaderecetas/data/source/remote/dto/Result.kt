package com.example.retolistaderecetas.data.source.remote.dto

data class Result(
    val id: Int,
    val image: String,
    val location: Location,
    val name: String,
    val description: String,
)