package com.github.snuffix.recruitmenttask.presentation.mapper

import com.github.snuffix.recruitmenttask.domain.model.DocumentModel
import com.github.snuffix.recruitmenttask.presentation.model.DocumentUIModel

class DocumentMapper : Mapper<DocumentUIModel, DocumentModel> {
    override fun mapToUIModel(document: DocumentModel): DocumentUIModel {
        return DocumentUIModel(
            id = document.id,
            name = document.name,
            description = document.description,
            type = document.type,
            creationDate = document.creationDate
        )
    }

    override fun mapToDomainModel(document: DocumentUIModel): DocumentModel {
        return DocumentModel(
            id = document.id,
            name = document.name,
            description = document.description,
            type = document.type,
            creationDate = document.creationDate
        )
    }
}