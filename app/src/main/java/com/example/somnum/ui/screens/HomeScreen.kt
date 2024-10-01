package com.example.somnum.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.somnum.ui.components.charts.DonutChartSample
import com.example.somnum.ui.components.typography.Title


@Composable
fun HomeScreen(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Title("Home")
        Text(text = "Votre dernière nuit")
        DonutChartSample(modifier)
    }

}