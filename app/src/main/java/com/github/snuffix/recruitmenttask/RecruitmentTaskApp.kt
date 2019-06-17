package com.github.snuffix.recruitmenttask

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import com.github.snuffix.recruitmenttask.cache.di.cacheModule
import com.github.snuffix.recruitmenttask.data.di.dataModule
import com.github.snuffix.recruitmenttask.data.model.DocumentStorePathEntity
import com.github.snuffix.recruitmenttask.data.repository.FileStorage
import com.github.snuffix.recruitmenttask.domain.di.domainModule
import com.github.snuffix.recruitmenttask.domain.usecase.CoroutinesDispatcherProvider
import com.github.snuffix.recruitmenttask.mapper.DocumentsMapper
import com.github.snuffix.recruitmenttask.presentation.di.presentationModule
import com.github.snuffix.recruitmenttask.remote.di.remoteModule
import com.github.snuffix.recruitmenttask.remote.network.service.NetworkCheck
import com.github.snuffix.recruitmenttask.remote.network.service.NetworkConfiguration
import kotlinx.coroutines.Dispatchers
import net.danlew.android.joda.JodaTimeAndroid
import org.apache.commons.io.FileUtils
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.dsl.module
import java.io.File
import java.io.IOException
import java.io.InputStream

@Suppress("unused")
open class RecruitmentTaskApp : Application() {

    override fun onCreate() {
        super.onCreate()

        JodaTimeAndroid.init(this)

        startKoin {
            // Android context
            androidContext(this@RecruitmentTaskApp)
            // modules

            val productionModules = listOf(
                applicationModule,
                cacheModule,
                remoteModule,
                dataModule,
                domainModule,
                presentationModule,
                uiModule
            )

            modules(productionModules)
        }
    }

    private val applicationModule = module {
        single {
            CoroutinesDispatcherProvider(
                main = Dispatchers.Main,
                computation = Dispatchers.Default,
                io = Dispatchers.IO
            )
        }
        single<NetworkCheck> {
            object : NetworkCheck {
                val connectivityManager = androidApplication().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

                override fun isOnline(): Boolean {
                    val netInfo = connectivityManager.activeNetworkInfo
                    return (netInfo != null && netInfo.isConnected)
                }
            }
        }
        single<NetworkConfiguration> {
            object : NetworkConfiguration {
                override val cacheDir = androidApplication().cacheDir
                override val isDebug = BuildConfig.DEBUG
            }
        }
        single<FileStorage> {
            object : FileStorage {
                override suspend fun storeFile(file: InputStream): DocumentStorePathEntity {
                    val cacheDir = androidApplication().cacheDir
                    val storePath = "$cacheDir-document.pdf"
                    FileUtils.copyInputStreamToFile(file, File(storePath))
                    return DocumentStorePathEntity(storePath)
                }
            }
        }
    }

    private val uiModule = module {
        single { DocumentsMapper() }
    }
}

