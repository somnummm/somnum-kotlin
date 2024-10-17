package com.example.somnum.data.repository

import com.example.somnum.data.network.supabase
import io.github.jan.supabase.gotrue.auth
import io.github.jan.supabase.gotrue.providers.builtin.Email
import io.github.jan.supabase.postgrest.postgrest

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

    suspend fun signUpWithEmail(mail: String, passwd: String): String? {
        if (mail.isEmpty() || passwd.isEmpty()) {
            return "Email ou mot de passe ne peut pas être vide."
        }

        return try {
            supabase.auth.signUpWith(Email) {
                email = mail
                password = passwd
            }
            val userId = supabase.auth.currentUserOrNull()?.id
            if (userId != null) {
                supabase.postgrest["UserInfo"]
                    .upsert(
                        mapOf(
                            "uuid" to userId,

                        )
                    )
            }
            return null
        } catch (e: Exception) {
            when (e.message) {
                else -> e.message
            }
        }
    }

    suspend fun signOut() {
        supabase.auth.signOut()
    }

    fun isLogged(): Boolean {
        return supabase.auth.currentUserOrNull() != null
    }
}