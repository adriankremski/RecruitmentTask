package com.github.snuffix.recruitmenttask.remote.network.service

import kotlinx.coroutines.Deferred
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url
import java.io.IOException

interface DocumentsService {
    @GET
    @Throws(Exception::class)
    suspend fun getFile(@Url url: String): Deferred<Response<ResponseBody>>
}
