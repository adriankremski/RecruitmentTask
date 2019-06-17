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
import com.github.snuffix.recruitmenttask.extensions.titleTransition
import kotlinx.android.synthetic.main.fragment_document_preview.*

const val DOCUMENT_ID_KEY = "DOCUMENT_ID_KEY"
const val DOCUMENT_NAME_KEY = "DOCUMENT_NAME_KEY"

class DocumentPreviewFragment : BaseFragment() {

    companion object {
        fun documentPreviewBundle(documentId: String, documentName: String) =
            bundleOf(DOCUMENT_ID_KEY to documentId, DOCUMENT_NAME_KEY to documentName)
    }

    private val documentId: String by extraNotNull(DOCUMENT_ID_KEY)
    private val documentName: String by extraNotNull(DOCUMENT_NAME_KEY)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedElementEnterTransition = TransitionInflater.from(context).inflateTransition(android.R.transition.move)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View = inflater.inflate(R.layout.fragment_document_preview, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        attachToolbar(documentName, toolbar)
        ViewCompat.setTransitionName(toolbar, requireContext().titleTransition(documentId))
        ViewCompat.setTransitionName(iconView, requireContext().iconTransition(documentId))
    }
}