package com.example.somnum

import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest

val supabase = createSupabaseClient(
    supabaseUrl = "https://wyfrzcwsrqkrwqqhgbhd.supabase.co",
    supabaseKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6Ind5ZnJ6Y3dzcnFrcndxcWhnYmhkIiwicm9sZSI6ImFub24iLCJpYXQiOjE3MTMyNzQzNjAsImV4cCI6MjAyODg1MDM2MH0.KnAOLtglCWZNxasBc1pmJJI0edx027o2vxSD8pymkTc"
) {
    install(Postgrest)
}