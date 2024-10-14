package com.example.somnum.ui.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.somnum.activities.MainActivity
import com.example.somnum.activities.ProfileActivity
import com.example.somnum.ui.components.ProfileField
import com.example.somnum.ui.components.ProfileHeader
import com.example.somnum.viewmodel.LoginViewModel
import com.example.somnum.viewmodel.ProfileViewModel
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ProfileScreen(profileViewModel: ProfileViewModel, loginViewModel: LoginViewModel) {
    val userProfile = profileViewModel.userProfile.collectAsState().value
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        profileViewModel.loadUserProfile()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background) // Couleur de fond plus douce
            .padding(32.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Titre du profil
        Text(
            text = "Profile",
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary // Couleur principale
        )

        // Si le profil utilisateur est chargé, afficher ses informations
        userProfile?.let { profile ->
            // Afficher la photo de profil, le nom complet et la bio
            ProfileHeader(
                fullName = profile.fullName?.let { it } ?: "Full Name",
                bio = profile.bio?.let { it } ?: "Bio",
                profileImageUrl = null
            )

            // Formatter la date de création
            val createdAt = profile.createdAt?.let {
                val formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy")
                val parsedDate = OffsetDateTime.parse(it)
                parsedDate.format(formatter)
            } ?: "Date inconnue"

            // Afficher les champs du profil
            ProfileField("Email", profile.email) // Couleur mauve clair
            ProfileField("Bed Time", profile.bedTime) // Couleur vert clair
            ProfileField("Wake Up Time", profile.wakeUpTime) // Couleur rouge clair
            Text(
                text = "Member since $createdAt",
                modifier = Modifier
                    .padding(bottom = 4.dp),
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.primary
            )
            Button(
                onClick = { profileViewModel.saveProfileChanges() },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                ),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(text = "Save Changes")
            }

            Button(
                onClick = { loginViewModel.logout(); (context as ProfileActivity).finish(); },
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
        } ?: run {
            CircularProgressIndicator(color = Color(0xFF1E88E5))
        }
    }
}