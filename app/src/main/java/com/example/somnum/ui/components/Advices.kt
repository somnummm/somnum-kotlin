package com.example.somnum.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.somnum.supabase
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
fun Advices(modifier: Modifier = Modifier) {
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
            modifier = Modifier.padding(bottom = 16.dp)
        )

        LazyColumn(modifier = Modifier.weight(1f)) {
            items(advices) { advice ->
                AdviceCard(advice)
            }
        }
    }
}

@Composable
fun AdviceCard(advice: Advice, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = advice.title,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = advice.description,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            Button(
                onClick = { /* ouvrir URL du conseil */ },
            ) {
                Text(text = "Voir plus")
            }
        }
    }
}



@Composable
@Preview(showBackground = true)
fun AdvicesPreview() {
    SomnumTheme {
        Advices()
    }
}
