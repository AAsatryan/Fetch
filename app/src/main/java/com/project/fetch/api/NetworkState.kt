package com.project.fetch.api

sealed class NetworkState<out T> {
    data class Success<out T>(val data: T) : NetworkState<T>()
    data class Error(val message: String, val errorCode: Int? = null) : NetworkState<Nothing>()
    object NoInternetConnection : NetworkState<Nothing>()
}