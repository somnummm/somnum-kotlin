package com.example.somnum.data.repository

class UserRepositoryImpl(
    private val supabaseClient: SupabaseClient // Injecter via DI
) : UserRepository {

    override suspend fun login(email: String, password: String): Result<User> {
        return try {
            val response = supabaseClient.auth.signInWithPassword(email, password)
            if (response.user != null) {
                Result.success(response.user)
            } else {
                Result.failure(Exception("Login failed"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}