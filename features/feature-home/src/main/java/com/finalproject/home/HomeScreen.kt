package com.finalproject.home

import CategoryButtons
import android.util.Log

import com.google.gson.Gson
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import com.finalproject.domain.model.movie.Movie
import com.finalproject.home.component.AllMoviesItem
import com.finalproject.home.component.BestSellingItem
import com.finalproject.home.component.HomeBarWithLogo
import com.finalproject.home.component.ImageSlider
import com.finalproject.home.component.MostPopular
import com.finalproject.util.LoadingAnimation

@Composable
fun HomeScreen(
    navController: NavController,
    homeViewModel: HomeViewModel = hiltViewModel()
) {
    val state = homeViewModel.state.collectAsState().value
    var selectedCategory by remember { mutableStateOf("All") }

    LaunchedEffect(Unit) {
        homeViewModel.getMovies()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = com.finalproject.util.R.color.prime2)),
        contentAlignment = Alignment.Center
    ) {
        when {
            state.isLoading -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .wrapContentSize()
                ) {
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
                    fontSize = 18.sp,
                    color = Color.Red
                )
            }

            state.movies != null -> {


                val sortedMovies = state.movies.sortedByDescending { it.rating }

                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(12.dp, bottom = 60.dp, top = 4.dp)
                ) {

                    item {
                        HomeBarWithLogo()                    }
                    item {
                        ImageSlider()
                    }

                    item {
                        Text(
                            text = "Çok Satanlar",
                            style = MaterialTheme.typography.titleLarge.copy(
                                color = MaterialTheme.colorScheme.onPrimary,
                                fontWeight = FontWeight.Bold,
                                fontSize = 20.sp,
                            ),
                            modifier = Modifier
                                .align(Alignment.CenterStart)
                                .padding(bottom = 4.dp)
                        )
                        LazyRow(
                            modifier = Modifier.padding(vertical = 8.dp)
                        ) {
                            items(state.movies) { movie ->
                                BestSellingItem(navController = navController, movie = movie)
                            }
                        }
                    }

                    item {
                        Text(
                            text = "Çok Beğenilenler",
                            style = MaterialTheme.typography.titleLarge.copy(
                                color = MaterialTheme.colorScheme.onPrimary,
                                fontWeight = FontWeight.Bold,
                                fontSize = 20.sp,
                            ),
                            modifier = Modifier
                                .align(Alignment.CenterStart)
                                .padding(vertical = 4.dp)
                        )
                        LazyRow(
                            modifier = Modifier.padding(vertical = 8.dp)
                        ) {
                            items(sortedMovies) { movie ->
                                MostPopular(navController = navController, movie = movie)
                            }
                        }
                    }
                    val filteredMovies = if (selectedCategory == "All") {
                        state.movies
                    } else {
                        state.movies.filter { it.category == selectedCategory }
                    }

                    item {
                        Text(
                            text = "Satıştaki Tüm Filmler",
                            style = MaterialTheme.typography.titleLarge.copy(
                                color = MaterialTheme.colorScheme.onPrimary,
                                fontWeight = FontWeight.Bold,
                                fontSize = 20.sp
                            ),
                            modifier = Modifier.padding(bottom = 10.dp, top = 2.dp)
                        )
                    }

                    item {
                        CategoryButtons(
                            categories = listOf("All", "Action","Science Fiction","Fantastic"),
                            selectedCategory = selectedCategory,
                            onCategorySelected = { category -> selectedCategory = category }
                        )
                    }

                    items(filteredMovies) { movie ->
                        AllMoviesItem(movie = movie, navController = navController)
                    }
                }
            }
        }
    }
}
