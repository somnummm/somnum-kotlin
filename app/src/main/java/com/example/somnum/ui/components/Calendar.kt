package com.example.somnum.ui.components

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.*

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CalendarComponent(
    selectedDate: LocalDate,
    onDateSelected: (LocalDate) -> Unit
) {
    var currentMonth by remember { mutableStateOf(YearMonth.from(selectedDate)) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        // En-tête du calendrier
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = {
                currentMonth = currentMonth.minusMonths(1)
            }) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                    contentDescription = "Mois précédent"
                )
            }

            Text(
                text = currentMonth.format(DateTimeFormatter.ofPattern("MMMM yyyy"))
                    .replaceFirstChar { it.uppercase() },
                style = MaterialTheme.typography.titleLarge
            )

            IconButton(onClick = {
                currentMonth = currentMonth.plusMonths(1)
            }) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                    contentDescription = "Mois suivant"
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Jours de la semaine
        Row(modifier = Modifier.fillMaxWidth()) {
            val daysOfWeek = DayOfWeek.values()
            for (dayIndex in 0..6) {
                val dayOfWeek = daysOfWeek[dayIndex % 7]
                Text(
                    text = dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.getDefault()),
                    modifier = Modifier.weight(1f),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Grille des jours
        val firstDayOfMonth = currentMonth.atDay(1)
        val daysInMonth = currentMonth.lengthOfMonth()
        val startOffset = (firstDayOfMonth.dayOfWeek.value - 1) % 7

        LazyVerticalGrid(
            columns = GridCells.Fixed(7),
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            items(startOffset) {
                Box(modifier = Modifier.size(40.dp))
            }

            items(daysInMonth) { day ->
                val date = currentMonth.atDay(day + 1)
                val isSelected = date == selectedDate

                Surface(
                    modifier = Modifier.size(40.dp),
                    shape = MaterialTheme.shapes.small,
                    color = if (isSelected)
                        MaterialTheme.colorScheme.primaryContainer
                    else
                        MaterialTheme.colorScheme.surface,
                    onClick = { onDateSelected(date) }
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Text(
                            text = (day + 1).toString(),
                            style = MaterialTheme.typography.bodyMedium,
                            color = if (isSelected)
                                MaterialTheme.colorScheme.onPrimaryContainer
                            else
                                MaterialTheme.colorScheme.onSurface
                        )
                    }
                }
            }
        }
    }
}