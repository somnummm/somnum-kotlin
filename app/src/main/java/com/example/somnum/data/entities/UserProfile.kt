package com.example.somnum.data.entities

data class UserProfile(
    val id: String,
    val email: String,
    val fullName: String? = null,
    val bio: String? = null,
    val avatarUrl: String? = null,
    val createdAt: String,
    val updatedAt: String,
    val bedTime: String? = null,
    val wakeUpTime: String? = null,
    val lastSleepDuration: Int? = null,
    val avgSleepDuration: Int? = null,
    val sleepQuality: String? = null,
    val sleepCycles: Map<String, Int>? = null,
    val sleepGoals: String? = null,
    val preferredWakeupType: String? = null,
    val sleepNotes: List<String>? = null
)