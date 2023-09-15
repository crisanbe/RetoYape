package com.example.retolistaderecetas.data.source.remote.mappers

import com.example.retolistaderecetas.data.source.remote.dto.Location
import javax.inject.Inject

class LocationMapper @Inject constructor(): Mapper<Location?, Location> {
    override fun mapToDomain(apiEntity: Location?): Location {
        return Location(latitude = apiEntity?.latitude.orEmpty(), longitude = apiEntity?.longitude.orEmpty())
    }
}