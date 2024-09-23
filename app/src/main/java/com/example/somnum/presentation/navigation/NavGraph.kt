package com.example.somnum.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import com.example.somnum.presentation.viewmodel.LoginViewModel
import androidx.hilt.navigation.compose.hiltViewModel

val navController = rememberNavController()

@Composable
fun AppNavigation(viewModel: LoginViewModel = hiltViewModel()) {
    val navController = rememberNavController()

    NavHost(navController, startDestination = "login") {
        composable("login") {
            LoginScreen(
                viewModel = viewModel,
                onLoginSuccess = {
                    navController.navigate("home") {
                        popUpTo("login") { inclusive = true } // Pour empêcher de revenir à l'écran de login
                    }
                }
            )
        }
        composable("home") {
            HomeScreen() // Écran d'accueil une fois connecté
        }
    }
}