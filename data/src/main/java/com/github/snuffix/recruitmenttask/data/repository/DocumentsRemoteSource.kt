package com.github.snuffix.recruitmenttask.data.repository

import com.github.snuffix.recruitmenttask.data.model.DocumentEntity

interface DocumentsRemoteSource {
    suspend fun getDocuments(): List<DocumentEntity>
}
