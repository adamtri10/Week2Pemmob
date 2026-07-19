package com.example.profilmahasiswa

import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

data class MataKuliah(
    val kode: String,
    val nama: String,
    val sks: Int,
    val nilaiAngka: Int,
    val nilaiHuruf: String
)

class ProfileViewModel : ViewModel() {
    var name by mutableStateOf("Adam Tri Ramadhanni")
    var nim by mutableStateOf("23083000153")
    var email by mutableStateOf("Triadam374@gmail.com")
    var phone by mutableStateOf("+62 882-0089-41626")
    var address by mutableStateOf("Malang, Jawa Timur")
    var profileImageUri by mutableStateOf<Uri?>(null)

    // Academic Data
    val ipk = "3.75"
    val totalSks = "120"
    val semesterAktif = "6"

    val daftarNilai = listOf(
        MataKuliah("IF301", "Pemrograman Mobile", 3, 92, "A"),
        MataKuliah("IF302", "Basis Data", 3, 88, "A"),
        MataKuliah("IF303", "Pemrograman Web", 3, 90, "A"),
        MataKuliah("IF304", "Sistem Operasi", 3, 85, "A-"),
        MataKuliah("IF305", "Jaringan Komputer", 3, 87, "A"),
        MataKuliah("IF306", "Rekayasa Perangkat Lunak", 3, 91, "A")
    )

    fun updateProfile(
        newName: String,
        newNim: String,
        newEmail: String,
        newPhone: String,
        newAddress: String,
        newImageUri: Uri?
    ) {
        name = newName
        nim = newNim
        email = newEmail
        phone = newPhone
        address = newAddress
        profileImageUri = newImageUri
    }
}
