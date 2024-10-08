package com.example.somnum.ui.screens

import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.example.somnum.activities.LoginActivity
import com.example.somnum.activities.MainActivity
import com.example.somnum.activities.SignUpActivity
import com.example.somnum.ui.viewmodel.LoginViewModel
import kotlinx.coroutines.launch

@Composable
fun LoginScreen(viewModel: LoginViewModel) {
    var email by remember { mutableStateOf("Clem83Gambier@gmail.com") }
    var password by remember { mutableStateOf("test") }
    var isLoading by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        TextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") }
        )
        Spacer(modifier = Modifier.height(16.dp))
        TextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            visualTransformation = PasswordVisualTransformation()
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {
                coroutineScope.launch {
                    viewModel.login(email, password, onResult = { result ->
                        if (result != null) {
                            Toast.makeText(context, result, Toast.LENGTH_SHORT).show()
                        } else {
                            context.startActivity(Intent(context, MainActivity::class.java))
                            (context as LoginActivity).finish()
                        }
                    }
                    )
                }
            },
            enabled = !isLoading
        ) {
            Text(if (isLoading) "Loading..." else "Se connecter")
        }
        Spacer(modifier = Modifier.height(16.dp))
        TextButton(
            onClick = {
                context.startActivity(Intent(context, SignUpActivity::class.java))
                (context as LoginActivity).finish()
            }
        ) {
            Text("S'inscrire")
        }
    }
}