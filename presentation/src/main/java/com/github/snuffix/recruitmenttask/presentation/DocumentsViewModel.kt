package com.github.snuffix.recruitmenttask.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.snuffix.recruitmenttask.domain.model.DocumentModel
import com.github.snuffix.recruitmenttask.domain.model.SortOrder
import com.github.snuffix.recruitmenttask.domain.usecase.GetDocumentsListUseCase
import com.github.snuffix.recruitmenttask.domain.usecase.SortDocumentsUseCase
import com.github.snuffix.recruitmenttask.presentation.mapper.DocumentMapper
import com.github.snuffix.recruitmenttask.presentation.model.DocumentSortOrder
import com.github.snuffix.recruitmenttask.presentation.model.DocumentUIModel
import com.github.snuffix.recruitmenttask.presentation.model.Resource
import kotlinx.coroutines.launch
import kotlin.properties.Delegates

class DocumentsViewModel constructor(
    private val getDocuments: GetDocumentsListUseCase,
    private val sortDocumentsUseCase: SortDocumentsUseCase,
    private val mapper: DocumentMapper
) : ViewModel() {

    private val displayedDocuments = mutableListOf<DocumentUIModel>()
    private val documentsResource: MutableLiveData<Resource<List<DocumentUIModel>>> = MutableLiveData()

    val sortOrders: List<DocumentSortOrder> = SortOrder.values().map { it.mapToUIModel() }

    var selectedSortOrder: DocumentSortOrder by Delegates.observable(DocumentSortOrder.BY_NAME_ASC) { _, oldOrder, newOrder ->
        if (oldOrder != newOrder) {
            viewModelScope.launch {
                val sortedDocuments = sortDocuments(selectedSortOrder, displayedDocuments.mapToDomainModel())
                displayDocuments(sortedDocuments)
            }
        }
    }

    init {
        getDocuments()
    }

    fun documents(): LiveData<Resource<List<DocumentUIModel>>> = documentsResource

    private fun getDocuments(forceRefresh: Boolean = false) {
        viewModelScope.launch {
            getDocuments.execute(GetDocumentsListUseCase.Params(forceRefresh = forceRefresh)).whenOk {
                val sortedDocuments = sortDocuments(selectedSortOrder, this.value)
                displayDocuments(sortedDocuments)
            }.whenError {
                documentsResource.postValue(Resource.error("Oops, something went wrong"))
            }
        }
    }

    private suspend fun sortDocuments(sortOrder: DocumentSortOrder, documents: List<DocumentModel>): List<DocumentModel> {
        val params = SortDocumentsUseCase.Params(sortOrder.mapToDomainModel(), documents)
        return sortDocumentsUseCase.execute(params)
    }

    private fun displayDocuments(documents: List<DocumentModel>) {
        displayedDocuments.clear()
        displayedDocuments.addAll(documents.mapToUIModel())
        documentsResource.postValue(Resource.Success(displayedDocuments))
    }

    private fun SortOrder.mapToUIModel() = DocumentSortOrder.valueOf(this.name)
    private fun DocumentSortOrder.mapToDomainModel() = SortOrder.valueOf(this.name)

    private fun List<DocumentModel>.mapToUIModel() = map { mapper.mapToUIModel(it) }
    private fun List<DocumentUIModel>.mapToDomainModel() = map { mapper.mapToDomainModel(it) }
}
