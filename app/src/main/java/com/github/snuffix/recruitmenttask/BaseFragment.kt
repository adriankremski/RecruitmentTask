package com.github.snuffix.recruitmenttask

import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.github.snuffix.recruitmenttask.presentation.model.ErrorType
import com.github.snuffix.recruitmenttask.presentation.model.Resource

abstract class BaseFragment : Fragment() {

    fun <T : Any> LiveData<Resource<T>>.observe(
        onLoading: () -> Unit = {},
        onError: (String?, ErrorType) -> Unit = { message, errorType -> },
        onSuccess: (T) -> Unit = {}
    ) {
        observe(viewLifecycleOwner, Observer<Resource<T>> { resource ->
            resource?.let {
                when (resource) {
                    is Resource.Loading -> {
                        onLoading()
                    }
                    is Resource.Error -> {
                        onError(resource.message, resource.errorType)
                    }
                    is Resource.Success -> {
                        onSuccess(resource.data)
                    }
                }
            }
        })
    }

    fun attachToolbar(title: String? = null, toolbar: Toolbar) {
        (activity as? ToolbarContainer)?.attachToolbar(title, toolbar)
    }
}