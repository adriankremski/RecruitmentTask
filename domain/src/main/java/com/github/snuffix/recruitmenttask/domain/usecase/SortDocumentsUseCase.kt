package com.github.snuffix.recruitmenttask.domain.usecase

import com.github.snuffix.recruitmenttask.domain.model.DocumentModel
import com.github.snuffix.recruitmenttask.domain.model.SortOrder
import kotlinx.coroutines.withContext

/**
 * Sorting documents is extracted to use case.
 * Thanks to that the code is easier to test and won't block the UI thread if the list is long
 */
open class SortDocumentsUseCase constructor(
    private val coroutinesDispatcherProvider: CoroutinesDispatcherProvider
) : BaseUseCase<List<DocumentModel>, SortDocumentsUseCase.Params>() {

    override suspend fun execute(params: Params) = withContext(coroutinesDispatcherProvider.io) {
        val documents = params.documents

        when (params.sortOrder) {
            SortOrder.BY_NAME_ASC -> documents.sortedBy { it.name }
            SortOrder.BY_NAME_DESC -> documents.sortedByDescending { it.name }
            SortOrder.BY_DATE_ASC -> documents.sortedBy { it.creationDate }
            SortOrder.BY_DATE_DESC -> documents.sortedByDescending { it.creationDate }
        }
    }

    data class Params(val sortOrder: SortOrder, val documents: List<DocumentModel>)
}
