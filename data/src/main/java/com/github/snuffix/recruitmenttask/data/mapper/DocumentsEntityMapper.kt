package com.github.snuffix.recruitmenttask.data.mapper

import com.github.snuffix.recruitmenttask.data.model.DocumentEntity
import com.github.snuffix.recruitmenttask.domain.model.DocumentModel
import java.lang.IllegalArgumentException

class DocumentsEntityMapper : EntityMapper<DocumentEntity, DocumentModel> {
    override fun mapFromEntity(entity: DocumentEntity): DocumentModel {
        return DocumentModel(
            id = entity.id ?: throw IllegalArgumentException("Id can't be null"),
            name = entity.name,
            description = entity.description,
            type = entity.type,
            creationDate = entity.creationDate
        )
    }
}
