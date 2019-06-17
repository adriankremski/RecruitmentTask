package com.github.snuffix.recruitmenttask.adapter

import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.navigation.fragment.FragmentNavigator
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.recyclerview.widget.RecyclerView
import com.github.snuffix.recruitmenttask.R
import com.github.snuffix.recruitmenttask.extensions.iconTransition
import com.github.snuffix.recruitmenttask.extensions.inflateView
import com.github.snuffix.recruitmenttask.extensions.titleTransition
import com.github.snuffix.recruitmenttask.model.DocumentViewItem
import com.github.snuffix.recruitmenttask.model.ViewItem
import com.hannesdorfmann.adapterdelegates4.AbsListItemAdapterDelegate
import kotlinx.android.synthetic.main.item_document_row.view.*
import java.text.DateFormat

class DocumentsAdapterDelegate(val onDocumentClick: (DocumentViewItem, FragmentNavigator.Extras) -> Unit) :
    AbsListItemAdapterDelegate<DocumentViewItem, ViewItem, DocumentsAdapterDelegate.DocumentItemHolder>() {

    override fun onBindViewHolder(item: DocumentViewItem, holder: DocumentItemHolder, payloads: MutableList<Any>) {
        holder.bind(item)
    }

    override fun isForViewType(item: ViewItem, items: MutableList<ViewItem>, position: Int) =
        item is DocumentViewItem && item.isPdf()

    override fun onCreateViewHolder(parent: ViewGroup): DocumentItemHolder {
        val itemView = parent.context.inflateView(R.layout.item_document_row, parent)
        return DocumentItemHolder(itemView)
    }

    inner class DocumentItemHolder(private val childView: View) : RecyclerView.ViewHolder(childView) {
        fun bind(document: DocumentViewItem) {
            childView.setOnClickListener {
                ViewCompat.setTransitionName(childView.cardView, titleTransition(document.id))
                ViewCompat.setTransitionName(childView.iconView, iconTransition(document.id))

                val extras = FragmentNavigatorExtras(
                    childView.cardView to ViewCompat.getTransitionName(childView.cardView)!!,
                    childView.iconView to ViewCompat.getTransitionName(childView.iconView)!!
                )

                onDocumentClick(document, extras)
            }
            childView.nameLabel.text = document.name
            childView.descriptionLabel.text = document.description
            childView.dateLabel.text = DateFormat.getDateInstance().format(document.creationDate.toDate())
        }
    }
}