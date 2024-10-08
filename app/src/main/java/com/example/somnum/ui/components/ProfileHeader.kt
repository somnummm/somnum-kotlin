package com.example.somnum.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.somnum.R

@Composable
fun ProfileHeader(
    fullName: String,
    bio: String,
    profileImageUrl: String? = null // Utilisez une chaîne pour l'URL de l'image
) {
    // Gradient pour le fond
    val backgroundGradient = Brush.horizontalGradient(
        colors = listOf(MaterialTheme.colorScheme.primary, Color(0xFFE0F7FA))
    )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally // Centre tout le contenu horizontalement
    ) {
        // Image de profil
        Box(
            modifier = Modifier
                .size(100.dp) // Taille de l'image de profil
                .background(Color.Gray, shape = CircleShape), // Couleur de fond par défaut
            contentAlignment = Alignment.Center
        ) {
            // Utilisation de Coil pour charger l'image à partir d'une URL
            if (profileImageUrl != null) {
                AsyncImage(
                    model = profileImageUrl,
                    contentDescription = "Profile Image",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Fit
                )
            } else {
                // Image par défaut si aucune image n'est fournie
                // Remplacez `R.drawable.default_profile` par votre ressource d'image par défaut
                Image(
                    painter = painterResource(id = R.drawable.default_profile_picture),
                    contentDescription = "Default Profile Image",
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(CircleShape),
                    contentScale = ContentScale.Fit
                )
            }
        }

        // Nom complet
        Text(
            text = fullName,
            fontSize = 20.sp,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier
                .padding(top = 8.dp) // Utilisation du gradient pour le texte
                .padding(8.dp) // Padding autour du texte
        )

        // Biographie
        Text(
            text = bio,
            fontSize = 14.sp,
            color = MaterialTheme.colorScheme.secondary,
            modifier = Modifier
                .padding(top = 4.dp)
                .padding(8.dp) // Padding autour du texte
        )
    }
}