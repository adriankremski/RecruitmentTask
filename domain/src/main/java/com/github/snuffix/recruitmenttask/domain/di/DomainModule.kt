package com.github.snuffix.recruitmenttask.domain.di

import com.github.snuffix.recruitmenttask.domain.usecase.GetDocumentsListUseCase
import com.github.snuffix.recruitmenttask.domain.usecase.GetDocumentFileUseCase
import com.github.snuffix.recruitmenttask.domain.usecase.SortDocumentsUseCase
import org.koin.dsl.module

val domainModule = module {
    factory { GetDocumentsListUseCase(get(), get()) }
    factory { SortDocumentsUseCase(get()) }
    factory { GetDocumentFileUseCase(get(), get()) }
}
