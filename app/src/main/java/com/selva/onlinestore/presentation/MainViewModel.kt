package com.selva.onlinestore.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.selva.onlinestore.domain.ProductRepository
import com.selva.onlinestore.screen.UiEvent
import com.selva.onlinestore.screen.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: ProductRepository
) : ViewModel() {

    private val _duckUrlState = MutableStateFlow(UiState())
    val duckUrlState: StateFlow<UiState>
        get() = _duckUrlState

    fun handleEvent(event: UiEvent) {
        when (event) {
            is UiEvent.GetDuckUrl -> getDuckUrl()
        }
    }

    private fun getDuckUrl() {
        viewModelScope.launch {
            _duckUrlState.update {
                it.copy(isLoading = true)
            }

            repository.getProductFromServer().collect { result ->
                result.message?.let { error ->
                    _duckUrlState.update {
                        it.copy(
                            isLoading = false,
                            error = error,
                            url = ""
                        )
                    }
                    return@collect
                } ?: result.data?.let { duckItem ->
                    Log.i(TAG, "url - ${duckItem.url}")
                    _duckUrlState.update {
                        it.copy(
                            isLoading = false,
                            url = duckItem.url
                        )
                    }
                }
            }
        }
    }

    companion object {
        const val TAG = "MainViewModel"
    }
}