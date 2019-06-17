package com.github.snuffix.recruitmenttask.domain.repository

import com.github.snuffix.recruitmenttask.domain.model.DocumentModel

interface DocumentsRepository {
    suspend fun getDocuments(forceRefresh: Boolean): List<DocumentModel>
    suspend fun getDocument(documentId: String): DocumentModel
}