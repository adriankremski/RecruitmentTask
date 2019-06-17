package com.github.snuffix.recruitmenttask.remote.di

import com.github.snuffix.recruitmenttask.data.repository.DocumentsRemoteSource
import com.github.snuffix.recruitmenttask.remote.DocumentsRemoteSourceImpl
import com.github.snuffix.recruitmenttask.remote.mapper.DocumentsMapper
import com.github.snuffix.recruitmenttask.remote.network.service.DocumentsServiceFactory
import org.koin.dsl.module

val remoteModule = module {
    single { DocumentsMapper() }
    single { DocumentsServiceFactory.makeService(get(), get()) }
    factory<DocumentsRemoteSource> { DocumentsRemoteSourceImpl(get(), get()) }
}

