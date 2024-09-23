package com.example.somnum.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.example.somnum.presentation.viewmodel.LoginViewModel

@Composable
fun LoginScreen(
    viewModel: LoginViewModel,
    onLoginSuccess: () -> Unit // Navigation vers un autre écran après succès
) {
    val email by viewModel::email
    val password by viewModel::password
    val loginState by viewModel::loginState

    // Affichage de l'écran de connexion
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Login", style = MaterialTheme.typography.titleLarge)
        Spacer(modifier = Modifier.height(16.dp))

        // Champ Email
        TextField(
            value = email,
            onValueChange = viewModel::onEmailChange,
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Email
            )
        )
        Spacer(modifier = Modifier.height(8.dp))

        // Champ Mot de passe
        TextField(
            value = password,
            onValueChange = viewModel::onPasswordChange,
            label = { Text("Password") },
            modifier = Modifier.fillMaxWidth(),
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Password
            )
        )
        Spacer(modifier = Modifier.height(16.dp))

        // Affichage du bouton de login ou de l'état en cours
        when (loginState) {
            is LoginViewModel.LoginState.Idle -> {
                Button(
                    onClick = { viewModel.login() },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Login")
                }
            }
            is LoginViewModel.LoginState.Loading -> {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
            }
            is LoginViewModel.LoginState.Error -> {
                Text(
                    text = (loginState as LoginViewModel.LoginState.Error).message,
                    color = Color.Red,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
                Button(
                    onClick = { viewModel.login() },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Retry")
                }
            }
            is LoginViewModel.LoginState.Success -> {
                onLoginSuccess()
            }
        }
    }
}