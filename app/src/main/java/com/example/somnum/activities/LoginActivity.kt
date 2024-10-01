package com.example.somnum.activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.somnum.ui.screens.LoginScreen
import com.example.somnum.ui.theme.SomnumTheme
import com.example.somnum.viewmodel.LoginViewModel

class LoginActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SomnumTheme {
                LoginScreen(LoginViewModel())
            }
        }
    }
}