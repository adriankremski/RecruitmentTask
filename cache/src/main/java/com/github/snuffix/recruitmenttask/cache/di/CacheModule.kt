package com.github.snuffix.recruitmenttask.cache.di

import com.github.snuffix.recruitmenttask.cache.DocumentsLocalSourceImpl
import com.github.snuffix.recruitmenttask.cache.db.DocumentsDatabase
import com.github.snuffix.recruitmenttask.cache.mapper.CachedDocumentsMapper
import com.github.snuffix.recruitmenttask.data.repository.DocumentsLocalSource
import org.koin.dsl.module

val cacheModule = module {
    single { DocumentsDatabase.getInstance(get()) }
    single { CachedDocumentsMapper() }
    factory<DocumentsLocalSource> { DocumentsLocalSourceImpl(get(), get()) }
}
