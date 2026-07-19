package com.example.profilmahasiswa

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.*
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.profilmahasiswa.screens.DataNilaiScreen
import com.example.profilmahasiswa.screens.ProfileEditScreen
import com.example.profilmahasiswa.screens.ProfileScreen
import com.example.profilmahasiswa.ui.theme.ProfilMahasiswaTheme

/**
 * MainActivity - Entry point aplikasi.
 */
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            val viewModel: ProfileViewModel = viewModel()
            var currentScreen by remember { mutableStateOf("profile") }

            ProfilMahasiswaTheme {
                when (currentScreen) {
                    "profile" -> ProfileScreen(
                        viewModel = viewModel,
                        onNavigateToEdit = { currentScreen = "edit" },
                        onNavigateToNilai = { currentScreen = "nilai" }
                    )
                    "edit" -> ProfileEditScreen(
                        viewModel = viewModel,
                        onNavigateBack = { currentScreen = "profile" }
                    )
                    "nilai" -> DataNilaiScreen(
                        viewModel = viewModel,
                        onNavigateBack = { currentScreen = "profile" }
                    )
                }
            }
        }
    }
}
