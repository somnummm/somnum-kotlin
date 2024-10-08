package com.example.somnum.ui.viewmodel

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.somnum.data.repository.Planner
import com.example.somnum.data.repository.PlannerRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate

class PlannerViewModel(private val plannerRepository: PlannerRepository = PlannerRepository()): ViewModel() {

    @RequiresApi(Build.VERSION_CODES.O)
    private val _selectedDate = MutableStateFlow(LocalDate.now())
    @RequiresApi(Build.VERSION_CODES.O)
    val selectedDate = _selectedDate.asStateFlow()

    private val _plannings = MutableStateFlow<List<Planner>>(emptyList())
    val plannings = _plannings.asStateFlow()

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
                val userId = "11c73277-1a06-4ab4-9d24-4eae0ae329d3"
                val dailyPlannings = plannerRepository.fetchPlanningForADay(date,userId)
                _plannings.value = dailyPlannings
            } catch (e: Exception) {
                _plannings.value = emptyList()
            }
        }
    }
}
