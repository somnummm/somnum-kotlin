package com.example.somnum.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.somnum.data.repository.AuthRepository
import kotlinx.coroutines.launch

class LoginViewModel(private val authRepository: AuthRepository = AuthRepository()) : ViewModel() {

    fun login(email: String, password: String, onResult: (String?) -> Unit) {
        viewModelScope.launch {
            val result = authRepository.loginWithEmail(email, password)
            onResult(result)
        }
    }

    fun isLogged(): Boolean {
        return authRepository.isLogged()
    }

    fun logout() {
        viewModelScope.launch { authRepository.signOut() }
    }

}