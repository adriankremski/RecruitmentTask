package com.github.snuffix.recruitmenttask.cache

import com.github.snuffix.recruitmenttask.cache.db.DocumentsDao
import com.github.snuffix.recruitmenttask.cache.db.DocumentsDatabase
import com.github.snuffix.recruitmenttask.cache.mapper.CachedDocumentsMapper
import com.github.snuffix.recruitmenttask.data.model.DocumentEntity
import com.github.snuffix.recruitmenttask.data.repository.DocumentsLocalSource

class DocumentsLocalSourceImpl constructor(
    private val cachedDocumentsMapper: CachedDocumentsMapper,
    documentsDatabase: DocumentsDatabase
) : DocumentsLocalSource {

    private val dao: DocumentsDao = documentsDatabase.documentsDao()

    override suspend fun getDocuments() =
        dao.queryDocuments().map { cachedDocumentsMapper.mapFromModel(it) }

    override suspend fun hasCachedDocuments() = dao.countDocuments() > 0

    override suspend fun saveDocuments(documents: List<DocumentEntity>) =
        dao.insertDocuments(documents.map { cachedDocumentsMapper.mapFromEntity(it) })

    override suspend fun getDocument(documentId: String): DocumentEntity {
        TODO("not implemented")
    }
}

