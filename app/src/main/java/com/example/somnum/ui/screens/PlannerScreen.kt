package com.example.somnum.ui.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.somnum.ui.components.planner.CalendarComponent
import com.example.somnum.ui.viewmodel.PlannerViewModel
import com.example.somnum.ui.components.planner.DateSelector

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun PlannerScreen(
    modifier: Modifier = Modifier,
    viewModel: PlannerViewModel
) {
    val selectedDate by viewModel.selectedDate.collectAsState()
    val plannings by viewModel.plannings.collectAsState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        CalendarComponent(
            selectedDate = selectedDate,
            onDateSelected = { newDate ->
                viewModel.onDateSelected(newDate)
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        DateSelector(selectedDate, plannings,viewModel)
    }
}
