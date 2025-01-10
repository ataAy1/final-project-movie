package com.finalproject.signin


import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.ImageShader
import androidx.compose.ui.graphics.ShaderBrush
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.airbnb.lottie.compose.*

@Composable
fun SignInScreen(
    navController: NavController,
    viewModel: SignInViewModel = hiltViewModel()
) {

    val signInState = viewModel.signInState.collectAsState().value

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var isPasswordVisible by remember { mutableStateOf(false) }
    var isConfirmPasswordVisible by remember { mutableStateOf(false) }



    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.login))
    val progress by animateLottieCompositionAsState(
        composition,
        iterations = LottieConstants.IterateForever
    )

    Scaffold { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(colorResource(id = com.finalproject.util.R.color.prime))
                .padding(innerPadding)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Giriş Yap",
                    color = Color.Black,
                    fontSize = 24.sp,
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.padding(top = 4.dp)
                )

                Spacer(modifier = Modifier.height(16.dp))

                LottieAnimation(
                    composition = composition,
                    progress = progress,
                    modifier = Modifier
                        .size(305.dp, 305.dp)
                )

                Spacer(modifier = Modifier.height(56.dp))


                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    TextField(
                        value = email,
                        onValueChange = { email = it },
                        label = { Text("Email", color = Color.White) },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Outlined.Email,
                                contentDescription = "Email Icon",
                                tint = Color.White
                            )
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = Color.LightGray,
                            unfocusedContainerColor = colorResource(id = com.finalproject.util.R.color.darkAccent)
                        )
                    )

                    TextField(
                        value = password,
                        onValueChange = { password = it },
                        label = { Text("Şifre", color = Color.White) },
                        visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Outlined.Lock,
                                contentDescription = "Şifre Icon",
                                tint = Color.White
                            )
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = Color.LightGray,
                            unfocusedContainerColor = colorResource(id = com.finalproject.util.R.color.darkAccent)
                        )
                    )


                    Spacer(modifier = Modifier.weight(1f))

                    Button(
                        onClick = {
                            viewModel.signIn(email, password)
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 16.dp)
                            .padding(bottom = 40.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = colorResource(id = com.finalproject.util.R.color.black)
                        )
                    ) {
                        Text(
                            text = "Giriş Yap",
                            fontSize = 22.sp,
                            fontWeight = FontWeight.W900,
                            color = colorResource(id = com.finalproject.util.R.color.colorAccent)
                        )
                    }

                    Spacer(modifier = Modifier.height(20.dp))


                }
            }
        }
    }

    when (signInState) {
        is SignInState.Loading -> {
            CircularProgressIndicator(modifier = Modifier.padding(16.dp))
        }
        is SignInState.Success -> {
            Text("Başarili", color = Color.Green)
            LaunchedEffect(Unit) {
                navController.navigate("home_screen")
            }
        }
        is SignInState.Error -> {
            Text("Error: ${signInState.message}", color = Color.Red)
        }
        is SignInState.Idle -> {}
    }
}
