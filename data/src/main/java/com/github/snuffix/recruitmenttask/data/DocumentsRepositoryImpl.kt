package com.github.snuffix.recruitmenttask.data

import com.github.snuffix.recruitmenttask.data.mapper.DocumentsEntityMapper
import com.github.snuffix.recruitmenttask.data.repository.DocumentsLocalSource
import com.github.snuffix.recruitmenttask.data.repository.DocumentsRemoteSource
import com.github.snuffix.recruitmenttask.data.repository.FileStorage
import com.github.snuffix.recruitmenttask.domain.model.DocumentStorePathModel
import com.github.snuffix.recruitmenttask.domain.repository.DocumentsRepository
import kotlinx.coroutines.coroutineScope

class DocumentsRepositoryImpl constructor(
    private val mapper: DocumentsEntityMapper,
    private val fileStorage: FileStorage,
    private val remoteSource: DocumentsRemoteSource,
    private val localSource: DocumentsLocalSource
) : DocumentsRepository {

    override suspend fun getDocumentFile(documentId: String) = coroutineScope {
        val document = localSource.getDocument(documentId)
        val documentFileStream = remoteSource.getDocumentFile(document.url)
        DocumentStorePathModel(storePath = fileStorage.storeFile(documentFileStream).storePath)
    }

    override suspend fun getDocuments(forceRefresh: Boolean) = coroutineScope {
        if (forceRefresh || !localSource.hasCachedDocuments()) {
            val documents = remoteSource.getDocuments()
            localSource.saveDocuments(documents)
        }

        localSource.getDocuments().map { mapper.mapFromEntity(it) }
    }
}
