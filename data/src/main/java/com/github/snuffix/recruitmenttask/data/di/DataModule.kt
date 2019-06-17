package com.github.snuffix.recruitmenttask.data.di

import com.github.snuffix.recruitmenttask.data.DocumentsRepositoryImpl
import com.github.snuffix.recruitmenttask.data.mapper.DocumentsEntityMapper
import com.github.snuffix.recruitmenttask.domain.repository.DocumentsRepository
import org.koin.dsl.module

val dataModule = module {
    single { DocumentsEntityMapper() }
    single<DocumentsRepository> { DocumentsRepositoryImpl(get(), get(), get()) }
}
