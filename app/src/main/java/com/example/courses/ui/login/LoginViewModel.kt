package com.example.courses.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.util.CoursesLogger
import com.example.core.util.SharedPrefsUtils
import com.example.data.repository.AuthRepositoryImpl
import com.example.domain.repository.AuthRepository
import com.example.core.util.ui.isValidEmail
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {
    private val _email = MutableLiveData("")
    private val _password = MutableLiveData("")

    private val _uiState : MutableLiveData<LoginUiState> = MutableLiveData(LoginUiState.Initial)
    val uiState: LiveData<LoginUiState> get() = _uiState

    private val _isFormValid = MutableLiveData(false)
    val isFormValid: LiveData<Boolean> get() = _isFormValid

    fun onEmailChanged(email: String) {
        _email.value = email
        validate()
    }

    fun onPasswordChanged(password: String) {
        _password.value = password
        validate()
    }

    private fun validate() {
        val email = _email.value ?: ""
        val password = _password.value ?: ""
        _isFormValid.value = isValidEmail(email.trim()) && password.trim().length >= 8
    }

    fun login(email: String, password: String) {
        viewModelScope.launch {
            _uiState.value = LoginUiState.EnterLoading
            authRepository.login(email.trim(), password.trim()).fold(
                onSuccess = {
                    _uiState.value = LoginUiState.EnterSuccess
                },
                onFailure = {
                    CoursesLogger.d("Login failed: ${it.message}")
                    _uiState.value = LoginUiState.EnterError(it.message ?: "Unknown error")
                },
            )
        }
    }
}
