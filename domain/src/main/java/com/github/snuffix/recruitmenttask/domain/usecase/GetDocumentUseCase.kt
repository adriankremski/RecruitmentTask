package com.github.snuffix.recruitmenttask.domain.usecase

import com.github.snuffix.recruitmenttask.domain.model.Result
import com.github.snuffix.recruitmenttask.domain.model.DocumentModel
import com.github.snuffix.recruitmenttask.domain.repository.DocumentsRepository
import kotlinx.coroutines.withContext

open class GetDocumentUseCase constructor(
    private val documentsRepository: DocumentsRepository,
    private val coroutinesDispatcherProvider: CoroutinesDispatcherProvider
) : BaseUseCase<Result<DocumentModel>, GetDocumentUseCase.Params>() {

    override suspend fun execute(params: Params) = withContext(coroutinesDispatcherProvider.io) {
        getResult { documentsRepository.getDocument(params.documentId) }
    }

    data class Params(val documentId: String)
}
