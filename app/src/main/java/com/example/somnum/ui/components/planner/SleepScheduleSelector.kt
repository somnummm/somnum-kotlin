package com.example.somnum.ui.components.planner

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.somnum.ui.components.pickers.TimePicker
import com.example.somnum.ui.viewmodel.PlannerViewModel
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun SleepScheduleSelector(
    timeSelector: Boolean,
    onTimeSelectorChange: (Boolean) -> Unit,
    date: LocalDate,
    viewModel: PlannerViewModel
) {
    var bedTime by remember { mutableStateOf(LocalTime.of(22, 0)) }
    var wakeUpTime by remember { mutableStateOf(LocalTime.of(7, 0)) }
    val nextDay = date.plusDays(1)

    if (!timeSelector) {
        Column {
            Text(text = "Bed time of " + date.format(DateTimeFormatter.ofPattern("dd MMMM yyyy")) + ":")
            TimePicker(
                initialTime = bedTime,
                onTimeSelected = { newTime ->
                    bedTime = newTime
                }
            )
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                Button(
                    onClick = { onTimeSelectorChange(true) }
                ) {
                    Text("Next")
                }
            }
        }
    } else {
        Column {
            Text(text = "Wake up time of " + nextDay.format(DateTimeFormatter.ofPattern("dd MMMM yyyy"))  + ":")
            TimePicker(
                initialTime = wakeUpTime,
                onTimeSelected = { newTime ->
                    wakeUpTime = newTime
                }
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Button(
                    onClick = { onTimeSelectorChange(false) }
                ) {
                    Text("Go back")
                }
                Button(
                    onClick = {
                        viewModel.createPlanning(date, bedTime.toString(), wakeUpTime.toString())
                    }
                ) {
                    Text("Confirm")
                }
            }
        }
    }
}