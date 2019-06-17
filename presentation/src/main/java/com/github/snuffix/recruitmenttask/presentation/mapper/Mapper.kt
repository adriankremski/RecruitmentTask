package com.github.snuffix.recruitmenttask.presentation.mapper

import com.github.snuffix.recruitmenttask.domain.model.DocumentModel
import com.github.snuffix.recruitmenttask.presentation.model.DocumentUIModel

interface Mapper<out PresentationModel, in DomainModel> {
    fun mapToUIModel(model: DomainModel): PresentationModel
    fun mapToDomainModel(document: DocumentUIModel): DocumentModel
}