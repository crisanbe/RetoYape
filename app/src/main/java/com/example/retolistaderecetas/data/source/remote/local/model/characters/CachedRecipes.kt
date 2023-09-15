package com.example.retolistaderecetas.data.source.remote.local.model.characters

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.retolistaderecetas.data.source.remote.dto.Location

@Entity(tableName = "recipes")
data class CachedRecipes(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val name: String,
    @ColumnInfo(name = "latitude") val latitudeName: String,
    @ColumnInfo(name = "longitude") val longitudeName: String,
    val description: String,
    val image: String,
) {
    companion object {
        fun fromDomain(domainModel: com.example.retolistaderecetas.domain.model.Recipe): CachedRecipes {
            val location = domainModel.location
            return CachedRecipes(
                id = domainModel.id,
                name = domainModel.name,
                description = domainModel.description,
                latitudeName = location.latitude,
                longitudeName = location.longitude,
                image = domainModel.image,
            )
        }
    }

    fun toDomain(): com.example.retolistaderecetas.domain.model.Recipes {
        return com.example.retolistaderecetas.domain.model.Recipes(
            id = id,
            name = name,
            location = Location(latitude = latitudeName, longitude = longitudeName),
            image = image,
        )
    }
}