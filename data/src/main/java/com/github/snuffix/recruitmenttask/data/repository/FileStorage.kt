package com.github.snuffix.recruitmenttask.data.repository

import com.github.snuffix.recruitmenttask.data.model.DocumentStorePathEntity
import java.io.InputStream

interface FileStorage {
    suspend fun storeFile(file: InputStream): DocumentStorePathEntity
}