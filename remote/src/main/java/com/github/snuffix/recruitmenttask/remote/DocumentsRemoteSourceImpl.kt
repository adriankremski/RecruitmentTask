package com.github.snuffix.recruitmenttask.remote

import com.github.snuffix.recruitmenttask.data.model.DocumentEntity
import com.github.snuffix.recruitmenttask.data.repository.DocumentsRemoteSource
import com.github.snuffix.recruitmenttask.data.repository.RemoteException
import com.github.snuffix.recruitmenttask.remote.mapper.DocumentsMapper
import com.github.snuffix.recruitmenttask.remote.model.DocumentModel
import com.github.snuffix.recruitmenttask.remote.network.service.DocumentsService
import kotlinx.coroutines.coroutineScope
import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat
import retrofit2.Response
import java.io.IOException
import java.io.InputStream

class DocumentsRemoteSourceImpl constructor(
    private val mapper: DocumentsMapper,
    private val documentsService: DocumentsService
) : DocumentsRemoteSource {
    private val dateFormatter = DateTimeFormat.forPattern("MMMM dd, yyyy")

    private val documents by lazy {
        listOf(
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
    }

    override suspend fun getDocuments(): List<DocumentEntity> = documents.map { mapper.mapFromModel(it) }

    override suspend fun getDocumentFile(url: String): InputStream {
        return documentsService.getFile(url).getBodyOrThrow().byteStream()
    }

    private fun <T : Any> Response<T>.getBodyOrThrow() = if (isSuccessful) {
        body() ?: throw IOException("Body can't be null")
    } else {
        throw RemoteException(code())
    }
}
