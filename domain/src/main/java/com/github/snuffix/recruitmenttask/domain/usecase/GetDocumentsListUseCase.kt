package com.github.snuffix.recruitmenttask.domain.usecase

import com.github.snuffix.recruitmenttask.domain.model.Result
import com.github.snuffix.recruitmenttask.domain.model.DocumentModel
import com.github.snuffix.recruitmenttask.domain.repository.DocumentsRepository
import kotlinx.coroutines.withContext

open class GetDocumentsListUseCase constructor(
    private val documentsRepository: DocumentsRepository,
    private val coroutinesDispatcherProvider: CoroutinesDispatcherProvider
) : BaseUseCase<Result<List<DocumentModel>>, GetDocumentsListUseCase.Params>() {

    override suspend fun execute(params: Params) = withContext(coroutinesDispatcherProvider.io) {
        getResult { documentsRepository.getDocuments(forceRefresh = params.forceRefresh) }
    }

    data class Params(val forceRefresh: Boolean)
}
