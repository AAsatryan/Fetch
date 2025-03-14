package com.project.fetch.api

import com.project.fetch.list.ListItemInfo
import java.net.SocketTimeoutException
import java.net.UnknownHostException

class DataRepository(private val apiService: ApiService) {

    suspend fun getItemsList(): NetworkState<List<ListItemInfo>> {
        return try {
            val response = apiService.getItemsList()
            if (response.isSuccessful) {
                response.body()?.let {
                    NetworkState.Success(it)
                } ?: NetworkState.Error("Empty response", response.code())
            } else {
                NetworkState.Error("Error: ${response.message()}", response.code())
            }
        } catch (_: UnknownHostException) {
            NetworkState.NoInternetConnection
        } catch (_: SocketTimeoutException) {
            NetworkState.Error("Request Timeout", null)
        } catch (e: Exception) {
            NetworkState.Error("Network error: ${e.localizedMessage}")
        }
    }
}