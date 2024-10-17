package com.example.somnum.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.somnum.data.repository.AuthRepository
import kotlinx.coroutines.launch

class SignUpViewModel(private val authRepository: AuthRepository = AuthRepository()) : ViewModel() {

    fun signUp(email: String, password: String, onResult: (String?) -> Unit) {
        viewModelScope.launch {
            val result = authRepository.signUpWithEmail(email, password)
            onResult(result)
        }
    }
}
