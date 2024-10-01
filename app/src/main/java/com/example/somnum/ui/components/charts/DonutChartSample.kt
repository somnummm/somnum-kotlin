package com.example.somnum.ui.components.charts

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.aay.compose.baseComponents.model.LegendPosition
import com.aay.compose.donutChart.DonutChart
import com.aay.compose.donutChart.model.PieChartData

@Composable
fun DonutChartSample(modifier: Modifier = Modifier) {
    val testPieChartData: List<PieChartData> = listOf(
        PieChartData(
            partName = "Deep",
            data = 55.0,
            color = MaterialTheme.colorScheme.primary,
        ),
        PieChartData(
            partName = "Core",
            data = 25.0,
            color = MaterialTheme.colorScheme.secondary,
        ),
        PieChartData(
            partName = "REM",
            data = 15.0,
            color = MaterialTheme.colorScheme.tertiary,
        ),
        PieChartData(
            partName = "Awake",
            data = 5.0,
            color = MaterialTheme.colorScheme.outline,
        ),
    )

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        DonutChart(
            pieChartData = testPieChartData,
            centerTitle = "Sommeil",
            centerTitleStyle = TextStyle(color = MaterialTheme.colorScheme.tertiary),
            outerCircularColor = Color.LightGray,
            innerCircularColor = Color.Gray,
            ratioLineColor = Color.LightGray,
            descriptionStyle = TextStyle(color = MaterialTheme.colorScheme.tertiary),
            legendPosition = LegendPosition.BOTTOM,
            textRatioStyle = TextStyle(color = MaterialTheme.colorScheme.primary),
        )
    }


}