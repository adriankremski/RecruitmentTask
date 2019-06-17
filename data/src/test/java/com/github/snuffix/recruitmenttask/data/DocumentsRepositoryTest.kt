package com.github.snuffix.recruitmenttask.data

import com.github.snuffix.recruitmenttask.data.mapper.DocumentsEntityMapper
import com.github.snuffix.recruitmenttask.data.model.DocumentEntity
import com.github.snuffix.recruitmenttask.data.repository.DocumentsLocalSource
import com.github.snuffix.recruitmenttask.data.repository.DocumentsRemoteSource
import com.github.snuffix.recruitmenttask.data.repository.FileStorage
import com.nhaarman.mockitokotlin2.*
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.Test
import java.util.*

class SongsRepositoryTest {

    private val mapper = DocumentsEntityMapper()
    private val remote = mock<DocumentsRemoteSource>()
    private val fileStorage = mock<FileStorage>()
    private val local = mock<DocumentsLocalSource>()
    private val repository = DocumentsRepositoryImpl(mapper, fileStorage, remote, local)

    @Test
    fun getDocumentsWillReturnCachedDocuments() {
        runBlocking {
            val testDocuments = stubTestData()
            whenever(local.hasCachedDocuments()).thenReturn(true)
            whenever(local.getDocuments()).thenReturn(testDocuments)

            val documents = repository.getDocuments(forceRefresh = false)

            verify(local).getDocuments()
            verify(local, never()).saveDocuments(any())
            assertEquals(testDocuments.map { mapper.mapFromEntity(it) }, documents)
        }
    }


    private fun stubTestData(): List<DocumentEntity> {
        return List(100) {
            DocumentEntity(
                id = UUID.randomUUID().toString(),
                name = UUID.randomUUID().toString(),
                url = UUID.randomUUID().toString(),
                type = UUID.randomUUID().toString(),
                description = UUID.randomUUID().toString(),
                creationDate = Date()
            )
        }
    }

    @Test
    fun getDocumentsWillFetchDocumentsAndStoreThem() {
        runBlocking {
            val testDocuments = stubTestData()
            whenever(local.hasCachedDocuments()).thenReturn(false)
            whenever(remote.getDocuments()).thenReturn(testDocuments)
            whenever(local.getDocuments()).thenReturn(testDocuments)

            val documents = repository.getDocuments(forceRefresh = false)

            verify(local).getDocuments()
            verify(local).saveDocuments(testDocuments)
            assertEquals(testDocuments.map { mapper.mapFromEntity(it) }, documents)
        }
    }
}