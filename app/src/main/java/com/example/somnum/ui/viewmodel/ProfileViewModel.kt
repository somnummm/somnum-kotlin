package com.example.somnum.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.somnum.data.network.supabase
import com.example.somnum.data.repository.AuthRepository
import io.github.jan.supabase.gotrue.auth
import com.example.somnum.data.entities.UserProfile
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.slf4j.MDC.put

class ProfileViewModel(private val authRepository: AuthRepository = AuthRepository()) :
    ViewModel() {

    val _userProfile = MutableStateFlow<UserProfile?>(null)
    val userProfile: StateFlow<UserProfile?> get() = _userProfile

    private var currentUser = supabase.auth.currentUserOrNull()

    fun loadUserProfile() {
        viewModelScope.launch {
            currentUser?.let { user ->
                val metadata = user.userMetadata ?: return@launch
                _userProfile.value = UserProfile(
                    id = user.id,
                    email = user.email ?: "",
                    fullName = metadata["fullName"] as? String,
                    bio = metadata["bio"] as? String,
                    avatarUrl = metadata["avatarUrl"] as? String,
                    createdAt = user.createdAt.toString(),
                    updatedAt = user.updatedAt.toString(),
                    bedTime = metadata["bedTime"] as? String,
                    wakeUpTime = metadata["wakeUpTime"] as? String,
                    lastSleepDuration = metadata["lastSleepDuration"] as? Int,
                    avgSleepDuration = metadata["avgSleepDuration"] as? Int,
                    sleepQuality = metadata["sleepQuality"] as? String,
                    sleepCycles = metadata["sleepCycles"] as? Map<String, Int>,
                    sleepGoals = metadata["sleepGoals"] as? String,
                    preferredWakeupType = metadata["preferredWakeupType"] as? String,
                    sleepNotes = metadata["sleepNotes"] as? List<String>
                )
            }
        }
    }

    fun saveProfileChanges(
    ) {
        viewModelScope.launch {
            _userProfile.value?.let { profile ->
                try {
                    supabase.auth.modifyUser {
                        data {
                            put("fullName", profile.fullName)
                            put("bio", profile.bio)
                            put("avatarUrl", profile.avatarUrl)
                            put("bedTime", profile.bedTime)
                            put("wakeUpTime", profile.wakeUpTime)
                            put("lastSleepDuration", profile.lastSleepDuration?.toString())
                            put("avgSleepDuration", profile.avgSleepDuration?.toString())
                            put("sleepQuality", profile.sleepQuality)
                            put("sleepCycles", profile.sleepCycles?.toString())
                            put("sleepGoals", profile.sleepGoals)
                            put("preferredWakeupType", profile.preferredWakeupType)
                            put("sleepNotes", profile.sleepNotes?.toString())
                        }
                    }
                    Log.i("ProfileViewModel", "Profile updated!")
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }

    fun logout() {
        viewModelScope.launch {
            authRepository.signOut()
        }
    }
}