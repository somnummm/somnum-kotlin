buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath("com.android.tools.build:gradle:8.1.4") // Assure-toi que cette version est compatible avec ton projet
        classpath(libs.kotlin.gradle.plugin)
        classpath(libs.hilt.android.gradle.plugin) // Plugin Hilt ajouté ici
    }
}

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    kotlin("plugin.serialization") version "1.9.0"
    kotlin("kapt") // Pour Hilt (génération de code)
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.navigation.compose)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)


    implementation(platform(libs.bom))
    implementation(libs.postgrest.kt)
    implementation(libs.ktor.client.android)

    // Coil pour le chargement d'images dans Compose (optionnel)
    implementation("io.coil-kt:coil-compose:2.4.0")

    // Testing
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation("androidx.compose.ui:ui-test-junit4:1.5.3")
    debugImplementation("androidx.compose.ui:ui-tooling:1.5.3")
    debugImplementation("androidx.compose.ui:ui-test-manifest:1.5.3")

    // Timber pour les logs (optionnel)
    implementation("com.jakewharton.timber:timber:5.0.1")

}
