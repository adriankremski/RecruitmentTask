package com.github.snuffix.recruitmenttask.data

import com.github.snuffix.recruitmenttask.data.mapper.DocumentsEntityMapper
import com.github.snuffix.recruitmenttask.data.repository.DocumentsLocalSource
import com.github.snuffix.recruitmenttask.data.repository.DocumentsRemoteSource
import com.github.snuffix.recruitmenttask.data.repository.FileStorage
import com.github.snuffix.recruitmenttask.domain.model.DocumentModel
import com.github.snuffix.recruitmenttask.domain.model.DocumentStorePathModel
import com.github.snuffix.recruitmenttask.domain.repository.DocumentsRepository

class DocumentsRepositoryImpl constructor(
    private val mapper: DocumentsEntityMapper,
    private val fileStorage: FileStorage,
    private val remoteSource: DocumentsRemoteSource,
    private val localSource: DocumentsLocalSource
) : DocumentsRepository {

    override suspend fun getDocumentFile(documentId: String): DocumentStorePathModel {
        val document = localSource.getDocument(documentId)
        val documentFileStream = remoteSource.getDocumentFile(document.url)
        return DocumentStorePathModel(storePath = fileStorage.storeFile(documentFileStream).storePath)
    }

    override suspend fun getDocuments(forceRefresh: Boolean) : List<DocumentModel> {
        if (forceRefresh || !localSource.hasCachedDocuments()) {
            val documents = remoteSource.getDocuments()
            localSource.saveDocuments(documents)
        }

        return localSource.getDocuments().map { mapper.mapFromEntity(it) }
    }
}
