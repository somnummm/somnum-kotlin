package com.example.somnum.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.somnum.viewmodel.ProfileViewModel

@Composable
fun ProfileScreen(viewModel: ProfileViewModel) {
    // Observer le profil utilisateur
    val userProfile = viewModel.userProfile.collectAsState().value

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .background(Color.White),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.Start
    ) {
        // Afficher le nom complet
        Text(
            text = "Profile",
            fontSize = 24.sp,
            color = Color.Black
        )

        userProfile?.let { profile ->
            // Champ pour le nom complet
            TextField(
                value = TextFieldValue(profile.fullName ?: ""),
                onValueChange = { newName ->
                    viewModel.updateUserProfile(profile.copy(fullName = newName.text))
                },
                label = { Text("Full Name") }
            )

            // Champ pour le bio
            TextField(
                value = TextFieldValue(profile.bio ?: ""),
                onValueChange = { newBio ->
                    viewModel.updateUserProfile(profile.copy(bio = newBio.text))
                },
                label = { Text("Bio") }
            )

            // Champ pour l'URL de l'avatar
            TextField(
                value = TextFieldValue(profile.avatarUrl ?: ""),
                onValueChange = { newAvatarUrl ->
                    viewModel.updateUserProfile(profile.copy(avatarUrl = newAvatarUrl.text))
                },
                label = { Text("Avatar URL") }
            )

            // Autres champs pour le profil
            // (Ajoute ici d'autres TextFields ou composants nécessaires pour d'autres données du profil)

            // Bouton pour sauvegarder les modifications
            Button(onClick = { viewModel.saveProfileChanges() }) {
                Text(text = "Save Changes")
            }
        } ?: run {
            // Afficher un message de chargement si le profil n'est pas encore chargé
            CircularProgressIndicator()
        }
    }
}