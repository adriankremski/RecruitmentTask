package com.github.snuffix.recruitmenttask.remote.network.service

import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Streaming
import retrofit2.http.Url

const val HEADER_ACCEPT_DOWNLOAD_PDF = "Accept: application/pdf"

interface DocumentsService {
    @Streaming
    @Headers(HEADER_ACCEPT_DOWNLOAD_PDF)
    @GET
    @Throws(Exception::class)
    suspend fun getFile(@Url url: String): Response<ResponseBody>
}
