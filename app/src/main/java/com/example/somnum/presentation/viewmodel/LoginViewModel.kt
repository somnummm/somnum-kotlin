package com.example.somnum.presentation.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class LoginViewModel(
    private val userRepository: UserRepository // Injecter le repository via DI
) : ViewModel() {

    var email by mutableStateOf("")
        private set

    var password by mutableStateOf("")
        private set

    var loginState by mutableStateOf<LoginState>(LoginState.Idle)
        private set

    fun onEmailChange(newEmail: String) {
        email = newEmail
    }

    fun onPasswordChange(newPassword: String) {
        password = newPassword
    }

    fun login() {
        viewModelScope.launch {
            loginState = LoginState.Loading
            try {
                val result = userRepository.login(email, password)
                if (result.isSuccess) {
                    loginState = LoginState.Success
                } else {
                    loginState = LoginState.Error("Invalid credentials")
                }
            } catch (e: Exception) {
                loginState = LoginState.Error(e.localizedMessage ?: "Unknown error")
            }
        }
    }

    // États de login
    sealed class LoginState {
        object Idle : LoginState()
        object Loading : LoginState()
        object Success : LoginState()
        data class Error(val message: String) : LoginState()
    }
}