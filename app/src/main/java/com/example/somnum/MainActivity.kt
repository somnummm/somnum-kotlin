package com.example.somnum

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.somnum.presentation.navigation.NavGraph
import dagger.hilt.android.AndroidEntryPoint
import com.example.somnum.ui.screens.NotesScreen
import com.example.somnum.ui.theme.SomnumTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SomnumTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize()
                ) { innerPadding ->
                    NotesScreen(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}