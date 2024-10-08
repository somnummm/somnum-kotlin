package com.example.somnum.data.entities

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Planner(
    val id: Int? = null,
    val date: String,
    @SerialName("sleepTime")
    val sleepTime: String,
    @SerialName("wakeTime")
    val wakeTime: String,
    @SerialName("createdAt")
    val createdAt: String? = null,
    @SerialName("userId")
    val userId: String
)