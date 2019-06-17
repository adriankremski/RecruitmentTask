package com.github.snuffix.recruitmenttask.remote.network.service

import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url

interface DocumentsService {
    @GET
    suspend fun getFile(@Url url: String): Response<ResponseBody>
}
