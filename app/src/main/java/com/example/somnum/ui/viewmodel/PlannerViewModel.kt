package com.example.somnum.ui.viewmodel

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.somnum.data.entities.Planner
import com.example.somnum.data.network.supabase
import com.example.somnum.data.repository.PlannerRepository
import com.example.somnum.viewmodel.ProfileViewModel
import io.github.jan.supabase.gotrue.auth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate

class PlannerViewModel(
    private val plannerRepository: PlannerRepository = PlannerRepository()
) : ViewModel() {

    @RequiresApi(Build.VERSION_CODES.O)
    private val _selectedDate = MutableStateFlow(LocalDate.now())

    @RequiresApi(Build.VERSION_CODES.O)
    val selectedDate = _selectedDate.asStateFlow()

    private val _plannings = MutableStateFlow<List<Planner?>>(emptyList())
    val plannings = _plannings.asStateFlow()

    val userId = supabase.auth.currentUserOrNull()?.id

    @RequiresApi(Build.VERSION_CODES.O)
    fun onDateSelected(date: LocalDate) {
        _selectedDate.value = date
        loadPlanningsForDate(date)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun loadPlanningsForDate(date: LocalDate) {
        viewModelScope.launch {
            try {
                //TODO : créer une classe pour le user
                val dailyPlannings = userId?.let {
                    plannerRepository.fetchPlanningForADay(
                        date,
                        it
                    )
                }
                if (dailyPlannings != null) {
                    _plannings.value = dailyPlannings
                }
            } catch (e: Exception) {
                _plannings.value = emptyList()
            }
        }
    }

    fun createPlanning(date: LocalDate, sleepTime: String, wakeTime: String) {
        val newPlanning = userId?.let {
            Planner(
                date = date.toString(),
                sleepTime = sleepTime,
                wakeTime = wakeTime,
                userId = it
            )
        }
        viewModelScope.launch {
            if (newPlanning != null) {
                plannerRepository.createPlanning(newPlanning)
            }
            //add to plannings
            _plannings.value += newPlanning
        }
    }
}
