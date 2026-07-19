package com.example.profilmahasiswa.screens

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.profilmahasiswa.ProfileViewModel
import com.example.profilmahasiswa.R
import com.example.profilmahasiswa.ui.theme.ProfilMahasiswaTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileEditScreen(
    viewModel: ProfileViewModel,
    onNavigateBack: () -> Unit
) {
    // Temporary state for unsaved changes
    var tempName by remember { mutableStateOf(viewModel.name) }
    var tempNim by remember { mutableStateOf(viewModel.nim) }
    var tempEmail by remember { mutableStateOf(viewModel.email) }
    var tempPhone by remember { mutableStateOf(viewModel.phone) }
    var tempAddress by remember { mutableStateOf(viewModel.address) }
    var tempImageUri by remember { mutableStateOf(viewModel.profileImageUri) }

    // Error states for validation
    var nameError by remember { mutableStateOf(false) }
    var nimError by remember { mutableStateOf(false) }
    var emailError by remember { mutableStateOf(false) }
    var phoneError by remember { mutableStateOf(false) }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: android.net.Uri? ->
        if (uri != null) {
            tempImageUri = uri
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Edit Profil Mahasiswa",
                        fontWeight = FontWeight.Bold
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Kembali")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = colorResource(id = R.color.colorPrimary),
                    titleContentColor = colorResource(id = R.color.colorPrimaryonContainer),
                    navigationIconContentColor = colorResource(id = R.color.colorPrimaryonContainer)
                )
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            ProfilePhotoEditSection(tempImageUri) {
                launcher.launch("image/*")
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Edit Section
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant
                ),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(
                    modifier = Modifier.padding(20.dp)
                ) {
                    Text(
                        text = "📝 Data Profil",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    EditRow(
                        icon = Icons.Default.Person,
                        label = "Nama Lengkap",
                        value = tempName,
                        onValueChange = { 
                            tempName = it
                            nameError = it.isEmpty()
                        },
                        isError = nameError,
                        errorMessage = "Nama tidak boleh kosong"
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    EditRow(
                        icon = Icons.Default.Badge,
                        label = "NIM",
                        value = tempNim,
                        onValueChange = { 
                            tempNim = it
                            nimError = it.isEmpty()
                        },
                        isError = nimError,
                        errorMessage = "NIM tidak boleh kosong"
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    EditRow(
                        icon = Icons.Default.Email,
                        label = "Email",
                        value = tempEmail,
                        onValueChange = { 
                            tempEmail = it
                            emailError = !android.util.Patterns.EMAIL_ADDRESS.matcher(it).matches()
                        },
                        isError = emailError,
                        errorMessage = "Format email tidak valid"
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    EditRow(
                        icon = Icons.Default.Phone,
                        label = "Telepon",
                        value = tempPhone,
                        onValueChange = { 
                            tempPhone = it
                            phoneError = it.isEmpty()
                        },
                        isError = phoneError,
                        errorMessage = "Telepon tidak boleh kosong"
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    EditRow(
                        icon = Icons.Default.LocationOn,
                        label = "Alamat",
                        value = tempAddress,
                        onValueChange = { tempAddress = it }
                    )
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                OutlinedButton(
                    onClick = onNavigateBack,
                    modifier = Modifier.weight(1f).height(50.dp),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text("Batal")
                }

                Button(
                    onClick = {
                        // Final validation
                        nameError = tempName.isEmpty()
                        nimError = tempNim.isEmpty()
                        emailError = !android.util.Patterns.EMAIL_ADDRESS.matcher(tempEmail).matches()
                        phoneError = tempPhone.isEmpty()

                        if (!nameError && !nimError && !emailError && !phoneError) {
                            viewModel.updateProfile(
                                tempName, tempNim, tempEmail, tempPhone, tempAddress, tempImageUri
                            )
                            onNavigateBack()
                        }
                    },
                    modifier = Modifier.weight(1f).height(50.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colorResource(id = R.color.colorSubmit)
                    )
                ) {
                    Icon(Icons.Default.Save, contentDescription = null, modifier = Modifier.size(20.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Simpan")
                }
            }
        }
    }
}

@Composable
fun ProfilePhotoEditSection(imageUri: android.net.Uri?, onClick: () -> Unit) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.clickable { onClick() }
    ) {
        Box(
            modifier = Modifier
                .size(150.dp)
                .clip(CircleShape)
                .background(
                    Brush.linearGradient(
                        colors = listOf(
                            MaterialTheme.colorScheme.primaryContainer,
                            MaterialTheme.colorScheme.secondaryContainer
                        )
                    )
                )
                .border(4.dp, MaterialTheme.colorScheme.primary, CircleShape),
            contentAlignment = Alignment.Center
        ) {
            if (imageUri != null) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(imageUri)
                        .crossfade(true)
                        .build(),
                    contentDescription = "Foto Profil",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            } else {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = "Foto Profil",
                    modifier = Modifier.size(70.dp),
                    tint = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }
        }
        Box(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .size(40.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.primary)
                .border(2.dp, Color.White, CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.CameraAlt,
                contentDescription = "Ganti Foto",
                tint = Color.White,
                modifier = Modifier.size(20.dp)
            )
        }
    }
}

@Composable
fun EditRow(
    icon: ImageVector,
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    isError: Boolean = false,
    errorMessage: String = ""
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = MaterialTheme.colorScheme.surface,
                shape = RoundedCornerShape(8.dp)
            )
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.primaryContainer),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = label,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(20.dp)
            )
        }

        Spacer(modifier = Modifier.width(12.dp))

        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            label = { Text(label, fontSize = 12.sp) },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            isError = isError,
            supportingText = if (isError) { { Text(errorMessage) } } else null,
            textStyle = LocalTextStyle.current.copy(fontSize = 14.sp)
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ProfileEditScreenPreview() {
    val viewModel = ProfileViewModel()
    ProfilMahasiswaTheme {
        ProfileEditScreen(viewModel = viewModel, onNavigateBack = {})
    }
}
