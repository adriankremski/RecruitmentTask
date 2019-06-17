package com.github.snuffix.recruitmenttask

import androidx.annotation.IntegerRes
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.github.snuffix.recruitmenttask.presentation.model.ErrorType
import com.github.snuffix.recruitmenttask.presentation.model.Resource
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

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

    fun executeWithDelay(@IntegerRes delayTime: Int = R.integer.mediumAnimationTime, block: () -> Unit) {
        lifecycleScope.launch {
            delay(requireContext().resources.getInteger(delayTime).toLong())
            block()
        }
    }
}