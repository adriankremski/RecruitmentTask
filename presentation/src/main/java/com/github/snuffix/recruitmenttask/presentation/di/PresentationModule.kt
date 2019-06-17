package com.github.snuffix.recruitmenttask.presentation.di

import com.github.snuffix.recruitmenttask.presentation.DocumentsViewModel
import com.github.snuffix.recruitmenttask.presentation.mapper.DocumentMapper
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val presentationModule = module {
    single { DocumentMapper() }
    viewModel { DocumentsViewModel(get(), get(), get()) }
}
