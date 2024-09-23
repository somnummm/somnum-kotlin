package com.example.somnum.data.remote

import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.gotrue.auth
import io.github.jan.supabase.gotrue.providers.builtin.Email
import io.github.jan.supabase.gotrue.user.UserInfo
import io.github.jan.supabase.postgrest.Postgrest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SupabaseClient {

    private val supabaseUrl =
        "https://wyfrzcwsrqkrwqqhgbhd.supabase.co"
    private val supabaseKey =
        "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6Ind5ZnJ6Y3dzcnFrcndxcWhnYmhkIiwicm9sZSI6ImFub24iLCJpYXQiOjE3MTMyNzQzNjAsImV4cCI6MjAyODg1MDM2MH0.KnAOLtglCWZNxasBc1pmJJI0edx027o2vxSD8pymkTc" // Remplace par ta clé anonyme Supabase

    val client = createSupabaseClient(
        supabaseUrl = supabaseUrl,
        supabaseKey = supabaseKey
    ) {
        install(Postgrest)
    }

    suspend fun login(mail: String, passwd: String) {
        return withContext(Dispatchers.IO) {
            client.auth.signInWith(Email) {
                email = mail
                password = passwd
            }
        }
    }

    suspend fun signUp(mail: String, passwd: String) {
        return withContext(Dispatchers.IO) {
            client.auth.signUpWith(Email) {
                email = mail
                password = passwd
            }
        }
    }

    suspend fun getUser(): UserInfo {
        return client.auth.retrieveUserForCurrentSession(updateSession = true)
    }

    suspend fun logout() {
        return client.auth.signOut()
    }
}