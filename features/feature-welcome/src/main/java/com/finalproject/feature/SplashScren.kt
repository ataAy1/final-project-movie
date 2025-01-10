package com.finalproject.feature

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.airbnb.lottie.compose.*
import kotlinx.coroutines.delay

@Composable
fun SplashMovieScreen(navController: NavController, viewModel: SplashViewModel = hiltViewModel()) {
    val isLoginUser by viewModel.isLoginUser.collectAsState()

    LaunchedEffect(isLoginUser) {
        delay(3260)
        if (isLoginUser) {
            navController.navigate("home_screen") {
                popUpTo("splash_screen") { inclusive = true }
            }
        } else {
            navController.navigate("welcome_screen") {
                popUpTo("splash_screen") { inclusive = true }
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = com.finalproject.util.R.color.black)),
        contentAlignment = Alignment.Center
    ) {
        LottieAnimation(
            composition = rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.splash)).value,
            progress = animateLottieCompositionAsState(
                composition = rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.splash)).value,
                iterations = LottieConstants.IterateForever
            ).value,
            modifier = Modifier.size(200.dp)
        )
    }
}
