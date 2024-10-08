package com.example.somnum

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import com.example.somnum.activities.LoginActivity
import com.example.somnum.activities.MainActivity
import com.example.somnum.ui.viewmodel.LoginViewModel

class SplashActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val loginViewModel = LoginViewModel()
        if (loginViewModel.isLogged()) {
            startActivity(Intent(this, MainActivity::class.java))
        } else {
            startActivity(Intent(this, LoginActivity::class.java))
        }
        finish()
    }

}