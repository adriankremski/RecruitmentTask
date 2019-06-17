package com.github.snuffix.recruitmenttask.domain.usecase

import com.github.snuffix.recruitmenttask.domain.model.DocumentStorePathModel
import com.github.snuffix.recruitmenttask.domain.model.Result
import com.github.snuffix.recruitmenttask.domain.repository.DocumentsRepository
import kotlinx.coroutines.withContext

open class GetDocumentFileUseCase constructor(
    private val documentsRepository: DocumentsRepository,
    private val coroutinesDispatcherProvider: CoroutinesDispatcherProvider
) : BaseUseCase<Result<DocumentStorePathModel>, GetDocumentFileUseCase.Params>() {

    override suspend fun execute(params: Params) = withContext(coroutinesDispatcherProvider.io) {
        getResult { documentsRepository.getDocumentFile(params.documentId) }
    }

    data class Params(val documentId: String, val storePath: String)
}
