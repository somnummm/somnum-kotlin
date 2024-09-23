package com.example.somnum.data.repository

import io.github.jan.supabase.gotrue.user.UserInfo

interface UserRepository {
    suspend fun login(email: String, password: String)
    suspend fun signUp(email: String, password: String)
    suspend fun getUser(): UserInfo
    suspend fun logout()
}