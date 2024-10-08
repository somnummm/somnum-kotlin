package com.example.somnum.ui.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.somnum.data.repository.Planner
import com.example.somnum.ui.components.CalendarComponent
import com.example.somnum.ui.viewmodel.PlannerViewModel
import kotlinx.datetime.LocalDateTime
import java.time.LocalDate
import java.time.format.DateTimeFormatter

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

        SelectedDateDisplay(selectedDate, plannings)
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun SelectedDateDisplay(date: LocalDate, plannings: List<Planner>) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.secondaryContainer
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "Date sélectionnée :",
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = date.format(DateTimeFormatter.ofPattern("dd MMMM yyyy")),
                style = MaterialTheme.typography.bodyLarge
            )

            Spacer(modifier = Modifier.height(8.dp))

            if (plannings.isEmpty()) {
                Text(
                    text = "Aucun planning pour cette date",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSecondaryContainer.copy(alpha = 0.6f)
                )
            } else {
                plannings.forEach { planner ->
                    PlanningItem(planner)
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun PlanningItem(planner: Planner) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Column(
            modifier = Modifier.padding(8.dp)
        ) {
            Text(
                text = "Horaires de sommeil",
                style = MaterialTheme.typography.titleSmall
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Couché : ${formatSleepTime(planner.sleepTime)}",
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = "Réveil : ${formatSleepTime(planner.wakeTime)}",
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

fun formatSleepTime(sleepTime: String): String {
    val timePart = sleepTime.split("T")[1]
    return timePart.substring(0, 5)
}

