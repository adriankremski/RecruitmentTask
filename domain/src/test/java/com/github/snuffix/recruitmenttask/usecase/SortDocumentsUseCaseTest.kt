package com.github.snuffix.recruitmenttask.usecase

import com.github.snuffix.recruitmenttask.domain.model.DocumentModel
import com.github.snuffix.recruitmenttask.domain.model.SortOrder
import com.github.snuffix.recruitmenttask.domain.usecase.CoroutinesDispatcherProvider
import com.github.snuffix.recruitmenttask.domain.usecase.SortDocumentsUseCase
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import java.util.*

class SortDocumentsUseCaseTest {

    private lateinit var sortDocumentsUseCase: SortDocumentsUseCase

    @Before
    fun setup() {
        sortDocumentsUseCase = SortDocumentsUseCase(
            CoroutinesDispatcherProvider(
                main = Dispatchers.Default,
                io = Dispatchers.Default,
                computation = Dispatchers.Default
            )
        )
    }

    @Test
    fun sortByNameDescendingSortsDocuments() {
        runBlocking {
            val documents = stubTestData().sortedBy { it.name }
            val sortedDocuments = sortDocumentsUseCase.execute(SortDocumentsUseCase.Params(SortOrder.BY_NAME_DESC, documents))

            assertEquals(documents.sortedByDescending { it.name }, sortedDocuments)
        }
    }

    private fun stubTestData(): List<DocumentModel> {
        val calendar = Calendar.getInstance()

        return List(100) {
            Calendar.getInstance().set(Calendar.DAY_OF_YEAR, it)

            DocumentModel(
                id = UUID.randomUUID().toString(),
                name = UUID.randomUUID().toString(),
                type = UUID.randomUUID().toString(),
                description = UUID.randomUUID().toString(),
                creationDate = calendar.time
            )
        }
    }

    @Test
    fun sortByNameAscendingSortsDocuments() {
        runBlocking {
            val documents = stubTestData().sortedByDescending { it.name }
            val sortedDocuments = sortDocumentsUseCase.execute(SortDocumentsUseCase.Params(SortOrder.BY_NAME_ASC, documents))

            assertEquals(documents.sortedBy { it.name }, sortedDocuments)
        }
    }

    @Test
    fun sortByDateDescendingSortsDocuments() {
        runBlocking {
            val documents = stubTestData().sortedBy { it.creationDate }
            val sortedDocuments = sortDocumentsUseCase.execute(SortDocumentsUseCase.Params(SortOrder.BY_DATE_DESC, documents))

            assertEquals(documents.sortedByDescending { it.creationDate }, sortedDocuments)
        }
    }

    @Test
    fun sortByDateAscendingSortsDocuments() {
        runBlocking {
            val documents = stubTestData().sortedByDescending { it.creationDate }
            val sortedDocuments = sortDocumentsUseCase.execute(SortDocumentsUseCase.Params(SortOrder.BY_DATE_ASC, documents))

            assertEquals(documents.sortedBy { it.creationDate }, sortedDocuments)
        }
    }
}
