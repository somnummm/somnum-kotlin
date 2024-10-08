package com.example.somnum.ui.components.pickers

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment


@SuppressLint("DefaultLocale")
@Composable
fun HourPicker(
    value: Int,
    onValueChange: (Int) -> Unit,
    range: IntRange
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        IconButton(
            onClick = { if (value < range.last) onValueChange(value + 1) }
        ) {
            Text("▲")
        }

        Text(
            text = String.format("%02d", value),
            style = MaterialTheme.typography.headlineSmall
        )

        IconButton(
            onClick = { if (value > range.first) onValueChange(value - 1) }
        ) {
            Text("▼")
        }
    }
}
