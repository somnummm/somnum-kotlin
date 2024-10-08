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
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.somnum.data.entities.Planner

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
                text = "Night hours : ",
                style = MaterialTheme.typography.titleSmall
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Bed time : ${formatSleepTime(planner.sleepTime)}",
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = "Wake up time : ${formatSleepTime(planner.wakeTime)}",
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

fun formatSleepTime(sleepTime: String): String {
    return sleepTime.substring(0, 5)
}