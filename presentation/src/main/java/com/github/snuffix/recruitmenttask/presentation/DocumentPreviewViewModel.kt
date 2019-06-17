package com.github.snuffix.recruitmenttask.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.snuffix.recruitmenttask.domain.usecase.GetDocumentFileUseCase
import com.github.snuffix.recruitmenttask.presentation.model.ErrorType
import com.github.snuffix.recruitmenttask.presentation.model.Resource
import kotlinx.coroutines.launch

class DocumentPreviewViewModel constructor(
    private val getDocumentFileUseCase: GetDocumentFileUseCase
) : ViewModel() {

    private val documentFilePathResource: MutableLiveData<Resource<String>> = MutableLiveData()

    fun documentFilePath(): LiveData<Resource<String>> = documentFilePathResource

    fun loadDocument(documentId: String) {
        viewModelScope.launch {
            documentFilePathResource.postValue(Resource.Loading())

            val params = GetDocumentFileUseCase.Params(documentId = documentId)
            getDocumentFileUseCase.execute(params).whenOk {
                documentFilePathResource.postValue(Resource.Success(this.value.storePath))
            }.whenError {
                documentFilePathResource.postValue(Resource.Error("Oops something went wrong", errorType = ErrorType.ERROR))
            }
        }
    }
}
