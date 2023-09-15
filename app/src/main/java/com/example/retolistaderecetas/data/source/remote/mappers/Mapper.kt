package com.example.retolistaderecetas.data.source.remote.mappers

interface Mapper<E, D> {
    fun mapToDomain(apiEntity: E): D
}