package com.example.somnum.ui.screens

import android.Manifest
import android.content.pm.PackageManager
import android.media.MediaRecorder
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.somnum.ui.components.hardware.MediaRecorderDemo
import com.example.somnum.ui.components.hardware.SleepSimulationComponent
import com.example.somnum.ui.components.typography.Title

@Composable
fun SimulationScreen(modifier: Modifier = Modifier) {
    var simulationStarted by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Title(title = "Simulation")
        Text(text="Simuler une nuit de sommeil")

        Button(
            onClick = { simulationStarted = true },
            shape = CircleShape,
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary
            ),
            modifier = Modifier
                .size(120.dp)
                .clip(CircleShape)
        ) {
            Text(
                text = "Démarrer une nuit",
                style = MaterialTheme.typography.labelLarge
            )
        }

        if (simulationStarted) {
            SleepSimulationComponent(
                hours = 8,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}
