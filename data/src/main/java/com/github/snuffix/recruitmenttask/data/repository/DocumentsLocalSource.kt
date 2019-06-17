package com.github.snuffix.recruitmenttask.data.repository

import com.github.snuffix.recruitmenttask.data.model.DocumentEntity

interface DocumentsLocalSource {
    suspend fun getDocuments(): List<DocumentEntity>
    suspend fun hasCachedDocuments(): Boolean
    suspend fun saveDocuments(documents: List<DocumentEntity>)
    suspend fun getDocument(documentId: String): DocumentEntity
}