package com.example.somnum.ui.screens

import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.example.somnum.activities.LoginActivity
import com.example.somnum.activities.MainActivity
import com.example.somnum.activities.SignUpActivity
import com.example.somnum.viewmodel.SignUpViewModel
import kotlinx.coroutines.launch

@Composable
fun SignUpScreen(viewModel: SignUpViewModel) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
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
        TextField(
            value = confirmPassword,
            onValueChange = { confirmPassword = it },
            label = { Text("Confirm Password") },
            visualTransformation = PasswordVisualTransformation()
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {
                if (password != confirmPassword) {
                    Toast.makeText(
                        context,
                        "Les mots de passe ne correspondent pas.",
                        Toast.LENGTH_SHORT
                    ).show()
                    return@Button
                }

                coroutineScope.launch {
                    isLoading = true
                    viewModel.signUp(email, password, onResult = { result ->
                        isLoading = false
                        if (result != null) {
                            Toast.makeText(context, result, Toast.LENGTH_SHORT).show()
                        } else {
                            context.startActivity(Intent(context, MainActivity::class.java))
                            (context as SignUpActivity).finish()
                        }
                    })
                }
            },
            enabled = !isLoading
        ) {
            Text(if (isLoading) "Loading..." else "S'inscrire")
        }
        Spacer(modifier = Modifier.height(16.dp))
        TextButton(
            onClick = {
                context.startActivity(Intent(context, LoginActivity::class.java))
            }
        ) {
            Text("Déjà inscrit ? Se connecter")
        }
    }
}
