package com.example.courses.ui.login

sealed class LoginUiState {
    data object Initial : LoginUiState()
    data object EnterLoading : LoginUiState()
    data object EnterSuccess : LoginUiState()
    data class EnterError(val message: String) : LoginUiState()
}