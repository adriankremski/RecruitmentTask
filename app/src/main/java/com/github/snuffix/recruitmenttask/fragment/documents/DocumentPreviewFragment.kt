package com.github.snuffix.recruitmenttask.fragment.documents

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.core.view.ViewCompat
import androidx.lifecycle.lifecycleScope
import androidx.transition.TransitionInflater
import com.github.snuffix.recruitmenttask.BaseFragment
import com.github.snuffix.recruitmenttask.R
import com.github.snuffix.recruitmenttask.extensions.extraNotNull
import com.github.snuffix.recruitmenttask.extensions.iconTransition
import com.github.snuffix.recruitmenttask.extensions.titleTransition
import com.github.snuffix.recruitmenttask.presentation.DocumentPreviewViewModel
import com.github.snuffix.recruitmenttask.presentation.model.ErrorType
import com.github.snuffix.recruitmenttask.view.TransitionEndListener
import kotlinx.android.synthetic.main.fragment_document_preview.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.android.viewmodel.ext.android.viewModel

const val DOCUMENT_ID_KEY = "DOCUMENT_ID_KEY"
const val DOCUMENT_NAME_KEY = "DOCUMENT_NAME_KEY"

class DocumentPreviewFragment : BaseFragment() {

    companion object {
        fun documentPreviewBundle(documentId: String, documentName: String) =
            bundleOf(DOCUMENT_ID_KEY to documentId, DOCUMENT_NAME_KEY to documentName)
    }

    private val documentPreviewViewModel: DocumentPreviewViewModel by viewModel()
    private val documentId: String by extraNotNull(DOCUMENT_ID_KEY)
    private val documentName: String by extraNotNull(DOCUMENT_NAME_KEY)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        TransitionInflater.from(context).inflateTransition(android.R.transition.move).apply {
            sharedElementEnterTransition = this

            this.addListener(TransitionEndListener {
                documentPreviewViewModel.loadDocument(documentId)
            })
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View = inflater.inflate(R.layout.fragment_document_preview, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        attachToolbar(documentName, toolbar)
        ViewCompat.setTransitionName(toolbar, requireContext().titleTransition(documentId))
        ViewCompat.setTransitionName(iconView, requireContext().iconTransition(documentId))

        errorView.onRetry = {
            documentPreviewViewModel.loadDocument(documentId)
        }

        documentPreviewViewModel.documentFilePath().observe(
            onLoading = ::showLoading,
            onError = ::showError,
            onSuccess = ::showFetchedDocument
        )
    }

    private fun showLoading() {
        errorView.visibility = View.GONE
        progressView.visibility = View.VISIBLE
    }

    private fun showError(message: String?, errorType: ErrorType) {
        lifecycleScope.launch {
            delay(requireContext().resources.getInteger(R.integer.mediumAnimationTime).toLong())

            progressView.visibility = View.GONE
            errorView.visibility = View.VISIBLE

            if (errorType == ErrorType.NETWORK) {
                errorView.networkError()
            } else {
                errorView.error(message)
            }
        }
    }

    private fun showFetchedDocument(documentStorePath: String) {
        progressView.visibility = View.GONE
        Toast.makeText(requireContext(), "Document fetched", Toast.LENGTH_SHORT).show()
    }
}