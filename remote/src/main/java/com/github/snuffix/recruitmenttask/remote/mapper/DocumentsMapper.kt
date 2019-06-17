package com.github.snuffix.recruitmenttask.remote.mapper

import com.github.snuffix.recruitmenttask.data.model.DocumentEntity
import com.github.snuffix.recruitmenttask.remote.model.DocumentModel

class DocumentsMapper : ModelMapper<DocumentModel, DocumentEntity> {
    override fun mapFromModel(model: DocumentModel): DocumentEntity {
        return DocumentEntity(
            name = model.name,
            url = model.url,
            description = model.description,
            type = model.type,
            creationDate = model.creationDate
        )
    }
}