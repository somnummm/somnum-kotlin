package com.example.somnum.ui.components.planner

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.somnum.data.entities.Planner
import com.example.somnum.ui.viewmodel.PlannerViewModel
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DateSelector(date: LocalDate, plannings: List<Planner?>, viewModel: PlannerViewModel) {
    var timeSelector by remember { mutableStateOf(false) }

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
                text = "Night of :",
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = date.format(DateTimeFormatter.ofPattern("dd MMMM yyyy")),
                style = MaterialTheme.typography.bodyLarge
            )

            Spacer(modifier = Modifier.height(8.dp))

            if (plannings.isEmpty()) {
                SleepScheduleSelector(
                    timeSelector = timeSelector,
                    onTimeSelectorChange = { timeSelector = it },
                    date,
                    viewModel
                )
            } else {
                plannings[0]?.let { PlanningItem(it, viewModel) }
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}
