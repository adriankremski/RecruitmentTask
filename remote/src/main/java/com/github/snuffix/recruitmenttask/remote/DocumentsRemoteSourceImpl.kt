package com.github.snuffix.recruitmenttask.remote

import com.github.snuffix.recruitmenttask.data.model.DocumentEntity
import com.github.snuffix.recruitmenttask.data.repository.DocumentsRemoteSource
import com.github.snuffix.recruitmenttask.remote.mapper.DocumentsMapper
import com.github.snuffix.recruitmenttask.remote.model.DocumentModel
import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat

class DocumentsRemoteSourceImpl constructor(private val mapper: DocumentsMapper) : DocumentsRemoteSource {

    private val dateFormatter = DateTimeFormat.forPattern("MMM dd, yyyy")

    private val documents = listOf(
        DocumentModel(
            name = "Sample",
            url = "https://s3-us-west-2.amazonaws.com/android-task/sample.pdf",
            description = "This is a description",
            type = "pdf",
            creationDate = DateTime.parse("May 10, 2019", dateFormatter).toDate()
        ),
        DocumentModel(
            name = "Sample 2",
            url = "https://s3-us-west-2.amazonaws.com/android-task/sample+2.pdf",
            description = "Description 2 is here",
            type = "pdf",
            creationDate = DateTime.parse("May 11, 2019", dateFormatter).toDate()
        ),
        DocumentModel(
            name = "Sample 3",
            url = "https://s3-us-west-2.amazonaws.com/android-task/sample+3.pdf",
            description = "Description 3 is here",
            type = "pdf",
            creationDate = DateTime.parse("May 13, 2019", dateFormatter).toDate()
        ),
        DocumentModel(
            name = "Sample 4",
            url = "https://s3-us-west-2.amazonaws.com/android-task/sample+4.pdf",
            description = "This is a unique description",
            type = "pdf",
            creationDate = DateTime.parse("May 15, 2019", dateFormatter).toDate()
        ),
        DocumentModel(
            name = "Sample 5",
            url = "https://s3-us-west-2.amazonaws.com/android-task/sample+5.pdf",
            description = "This description is short",
            type = "pdf",
            creationDate = DateTime.parse("May 20, 2019", dateFormatter).toDate()
        )
    )

    override suspend fun getDocuments(): List<DocumentEntity> = documents.map { mapper.mapFromModel(it) }
}
