package com.example.somnum.ui.screens

import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.somnum.activities.LoginActivity
import com.example.somnum.activities.MainActivity
import com.example.somnum.activities.ProfileActivity
import com.example.somnum.ui.components.ProfileField
import com.example.somnum.ui.components.ProfileHeader
import com.example.somnum.ui.viewmodel.LoginViewModel
import com.example.somnum.viewmodel.ProfileViewModel
import io.github.jan.supabase.gotrue.auth
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ProfileScreen(profileViewModel: ProfileViewModel, loginViewModel: LoginViewModel) {
    val context = LocalContext.current
    val user = supabase.auth.currentUserOrNull()

    val userProfile = profileViewModel.userProfileInfos
    val isLoading = profileViewModel.isLoading

    LaunchedEffect(Unit) {
        profileViewModel.loadUserProfile()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(32.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Profile",
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )

        if (isLoading.value) {
            Text(text = "Loading...", fontSize = 16.sp)
        } else {
            ProfileHeader(
                fullName = userProfile?.fullName ?: "Full Name",
                bio = userProfile?.bio ?: "Bio",
                profileImageUrl = null
            )
        }

        val createdAt = user?.createdAt.let {
            val formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy")
            val parsedDate = OffsetDateTime.parse(it.toString())
            parsedDate.format(formatter)
        } ?: "Date inconnue"

        ProfileField(label = "Email", value = user?.email ?: "Email non disponible")

        Text(
            text = "Member since $createdAt",
            modifier = Modifier.padding(bottom = 4.dp),
            fontSize = 14.sp,
            color = MaterialTheme.colorScheme.primary
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                context.startActivity(Intent(context, MainActivity::class.java))
                (context as ProfileActivity).finish()
            },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary
            ),
            shape = RoundedCornerShape(8.dp)
        ) {
            Text(text = "Return to Home")
        }

        Button(
            onClick = {
                loginViewModel.logout()
                context.startActivity(Intent(context, LoginActivity::class.java))
                (context as ProfileActivity).finish()
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Gray,
                contentColor = Color.White
            ),
            shape = RoundedCornerShape(8.dp)
        ) {
            Text(text = "Logout")

        }
    }
}