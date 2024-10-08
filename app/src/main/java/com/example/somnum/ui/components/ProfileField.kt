package com.example.somnum.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ProfileField(
    label: String,
    value: String?,
    onValueChange: ((String) -> Unit)? = null,
    editable: Boolean = false,
    height: Int = 40 // Hauteur fixe par défaut
) {
    var isEditable by remember { mutableStateOf(false) }

    // Gradient pour le fond
    val backgroundGradient = Brush.horizontalGradient(
        colors = listOf(MaterialTheme.colorScheme.primary, Color(0xFFE0F7FA))
    )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = label,
            modifier = Modifier
                .padding(bottom = 4.dp),
            fontSize = 14.sp,
            color = MaterialTheme.colorScheme.primary
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(height.dp) // Fixez la hauteur ici
                .background(
                    brush = backgroundGradient,
                    shape = RoundedCornerShape(16.dp)
                )
                .padding(8.dp),
            contentAlignment = Alignment.Center // Centre tout le contenu de la Box
        ) {
            if (isEditable && value != null && onValueChange != null) {
                TextField(
                    value = value,
                    onValueChange = onValueChange,
                    modifier = Modifier
                        .fillMaxWidth(0.9f)
                        .fillMaxHeight(), // Utilisez toute la hauteur
                    singleLine = true,
                    visualTransformation = VisualTransformation.None,
                    textStyle = LocalTextStyle.current.copy(color = MaterialTheme.colorScheme.onSurface),
                    shape = RoundedCornerShape(16.dp),
                    colors = TextFieldDefaults.colors(
                        cursorColor = MaterialTheme.colorScheme.primary,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        disabledIndicatorColor = Color.Transparent
                    )
                )
            } else {
                Text(
                    text = value ?: "",
                    fontSize = 16.sp,
                    color = Color.Black,
                    modifier = Modifier
                        .fillMaxWidth(0.9f)
                        .fillMaxHeight(), // Utilisez toute la hauteur
                    maxLines = 1,
                    textAlign = TextAlign.Start
                )
            }

            if (editable) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "Edit Icon",
                    modifier = Modifier
                        .size(24.dp)
                        .clickable {
                            isEditable = !isEditable
                        },
                    tint = MaterialTheme.colorScheme.onSurface
                )
            }
        }
    }
}