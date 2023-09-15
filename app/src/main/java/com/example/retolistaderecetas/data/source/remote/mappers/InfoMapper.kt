package com.example.retolistaderecetas.data.source.remote.mappers

import com.example.retolistaderecetas.data.source.remote.ApiInfo
import com.example.retolistaderecetas.domain.model.info.Info
import javax.inject.Inject

class InfoMapper @Inject constructor(): Mapper<ApiInfo?, Info> {
    override fun mapToDomain(apiEntity: ApiInfo?): Info {
        return Info(pages = apiEntity?.pages ?: 0)
    }
}