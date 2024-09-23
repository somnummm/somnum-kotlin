package com.example.somnum

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ListItem
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.somnum.ui.theme.SomnumTheme
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.postgrest.from
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.serialization.Serializable


val supabase = createSupabaseClient(
    supabaseUrl = "https://wyfrzcwsrqkrwqqhgbhd.supabase.co",
    supabaseKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6Ind5ZnJ6Y3dzcnFrcndxcWhnYmhkIiwicm9sZSI6ImFub24iLCJpYXQiOjE3MTMyNzQzNjAsImV4cCI6MjAyODg1MDM2MH0.KnAOLtglCWZNxasBc1pmJJI0edx027o2vxSD8pymkTc"
) {
    install(Postgrest)
}


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SomnumTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    NotesList()
                }
            }
        }
    }
}

@Serializable
data class Note(
    val id: Int,
    val body: String
)

@Composable
fun NotesList() {
    val notes = remember { mutableStateListOf<Note>() }
    LaunchedEffect(Unit) {
        withContext(Dispatchers.IO){
            val results = supabase.from("notes").select().decodeList<Note>()
            notes.addAll(results)
        }
    }
    Column {
        LazyColumn {
            items(notes){
                    note -> ListItem(headlineContent = { Text(text = note.body)})
            }
        }
        var newNote by remember { mutableStateOf("") }
        var composableScope = rememberCoroutineScope()
        Row{
            OutlinedTextField(value = newNote, onValueChange = {newNote= it})
            Button(onClick = {
                composableScope.launch(Dispatchers.IO) {
                    val note = supabase.from("notes").insert(mapOf("body" to newNote)){
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
