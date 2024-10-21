package com.example.somnum.data.entities

import kotlinx.serialization.Serializable

@Serializable
data class UserProfile(
    val uuid: String?,
    val fullName: String?,
    val bio: String?,
    val avatar: String?,
)