package com.project.fetch.list

sealed class ScreenState {

    object Loading : ScreenState()
    object NoInternet : ScreenState()
    data class Success(val items: Map<Int, List<ListItemInfo>>) : ScreenState()
    data class Error(val message: String) : ScreenState()

}