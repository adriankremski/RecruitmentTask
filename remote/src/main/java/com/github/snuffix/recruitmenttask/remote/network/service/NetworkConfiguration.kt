package com.github.snuffix.recruitmenttask.remote.network.service

import java.io.File

interface NetworkConfiguration {
    val cacheDir: File
    val isDebug: Boolean
}