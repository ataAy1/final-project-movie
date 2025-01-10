package com.finalproject.settings

import android.app.Activity
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import com.finalproject.domain.model.user.UserModel
import com.finalproject.util.LoadingAnimation
import com.finalproject.util.UtilImage
import com.skydoves.landscapist.animation.circular.CircularRevealPlugin
import com.skydoves.landscapist.coil.CoilImage
import com.skydoves.landscapist.components.rememberImageComponent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    navController: NavController,
    settingsViewModel: SettingsViewModel = hiltViewModel()
) {
    var profilePhotoUri by remember { mutableStateOf<Uri?>(null) }
    var name by remember { mutableStateOf(TextFieldValue("")) }
    var phoneNumber by remember { mutableStateOf(TextFieldValue("")) }
    var userEmail by remember { mutableStateOf("") }
    val context = LocalContext.current


    val state by settingsViewModel.state.collectAsState()

    LaunchedEffect(state.userSettings) {
        state.userSettings?.let { userSettings ->
            if (name.text.isEmpty()) {
                name = TextFieldValue(userSettings.username)
            }
            if (phoneNumber.text.isEmpty()) {
                phoneNumber = TextFieldValue(userSettings.userPhone)
            }
            if (userEmail.isEmpty()) {
                userEmail = userSettings.userMail
            }
            profilePhotoUri = Uri.parse(userSettings.profilePhotoUrl)
        }
    }


    LaunchedEffect(state.isSaveSuccessful) {
        if (state.isSaveSuccessful) {
            Toast.makeText(context, "Başarili", Toast.LENGTH_SHORT).show()
        } else if (state.error != null) {
            Toast.makeText(context, "Hata: ${state.error}", Toast.LENGTH_SHORT).show()
        }
    }

    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            result.data?.data?.let { uri ->
                profilePhotoUri = uri
                UtilImage.handleImagePickerResult(
                    result.data,
                    mutableListOf(uri)
                )
            }
        }
    }

    state.error?.let {
        Text(text = it, color = Color.Red)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Hesap Ayarları",
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(end = 54.dp, top = 10.dp),
                        fontSize = 22.sp,
                        color = MaterialTheme.colorScheme.onPrimary,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Image(
                            painterResource(id = R.drawable.ic_arrow),
                            modifier = Modifier
                                .size(40.dp)
                                .padding(top = 4.dp),
                            contentDescription = "Back",
                        )
                    }
                },
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    colorResource(id = com.finalproject.util.R.color.prime))
            )
        },
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(colorResource(id = com.finalproject.util.R.color.prime2))
                    .padding(paddingValues)
            ) {

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(220.dp)
                        .background(
                            brush = Brush.linearGradient(
                                colors = listOf(
                                    colorResource(id = com.finalproject.util.R.color.prime1),
                                    colorResource(id = com.finalproject.util.R.color.darkAccent)
                                )
                            )
                        ),
                    contentAlignment = Alignment.TopCenter
                ) {
                    Box(
                        modifier = Modifier
                            .align(Alignment.TopCenter)
                            .padding(top = 42.dp, start = 20.dp)
                    ) {
                        if (profilePhotoUri != null) {
                            CoilImage(
                                imageModel = { profilePhotoUri },
                                modifier = Modifier
                                    .size(140.dp)
                                    .clip(CircleShape)
                                    .border(2.dp, Color.White, CircleShape),
                                component = rememberImageComponent {
                                    +CircularRevealPlugin(duration = 1500)
                                },
                                failure = {
                                    Text(text = "Hata")
                                }
                            )

                        } else {
                            Image(
                                painter = painterResource(id = R.drawable.ic_change_photo),
                                contentDescription = "Foto",
                                modifier = Modifier
                                    .size(140.dp)
                                    .clip(CircleShape)
                                    .border(2.dp, Color.White, CircleShape)
                            )
                        }

                        IconButton(
                            onClick = { UtilImage.openImagePicker(imagePickerLauncher) },
                            modifier = Modifier.padding(top = 4.dp, start = 104.dp)
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.ic_change_photo),
                                contentDescription = "Upload",
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    horizontalAlignment = Alignment.Start
                ) {
                    if (state.isLoading || state.isSaving) {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            LoadingAnimation(
                                circleSize = 25.dp,
                                circleColor = colorResource(id = com.finalproject.util.R.color.colorAccent),
                                spaceBetween = 10.dp,
                                travelDistance = 20.dp
                            )                        }
                    }


                    Text("Adınız:", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                    TextField(
                        value = name,
                        onValueChange = { name = it },
                        label = {
                            Text(
                                "Ad Soyad",
                                style = TextStyle(
                                    fontSize = 14.sp,
                                    color = colorResource(id = com.finalproject.util.R.color.colorAccent)
                                ),
                                modifier = Modifier.padding(bottom = 8.dp)
                            )
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 16.dp),
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = Color.Transparent,
                            unfocusedContainerColor = Color.Transparent,
                            focusedIndicatorColor = colorResource(id = com.finalproject.util.R.color.colorAccent),
                            unfocusedIndicatorColor = colorResource(id = com.finalproject.util.R.color.colorAccent)
                        ),
                        textStyle = TextStyle(fontSize = 18.sp, color = Color.White)
                    )

                    Text("Mail Adresi:", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                    TextField(
                        value = userEmail,
                        onValueChange = {},
                        label = {
                            Text(
                                "Mail Adresi",
                                style = TextStyle(
                                    fontSize = 14.sp,
                                    color = colorResource(id = com.finalproject.util.R.color.colorAccent)
                                ),
                                modifier = Modifier.padding(bottom = 8.dp)
                            )
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 12.dp),
                        enabled = true,
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = Color.Transparent,
                            unfocusedContainerColor = Color.Transparent,
                            focusedIndicatorColor = colorResource(id = com.finalproject.util.R.color.colorAccent),
                            unfocusedIndicatorColor = colorResource(id = com.finalproject.util.R.color.colorAccent)
                        ),
                        textStyle = TextStyle(fontSize = 18.sp, color = Color.White)
                    )

                    Text("Telefon Numarası :", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                    TextField(
                        value = phoneNumber,
                        onValueChange = { phoneNumber = it },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 12.dp),
                        label = {
                            Text(
                                "Telefon Numarası",
                                style = TextStyle(
                                    fontSize = 14.sp,
                                    color = colorResource(id = com.finalproject.util.R.color.colorAccent)
                                ),
                                modifier = Modifier.padding(bottom = 8.dp)
                            )
                        },
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = Color.Transparent,
                            unfocusedContainerColor = Color.Transparent,
                            focusedIndicatorColor = colorResource(id = com.finalproject.util.R.color.colorAccent),
                            unfocusedIndicatorColor = colorResource(id = com.finalproject.util.R.color.colorAccent)
                        ),
                        textStyle = TextStyle(fontSize = 18.sp, color = Color.White)
                    )


                    Button(
                        onClick = {
                            if (phoneNumber.text.isNotEmpty()) {
                                settingsViewModel.saveUserSettings(
                                    username = name.text,
                                    userPhone = phoneNumber.text,
                                    userMail = userEmail,
                                    profilePhotoUri = profilePhotoUri
                                )
                            } else {

                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 16.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = com.finalproject.util.R.color.prime))
                    ) {
                        Text("Kaydet", color = colorResource(id = com.finalproject.util.R.color.colorAccent), fontSize = 20.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    )
}
