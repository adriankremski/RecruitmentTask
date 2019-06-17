package com.github.snuffix.recruitmenttask.adapter

import androidx.navigation.fragment.FragmentNavigator
import androidx.recyclerview.widget.DiffUtil
import com.github.snuffix.recruitmenttask.model.DocumentViewItem
import com.github.snuffix.recruitmenttask.model.ViewItem
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter
import com.hannesdorfmann.adapterdelegates4.ListDelegationAdapter

class DocumentsAdapter(diffUtil: DiffUtil.ItemCallback<ViewItem>, onDocumentClick: (DocumentViewItem, FragmentNavigator.Extras) -> Unit) : AsyncListDifferDelegationAdapter<ViewItem>(diffUtil) {
    init {
        delegatesManager.addDelegate(0, DocumentsAdapterDelegate(onDocumentClick))
    }
}