package com.example.somnum.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.somnum.data.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    fun login(email: String, password: String) {
        viewModelScope.launch {
            val response = userRepository.login(email, password)
            // Gérer la réponse ici
        }
    }

    fun signUp(email: String, password: String) {
        viewModelScope.launch {
            val response = userRepository.signUp(email, password)
            // Gérer la réponse ici
        }
    }
}