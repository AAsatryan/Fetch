package com.project.fetch.api

import com.project.fetch.list.ListItemInfo
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {

    @GET("hiring.json")
    suspend fun getItemsList(): Response<List<ListItemInfo>>
}