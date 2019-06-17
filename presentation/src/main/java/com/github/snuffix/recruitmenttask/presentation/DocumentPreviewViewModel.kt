package com.github.snuffix.recruitmenttask.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.snuffix.recruitmenttask.domain.usecase.GetDocumentFileUseCase
import com.github.snuffix.recruitmenttask.presentation.model.Resource
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class DocumentPreviewViewModel constructor(
    private val getDocumentFileUseCase: GetDocumentFileUseCase
) : ViewModel() {

    var currentPage: Int = 0
    private val documentFilePathResource: MutableLiveData<Resource<String>> = MutableLiveData()
    private var currentJob: Job? = null

    fun documentFilePath(): LiveData<Resource<String>> = documentFilePathResource

    fun loadDocument(documentId: String) {
        currentJob?.cancel()
        currentJob = viewModelScope.launch {
            documentFilePathResource.postValue(Resource.Loading())

            val params = GetDocumentFileUseCase.Params(documentId = documentId)

            getDocumentFileUseCase.execute(params).whenOk {
                documentFilePathResource.postValue(Resource.Success(this.value.storePath))
            }.whenNetworkError {
                documentFilePathResource.postValue(Resource.networkError())
            }.whenError {
                documentFilePathResource.postValue(Resource.error())
            }
        }
    }
}
