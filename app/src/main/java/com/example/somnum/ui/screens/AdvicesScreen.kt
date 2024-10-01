package com.example.somnum.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.somnum.data.network.supabase
import com.example.somnum.ui.components.AdviceCard
import com.example.somnum.ui.theme.SomnumTheme
import io.github.jan.supabase.postgrest.from
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.Serializable



@Serializable
data class Advice(
    val id: Int,
    val title: String,
    val description: String,
    val url: String
)

@Composable
fun AdvicesScreen(modifier: Modifier = Modifier) {
    val advices = remember { mutableStateListOf<Advice>() }
    LaunchedEffect(Unit) {
        withContext(Dispatchers.IO) {
            val results = supabase.from("advices").select().decodeList<Advice>()
            advices.addAll(results)
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Conseils pour un meilleur sommeil",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp),
            color = MaterialTheme.colorScheme.primary
        )

        LazyColumn(modifier = Modifier.weight(1f)) {
            items(advices) { advice ->
                AdviceCard(advice)
            }
        }
    }
}



@Composable
@Preview(showBackground = true)
fun AdvicesScreenPreview() {
    SomnumTheme {
        AdvicesScreen()
    }
}
