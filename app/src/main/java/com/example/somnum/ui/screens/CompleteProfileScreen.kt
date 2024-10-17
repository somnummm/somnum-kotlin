package com.example.somnum.ui.screens

import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.somnum.activities.CompleteProfileActivity
import com.example.somnum.activities.MainActivity
import com.example.somnum.ui.components.SomnumInput
import com.example.somnum.viewmodel.ProfileViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun CompleteProfileScreen(viewModel: ProfileViewModel) {
    LaunchedEffect(Unit) {
        viewModel.loadUserProfile()
    }

    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    val userProfile = viewModel.userProfileInfos

    var fullName by rememberSaveable { mutableStateOf(userProfile?.fullName ?: "") }
    var bio by rememberSaveable { mutableStateOf(userProfile?.bio ?: "") }
    var showToast by remember { mutableStateOf(false) }

    LaunchedEffect(userProfile) {
        userProfile?.let {
            fullName = userProfile.fullName ?: ""
            bio = userProfile.bio ?: ""
        }
    }

    LaunchedEffect(fullName, bio) {
        delay(1000)
        coroutineScope.launch {
            if (userProfile != null) {
                val updatedProfile = userProfile.copy(
                    fullName = fullName,
                    bio = bio
                )
                viewModel.saveProfileChanges(profile = updatedProfile)
                if (!showToast) {
                    showToast = true
                    Toast.makeText(context, "Profile updated!", Toast.LENGTH_SHORT).show()
                    delay(2000)
                    showToast = false
                }
            } else {
                Log.d("CompleteProfileScreen", "User profile is null")
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Let's complete your profile",
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )

        Spacer(modifier = Modifier.height(16.dp))

        SomnumInput(
            value = fullName,
            onValueChange = { fullName = it },
            label = "Full Name"
        )

        Spacer(modifier = Modifier.height(16.dp))

        SomnumInput(
            value = bio,
            onValueChange = { bio = it },
            label = "Bio"
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                context.startActivity(Intent(context, MainActivity::class.java))
                (context as CompleteProfileActivity).finish()
            }
        ) {
            Text("Let's go!")
        }
    }
}

@Composable
@Preview
fun PreviewCompleteProfileScreen() {
    CompleteProfileScreen(ProfileViewModel())
}
