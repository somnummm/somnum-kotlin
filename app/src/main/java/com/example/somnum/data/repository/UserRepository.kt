package com.example.somnum.data.repository

import android.util.Log
import com.example.somnum.data.entities.UserProfile
import com.example.somnum.data.network.supabase
import io.github.jan.supabase.postgrest.postgrest

class UserRepository {
    suspend fun getUserInfo(userId: String): UserProfile {
        val result = supabase.postgrest["UserInfo"]
            .select() {
                filter {
                    eq("uuid", userId)
                }
            }
            .decodeSingle<UserProfile>()
        Log.d("UserRepository", "User profile: $result")
        return result
    }
}