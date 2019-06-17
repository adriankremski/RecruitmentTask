package com.github.snuffix.recruitmenttask.mapper

import com.github.snuffix.recruitmenttask.model.DocumentViewItem
import com.github.snuffix.recruitmenttask.presentation.model.DocumentUIModel
import org.joda.time.DateTime

class DocumentsMapper : ViewMapper<DocumentUIModel, DocumentViewItem> {
    override fun mapToViewItem(document: DocumentUIModel) = DocumentViewItem(
        id = document.id,
        name = document.name,
        description = document.description,
        type = document.type,
        creationDate = DateTime(document.creationDate)
    )
}