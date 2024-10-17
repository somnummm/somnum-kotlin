package com.example.somnum.viewmodel

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.somnum.data.entities.UserProfile
import com.example.somnum.data.network.supabase
import com.example.somnum.data.repository.UserRepository
import io.github.jan.supabase.gotrue.auth
import io.github.jan.supabase.postgrest.postgrest
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val userRepository: UserRepository = UserRepository()
) : ViewModel() {

    private val userId = supabase.auth.currentUserOrNull()?.id

    private var _userProfileInfos: UserProfile? = null
    val userProfileInfos: UserProfile?
        get() = _userProfileInfos

    var isLoading = mutableStateOf(true)
        private set

    fun loadUserProfile() {
        isLoading.value = true
        viewModelScope.launch {
            try {
                _userProfileInfos = userId?.let { userRepository.getUserInfo(it) }
                if (_userProfileInfos != null) {
                    Log.d("ProfileViewModel", "User profile: $_userProfileInfos")
                } else {
                    Log.e("ProfileViewModel", "Impossible de charger le profil utilisateur.")
                }
            } catch (e: Exception) {
                Log.e(
                    "ProfileViewModel",
                    "Erreur lors du chargement du profil utilisateur : ${e.message}"
                )
                e.printStackTrace()
            } finally {
                isLoading.value = false
            }
        }
    }

    suspend fun saveProfileChanges(profile: UserProfile?) {
        try {
            if (userId != null) {
                val updateData = mutableMapOf<String, String>()

                if (profile != null) {
                    profile.fullName?.let { updateData["fullName"] = it }
                    profile.bio?.let { updateData["bio"] = it }
                    profile.avatar?.let { updateData["avatarUrl"] = it }
                }

                if (updateData.isNotEmpty()) {
                    supabase.postgrest["UserInfo"]
                        .update(updateData) {
                            filter { eq("uuid", userId) }
                        }
                } else {
                    Log.d("ProfileViewModel", "Aucune donnée à mettre à jour.")
                }
            } else {
                Log.e("ProfileViewModel", "Impossible de mettre à jour : ID utilisateur manquant.")
            }
        } catch (e: Exception) {
            e.printStackTrace()

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