package com.finalproject.favorites

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.finalproject.domain.model.movie.Movie
import com.finalproject.favorites.component.FavoriteItem
import com.finalproject.util.LoadingAnimation

@Composable
fun FavoritesScreen(navController: NavController, favoritesViewModel: FavoritesViewModel = hiltViewModel()) {
    val state = favoritesViewModel.state.collectAsState().value
    val basketState = favoritesViewModel.basketState.collectAsState().value
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        favoritesViewModel.getFavorites()
    }

    LaunchedEffect(basketState.successMessage) {
        basketState.successMessage?.let {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
        }
    }

    Column(
        modifier = Modifier
            .background(colorResource(id = com.finalproject.util.R.color.prime2))
            .fillMaxSize()
            .padding(bottom = 66.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TopBar()
        Box(modifier = Modifier.fillMaxSize()) {
            when {
                state.isLoading -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        LoadingAnimation(
                            circleSize = 35.dp,
                            circleColor = colorResource(id = com.finalproject.util.R.color.colorAccent),
                            spaceBetween = 10.dp,
                            travelDistance = 20.dp
                        )
                    }
                }
                state.error != null -> {
                    Text(
                        text = "Error: ${state.error}",
                        modifier = Modifier
                            .align(Alignment.Center)
                            .padding(16.dp),
                        color = Color.White
                    )
                }
                state.favoriteMovies.isNotEmpty() -> {
                    LazyColumn(
                        modifier = Modifier
                            .padding(horizontal = 16.dp)
                            .fillMaxSize()
                    ) {
                        items(state.favoriteMovies) { movie ->
                            FavoriteItem(movie = movie, navController = navController)
                        }
                    }
                }
                else -> {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.fillMaxSize(),
                    ) {
                        Spacer(modifier = Modifier.weight(1f))

                        androidx.compose.material.Text(
                            "Favorileriniz Boş",
                            style = MaterialTheme.typography.headlineMedium,
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            color = colorResource(id = com.finalproject.util.R.color.colorAccent)
                        )

                        val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.favorite))
                        val progress by animateLottieCompositionAsState(composition, iterations = LottieConstants.IterateForever)

                        LottieAnimation(
                            composition = composition,
                            progress = progress,
                            modifier = Modifier.size(220.dp)
                        )

                        Spacer(modifier = Modifier.weight(1f))
                    }
                }
            }

                if (basketState.isLoading) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    LoadingAnimation(
                        circleSize = 35.dp,
                        circleColor = colorResource(id = com.finalproject.util.R.color.colorAccent),
                        spaceBetween = 10.dp,
                        travelDistance = 20.dp
                    )
                }
        }
    }
}
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar() {
    androidx.compose.material3.TopAppBar(
        title = {
            androidx.compose.material.Text(
                text = "Favorilerim",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 14.dp, bottom = 10.dp),
                fontSize = 24.sp,
                color = colorResource(id = com.finalproject.util.R.color.white),
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
        },
        colors = TopAppBarDefaults.mediumTopAppBarColors(
            colorResource(id = com.finalproject.util.R.color.prime)
        ),
        modifier = Modifier.height(62.dp)
    )
}
