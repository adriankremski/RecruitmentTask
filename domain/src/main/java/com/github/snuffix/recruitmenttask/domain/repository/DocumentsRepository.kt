package com.github.snuffix.recruitmenttask.domain.repository

import com.github.snuffix.recruitmenttask.domain.model.DocumentModel
import com.github.snuffix.recruitmenttask.domain.model.DocumentStorePathModel

interface DocumentsRepository {
    suspend fun getDocuments(forceRefresh: Boolean): List<DocumentModel>
    suspend fun getDocumentFile(documentId: String): DocumentStorePathModel
}