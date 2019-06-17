package com.github.snuffix.recruitmenttask.remote.di

import com.github.snuffix.recruitmenttask.data.repository.DocumentsRemoteSource
import com.github.snuffix.recruitmenttask.remote.DocumentsRemoteSourceImpl
import com.github.snuffix.recruitmenttask.remote.mapper.DocumentsMapper
import org.koin.dsl.module

val remoteModule = module {
    single { DocumentsMapper() }
    factory<DocumentsRemoteSource> { DocumentsRemoteSourceImpl(get()) }
}

