package com.example.somnum.data.repository

import com.example.somnum.data.network.supabase
import io.github.jan.supabase.gotrue.auth
import io.github.jan.supabase.gotrue.providers.builtin.Email

class AuthRepository {

    suspend fun loginWithEmail(mail: String, passwd: String): String? {
        if (mail.isEmpty() || passwd.isEmpty()) {
            return "Email ou mot de passe ne peut pas être vide."
        }

        return try {
            supabase.auth.signInWith(Email) {
                email = mail
                password = passwd
            }
            if (supabase.auth.currentUserOrNull() == null) {
                "Connexion échouée. Veuillez vérifier votre email et mot de passe."
            } else {
                null
            }
        } catch (e: Exception) {
            when (e.message) {
                else -> "Connexion échouée. Veuillez vérifier votre email et mot de passe."
            }
        }
    }

    suspend fun signUpWithEmail(mail: String, passwd: String): Boolean {
        supabase.auth.signUpWith(Email) {
            email = mail
            password = passwd
        }
        return supabase.auth.currentUserOrNull() != null
    }

    suspend fun signOut() {
        supabase.auth.signOut()
    }

    fun isLogged(): Boolean {
        return supabase.auth.currentUserOrNull() != null
    }
}