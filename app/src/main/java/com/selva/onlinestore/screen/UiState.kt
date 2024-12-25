package com.selva.onlinestore.screen

data class UiState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val url: String? = null
)