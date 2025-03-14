package com.project.fetch.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.fetch.api.DataRepository
import com.project.fetch.api.NetworkState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ListViewModel(
    private val repository: DataRepository
) : ViewModel() {

    private val _screenState = MutableStateFlow<ScreenState>(ScreenState.Loading)
    val screenState: StateFlow<ScreenState> = _screenState.asStateFlow()

    init {
        getListFromApi()
    }

    private fun getListFromApi() {
        viewModelScope.launch {
            _screenState.value = ScreenState.Loading
            when (val result = repository.getItemsList()) {
                is NetworkState.Success -> {
                    val transformedList = result.data
                        .filter { !it.name.isNullOrBlank() }
                        .sortedWith(compareBy<ListItemInfo> { it.listId }.thenBy { it.name })
                        .groupBy { it.listId }

                    _screenState.value = ScreenState.Success(transformedList)
                }

                is NetworkState.Error -> {
                    _screenState.value = ScreenState.Error(result.message)
                }

                is NetworkState.NoInternetConnection -> {
                    _screenState.value = ScreenState.NoInternet
                }
            }

        }
    }

    fun retryRequest(){
        getListFromApi()
    }
}