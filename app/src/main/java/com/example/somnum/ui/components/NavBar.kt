package com.example.somnum.ui.components

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.example.somnum.ui.screens.AdvicesScreen
import com.example.somnum.ui.screens.HomeScreen
import com.example.somnum.ui.screens.PlannerScreen
import com.example.somnum.ui.screens.SimulationScreen
import com.example.somnum.ui.viewmodel.PlannerViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Router() {
    var selectedItem by remember { mutableIntStateOf(0) }
    val items = listOf("Home", "Advices", "Settings")
    val plannerViewModel = PlannerViewModel()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            NavigationBar {
                NavigationBarItem(
                    icon = { Icon(Icons.Filled.Home, contentDescription = "Home") },
                    label = { Text("Home") },
                    selected = selectedItem == 0,
                    onClick = { selectedItem = 0 }
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Filled.PlayArrow, contentDescription = "Simulation") },
                    label = { Text("Simulation") },
                    selected = selectedItem == 1,
                    onClick = { selectedItem = 1 }
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Filled.DateRange, contentDescription = "Advices") },
                    label = { Text("Planner") },
                    selected = selectedItem == 2,
                    onClick = { selectedItem = 2 }
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Filled.Info, contentDescription = "Advices") },
                    label = { Text("Advices") },
                    selected = selectedItem == 3,
                    onClick = { selectedItem = 3 }
                )
            }
        }
    ) { innerPadding ->
        when (selectedItem) {
            0 -> HomeScreen(modifier = Modifier.padding(innerPadding))
            1 -> SimulationScreen(modifier = Modifier.padding(innerPadding))
            2 -> PlannerScreen(modifier = Modifier.padding(innerPadding),plannerViewModel)
            3 -> AdvicesScreen(modifier = Modifier.padding(innerPadding))

        }
    }
}


