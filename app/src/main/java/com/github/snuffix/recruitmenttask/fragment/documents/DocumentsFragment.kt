package com.github.snuffix.recruitmenttask.fragment.documents

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.core.view.doOnPreDraw
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import com.github.snuffix.recruitmenttask.BaseFragment
import com.github.snuffix.recruitmenttask.R
import com.github.snuffix.recruitmenttask.adapter.DocumentsAdapter
import com.github.snuffix.recruitmenttask.extensions.afterMeasured
import com.github.snuffix.recruitmenttask.mapper.DocumentsMapper
import com.github.snuffix.recruitmenttask.presentation.DocumentsViewModel
import com.github.snuffix.recruitmenttask.presentation.model.DocumentSortOrder
import kotlinx.android.synthetic.main.fragment_documents.*
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel


class DocumentsFragment : BaseFragment() {

    private val documentsMapper by inject<DocumentsMapper>()
    private val documentsViewModel by viewModel<DocumentsViewModel>()

    private val documentsAdapter: DocumentsAdapter
        get() = documentsRecycler.adapter as DocumentsAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.fragment_documents, container, false)

        setHasOptionsMenu(true)

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        attachToolbar(getString(R.string.screen_title_documents), toolbar)

        documentsRecycler.adapter = DocumentsAdapter { document, extras ->
            val bundle = DocumentPreviewFragment.documentPreviewBundle(documentId = document.id, documentName = document.name)
            findNavController().navigate(R.id.action_documentsFragment_to_documentPreviewFragment, bundle, null, extras)
        }

        subscribeToDocuments()
    }

    private fun subscribeToDocuments() {
        documentsViewModel.documents().observe(
            onLoading = {
            },
            onError = { message, _ ->
                Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
            },
            onSuccess = { documents ->
                val documents = documents.map { documentsMapper.mapToViewItem(it) }
                documentsAdapter.items = documents
                documentsAdapter.notifyDataSetChanged()
            }
        )
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        val groupId = 0

        documentsViewModel.sortOrders.forEach { order ->
            menu.add(groupId, order.ordinal, order.ordinal, order.displayName)
        }

        menu.setGroupCheckable(groupId, true, true)
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        menu.findItem(documentsViewModel.selectedSortOrder.ordinal)?.isChecked = true
        super.onPrepareOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (!item.isChecked) {
            item.isChecked = true
        }

        documentsViewModel.selectedSortOrder = DocumentSortOrder.values()[item.itemId]
        return true
    }
}