package com.github.snuffix.recruitmenttask.adapter

import androidx.navigation.fragment.FragmentNavigator
import com.github.snuffix.recruitmenttask.model.DocumentViewItem
import com.github.snuffix.recruitmenttask.model.ViewItem
import com.hannesdorfmann.adapterdelegates4.ListDelegationAdapter

class DocumentsAdapter(onDocumentClick: (DocumentViewItem, FragmentNavigator.Extras) -> Unit) : ListDelegationAdapter<List<ViewItem>>() {
    init {
        delegatesManager.addDelegate(0, DocumentsAdapterDelegate(onDocumentClick))
    }
}