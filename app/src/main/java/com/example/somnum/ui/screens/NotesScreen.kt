package com.example.somnum.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ListItem
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.postgrest.from
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.serialization.Serializable

@Serializable
data class Note(
    val id: Int,
    val body: String
)

val supabase = createSupabaseClient(
    supabaseUrl = "https://wyfrzcwsrqkrwqqhgbhd.supabase.co",
    supabaseKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6Ind5ZnJ6Y3dzcnFrcndxcWhnYmhkIiwicm9sZSI6ImFub24iLCJpYXQiOjE3MTMyNzQzNjAsImV4cCI6MjAyODg1MDM2MH0.KnAOLtglCWZNxasBc1pmJJI0edx027o2vxSD8pymkTc"
) {
    install(Postgrest)
}


@Composable
fun NotesScreen(modifier: Modifier = Modifier) {
    val notes = remember { mutableStateListOf<Note>() }
    var newNote by remember { mutableStateOf("") }
    val composableScope = rememberCoroutineScope()

    // Récupération des notes existantes
    LaunchedEffect(Unit) {
        withContext(Dispatchers.IO) {
            val results = supabase.from("notes").select().decodeList<Note>()
            notes.addAll(results)
        }
    }

    Column(modifier = modifier.fillMaxSize().padding(16.dp)) {
        // Liste des notes
        LazyColumn(modifier = Modifier.weight(1f)) {
            items(notes) { note ->
                ListItem(headlineContent = { Text(text = note.body) })
            }
        }

        // Formulaire d'ajout de nouvelle note
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            OutlinedTextField(
                value = newNote,
                onValueChange = { newNote = it },
                label = { Text("Nouvelle note") },
                modifier = Modifier.weight(1f).padding(end = 8.dp)
            )
            Button(onClick = {
                composableScope.launch(Dispatchers.IO) {
                    val note = supabase.from("notes").insert(mapOf("body" to newNote)) {
                        select()
                        single()
                    }.decodeAs<Note>()
                    notes.add(note)
                    newNote = ""
                }
            }) {
                Text(text = "Save")
            }
        }
    }
}
