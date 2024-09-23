package com.example.somnum.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.somnum.ui.components.Advices

@Composable
fun HomeScreen(modifier: Modifier = Modifier) {
    // Contenu de l'écran d'accueil
    Column {
        Advices()
    }

}