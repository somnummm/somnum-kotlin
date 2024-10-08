package com.example.somnum.ui.components.hardware
import androidx.compose.material3.MaterialTheme
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

enum class SleepState {
    AWAKE, REM, CORE, DEEP
}

data class SleepPhase(
    val state: SleepState,
    val durationMinutes: Long,
    val startTimeMillis: Long
)

class SleepSimulation {
    private val phases = mutableListOf<SleepPhase>()
    private var currentTimeMillis = System.currentTimeMillis()

    fun simulate(totalSleepHours: Int): List<SleepPhase> {
        phases.clear()
        currentTimeMillis = System.currentTimeMillis()

        // Phase d'endormissement
        addPhase(SleepState.AWAKE, (10..20).random().toLong())

        var remainingMinutes = totalSleepHours * 60
        while (remainingMinutes > 0) {
            val cycle = listOf(
                SleepState.CORE to (40..50).random().toLong(),
                SleepState.DEEP to (20..40).random().toLong(),
                SleepState.CORE to (20..30).random().toLong(),
                SleepState.REM to (15..25).random().toLong()
            )

            for ((state, duration) in cycle) {
                if (remainingMinutes <= 0) break
                val actualDuration = minOf(duration, remainingMinutes.toLong())
                addPhase(state, actualDuration)
                remainingMinutes -= actualDuration.toInt()
            }
        }

        addPhase(SleepState.AWAKE, (5..15).random().toLong())
        return phases.toList()
    }

    private fun addPhase(state: SleepState, durationMinutes: Long) {
        phases.add(SleepPhase(state, durationMinutes, currentTimeMillis))
        currentTimeMillis += durationMinutes * 60 * 1000
    }

    fun getStateDurations(phases: List<SleepPhase>): Map<SleepState, Long> {
        return phases.groupBy { it.state }
            .mapValues { (_, phaseList) ->
                phaseList.sumOf { it.durationMinutes }
            }
    }
}

@Composable
fun SleepSimulationComponent(
    hours: Int,
    modifier: Modifier = Modifier
) {
    var phases by remember { mutableStateOf<List<SleepPhase>>(emptyList()) }
    var durations by remember { mutableStateOf<Map<SleepState, Long>>(emptyMap()) }

    val simulation = remember { SleepSimulation() }

    // Récupérer les couleurs du thème en dehors du Canvas
    val awakeColor = MaterialTheme.colorScheme.outline
    val remColor = MaterialTheme.colorScheme.tertiary
    val coreColor = MaterialTheme.colorScheme.secondary
    val deepColor = MaterialTheme.colorScheme.primary

    LaunchedEffect(hours) {
        val simulatedPhases = simulation.simulate(hours)
        phases = simulatedPhases
        durations = simulation.getStateDurations(simulatedPhases)
    }

    Column(modifier = modifier.fillMaxWidth()) {
        Text(
            text = "Simulation de Sommeil ($hours heures)",
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        if (phases.isNotEmpty()) {
            Canvas(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
                    .padding(vertical = 8.dp)
            ) {
                val width = size.width
                val height = size.height
                val totalDuration = phases.sumOf { it.durationMinutes }

                var xOffset = 0f
                phases.forEach { phase ->
                    val phaseWidth = (phase.durationMinutes.toFloat() / totalDuration.toFloat()) * width
                    val color = when (phase.state) {
                        SleepState.AWAKE -> awakeColor
                        SleepState.REM -> remColor
                        SleepState.CORE -> coreColor
                        SleepState.DEEP -> deepColor
                    }

                    drawRect(
                        color = color,
                        topLeft = Offset(xOffset, 0f),
                        size = Size(phaseWidth, height)
                    )
                    xOffset += phaseWidth
                }
            }

            Column(modifier = Modifier.padding(top = 16.dp)) {
                SleepState.values().forEach { state ->
                    val duration = durations[state] ?: 0
                    val color = when (state) {
                        SleepState.AWAKE -> awakeColor
                        SleepState.REM -> remColor
                        SleepState.CORE -> coreColor
                        SleepState.DEEP -> deepColor
                    }

                    Row(
                        modifier = Modifier
                            .padding(vertical = 4.dp)
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = state.name,
                            color = color,
                            style = MaterialTheme.typography.bodyLarge
                        )
                        Text(
                            text = "$duration minutes",
                            color = color,
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                }
            }

            Text(
                text = "Durée totale: ${phases.sumOf { it.durationMinutes }} minutes",
                modifier = Modifier.padding(top = 16.dp),
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold
            )
        }
    }
}