package com.github.snuffix.recruitmenttask.cache.mapper

import com.github.snuffix.recruitmenttask.cache.model.DocumentCachedModel
import com.github.snuffix.recruitmenttask.data.model.DocumentEntity
import java.util.*


class CachedDocumentsMapper : ModelMapper<DocumentCachedModel, DocumentEntity> {
    override fun mapFromModel(model: DocumentCachedModel): DocumentEntity {
        return DocumentEntity(
            id = model.id,
            name = model.name,
            url = model.url,
            description = model.description,
            type = model.type,
            creationDate = model.creationDate
        )
    }

    override fun mapFromEntity(model: DocumentEntity): DocumentCachedModel {
        return DocumentCachedModel(
            id = UUID.randomUUID().toString(),
            name = model.name,
            url = model.url,
            description = model.description,
            type = model.type,
            creationDate = model.creationDate
        )
    }
}