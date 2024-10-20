package com.example.somnum.ui.screens

import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.somnum.R
import com.example.somnum.activities.LoginActivity
import com.example.somnum.activities.MainActivity
import com.example.somnum.activities.SignUpActivity
import com.example.somnum.ui.components.SomnumInput
import com.example.somnum.ui.viewmodel.LoginViewModel
import kotlinx.coroutines.launch


@Composable
fun LoginScreen(viewModel: LoginViewModel) {
    val sharedPreferences =
        LocalContext.current.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
    var email by remember { mutableStateOf(sharedPreferences.getString("email", "") ?: "") }
    var password by remember { mutableStateOf(sharedPreferences.getString("password", "") ?: "") }
    val isLoading by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        if (email.isNotEmpty() && password.isNotEmpty()) {
            coroutineScope.launch {
                viewModel.login(email, password, onResult = { result ->
                    if (result == null) {
                        context.startActivity(Intent(context, MainActivity::class.java))
                        (context as LoginActivity).finish()
                    }
                })
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier
                .size(130.dp)
                .border(
                    width = 2.dp,
                    color = MaterialTheme.colorScheme.primary,
                    shape = RoundedCornerShape(64.dp)
                )
                .align(Alignment.CenterHorizontally)
        ) {
            Image(
                painter = painterResource(id = R.drawable.somnum_logo),
                contentDescription = "Somnum Logo",
                modifier = Modifier
                    .size(130.dp),
                contentScale = ContentScale.Fit
            )
        }
        SomnumInput(
            value = email,
            onValueChange = { email = it },
            label = "Email"
        )
        Spacer(modifier = Modifier.height(16.dp))
        SomnumInput(
            value = password,
            onValueChange = { password = it },
            label = "Password",
            isPassword = true
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
            Text(if (isLoading) "Loading..." else "Login")
        }
        Spacer(modifier = Modifier.height(16.dp))
        TextButton(
            onClick = {
                context.startActivity(Intent(context, SignUpActivity::class.java))
                (context as LoginActivity).finish()
            }
        ) {
            Text("Sign-up")
        }
    }
}