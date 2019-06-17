package com.github.snuffix.recruitmenttask.data

import com.github.snuffix.recruitmenttask.data.mapper.DocumentsEntityMapper
import com.github.snuffix.recruitmenttask.data.repository.DocumentsLocalSource
import com.github.snuffix.recruitmenttask.data.repository.DocumentsRemoteSource
import com.github.snuffix.recruitmenttask.domain.repository.DocumentsRepository
import kotlinx.coroutines.coroutineScope

class DocumentsRepositoryImpl constructor(
    private val mapper: DocumentsEntityMapper,
    private val remoteSource: DocumentsRemoteSource,
    private val localSource: DocumentsLocalSource
) : DocumentsRepository {
    override suspend fun getDocuments(forceRefresh: Boolean) = coroutineScope {
        if (forceRefresh || !localSource.hasCachedDocuments()) {
            val documents = remoteSource.getDocuments()
            localSource.saveDocuments(documents)
        }

        localSource.getDocuments().map { mapper.mapFromEntity(it) }
    }

    override suspend fun getDocument(documentId: String) = mapper.mapFromEntity(localSource.getDocument(documentId))
}
