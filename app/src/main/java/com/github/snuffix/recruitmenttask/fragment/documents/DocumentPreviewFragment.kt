package com.github.snuffix.recruitmenttask.fragment.documents

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.view.ViewCompat
import androidx.transition.TransitionInflater
import com.github.snuffix.recruitmenttask.BaseFragment
import com.github.snuffix.recruitmenttask.R
import com.github.snuffix.recruitmenttask.extensions.extraNotNull
import com.github.snuffix.recruitmenttask.extensions.iconTransition
import com.github.snuffix.recruitmenttask.extensions.onPageChange
import com.github.snuffix.recruitmenttask.extensions.titleTransition
import com.github.snuffix.recruitmenttask.presentation.DocumentPreviewViewModel
import com.github.snuffix.recruitmenttask.presentation.model.ErrorType
import com.github.snuffix.recruitmenttask.view.TransitionEndListener
import es.voghdev.pdfviewpager.library.adapter.PDFPagerAdapter
import kotlinx.android.synthetic.main.fragment_document_preview.*
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
        executeWithDelay {
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
        executeWithDelay {
            progressView.visibility = View.GONE
            controls.visibility = View.VISIBLE

            val adapter = PDFPagerAdapter(requireContext(), documentStorePath)
            pdfViewPager.adapter = adapter

            pageNumberLabel.text = getString(R.string.page_with_number, (documentPreviewViewModel.currentPage + 1).toString())

            pdfViewPager.setCurrentItem(documentPreviewViewModel.currentPage, false)
            invalidateNavigationArrows(currentPage = documentPreviewViewModel.currentPage, pageCount = adapter.count)

            pdfViewPager.onPageChange { page ->
                invalidateNavigationArrows(currentPage = page, pageCount = adapter.count)
                documentPreviewViewModel.currentPage = page
                pageNumberLabel.text = getString(R.string.page_with_number, (page + 1).toString())
            }

            previousPage.setOnClickListener {
                val currentItem = pdfViewPager.currentItem

                if (currentItem > 0) {
                    pdfViewPager.currentItem = pdfViewPager.currentItem - 1
                }
            }

            nextPage.setOnClickListener {
                val currentItem = pdfViewPager.currentItem

                if (currentItem < adapter.count) {
                    pdfViewPager.currentItem = pdfViewPager.currentItem + 1
                }
            }
        }
    }

    private fun invalidateNavigationArrows(currentPage: Int, pageCount: Int) {
        previousPage.visibility = if (currentPage == 0) View.GONE else View.VISIBLE
        nextPage.visibility = if (currentPage == pageCount - 1) View.GONE else View.VISIBLE
    }

}