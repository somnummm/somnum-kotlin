package com.example.somnum.data.repository


import com.example.somnum.data.remote.SupabaseClient
import io.github.jan.supabase.gotrue.user.UserInfo

class UserRepositoryImpl(
    private val supabaseClient: SupabaseClient // Injecter via DI
) : UserRepository {

    override suspend fun login(email: String, password: String) {
        return supabaseClient.login(email, password)
    }

    override suspend fun signUp(email: String, password: String) {
        return supabaseClient.signUp(email, password)
    }

    override suspend fun getUser(): UserInfo {
        return supabaseClient.getUser()
    }

    override suspend fun logout() {
        return supabaseClient.logout()
    }
}