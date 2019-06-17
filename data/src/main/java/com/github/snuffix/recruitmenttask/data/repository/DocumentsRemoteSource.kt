package com.github.snuffix.recruitmenttask.data.repository

import com.github.snuffix.recruitmenttask.data.model.DocumentEntity
import java.io.IOException
import java.io.InputStream

interface DocumentsRemoteSource {
    suspend fun getDocuments(): List<DocumentEntity>
    suspend fun getDocumentFile(url: String): InputStream
}

class RemoteException(val code: Int) : Exception()
