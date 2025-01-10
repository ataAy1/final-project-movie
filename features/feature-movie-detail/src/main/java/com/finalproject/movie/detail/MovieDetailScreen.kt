package com.finalproject.movie.detail

import android.util.Log
import android.webkit.WebView
import android.widget.Toast
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.SnackbarDuration
import androidx.compose.material.SnackbarHost
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
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
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.finalproject.movie.detail.MovieDetailViewModel
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.compose.LocalLifecycleOwner
import coil.compose.rememberAsyncImagePainter
import com.finalproject.domain.model.movie.Movie
import com.finalproject.movie.detail.component.BasketButton
import com.finalproject.movie.detail.component.CastComponent
import com.finalproject.movie.detail.component.CinemaxOverlay
import com.finalproject.movie.detail.component.DetailItem
import com.finalproject.movie.detail.component.MovieDetailButton
import com.finalproject.movie.detail.component.getActorsForMovie
import com.finalproject.util.Constants.getImageUrl
import com.finalproject.util.LoadingAnimation
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView
import com.skydoves.landscapist.animation.circular.CircularRevealPlugin
import com.skydoves.landscapist.coil.CoilImage
import com.skydoves.landscapist.components.rememberImageComponent

@Composable
fun MovieDetailScreen(
    movieId: Int,
    navController: NavController,
    movieDetailViewModel: MovieDetailViewModel = hiltViewModel()
) {

    val favoriteState = movieDetailViewModel.favoriteState.collectAsState().value
    val state = movieDetailViewModel.state.collectAsState().value
    val movie = state.movie.collectAsState(initial = null).value
    val basketState = movieDetailViewModel.basketState.collectAsState().value

    var isZoomed by remember { mutableStateOf(false) }
    val width by animateDpAsState(targetValue = if (isZoomed) 260.dp else 205.dp)
    val height by animateDpAsState(targetValue = if (isZoomed) 420.dp else 327.dp)
    var showVideo by remember { mutableStateOf(false) }
    val verticalPadding by animateDpAsState(targetValue = if (isZoomed) 40.dp else 144.dp)
    val context = LocalContext.current
    val hasShownToast = remember { mutableStateOf(false) }
    val successMessage = basketState.successMessage

    LaunchedEffect(movieId) {
        movieDetailViewModel.getMovieById(movieId)
        movieDetailViewModel.basketResetState()

    }


    LaunchedEffect(isZoomed) {
        if (isZoomed) {
            kotlinx.coroutines.delay(3250)
            isZoomed = false
        }
    }

    LaunchedEffect(successMessage) {
        successMessage?.let { message ->
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                movieDetailViewModel.basketResetState()
            }
        }





    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF1F1D2B))
    ) {
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
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(text = "Error: ${state.error}", fontSize = 18.sp, color = Color.Red)
                }
            }

            movie != null -> {
                Box {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(532.dp)
                    ) {
                        val backdropUrl = getImageUrl(movie.image)
                        val painter = rememberAsyncImagePainter(backdropUrl)

                        Image(
                            painter = painter,
                            contentDescription = movie.name,
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )

                        CinemaxOverlay(
                            color1 = Color.Black,
                            color2 = Color.Black,
                            alpha = 0.75f
                        )
                    }

                    Box(
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .padding(bottom = verticalPadding)
                    ) {
                        val posterUrl = getImageUrl(movie.image)


                        CoilImage(
                            imageModel = { posterUrl },
                            modifier = Modifier
                                .size(width = width, height = height)
                                .clickable {
                                    isZoomed = true
                                },
                            component = rememberImageComponent {
                                +CircularRevealPlugin(
                                    duration = 550
                                )

                            },
                            loading = {
                                Box(modifier = Modifier.matchParentSize()) {
                                    CircularProgressIndicator(
                                        modifier = Modifier.align(Alignment.Center)
                                    )
                                }
                            },
                            failure = {
                                Text(
                                    text = "Image request failed.",
                                    modifier = Modifier.align(Alignment.Center)
                                )
                            }
                        )
                    }

                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 18.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_arrow),
                            contentDescription = "Back",
                            modifier = Modifier
                                .size(38.dp)
                                .padding(start = 8.dp),
                        )
                    }

                    Spacer(modifier = Modifier.weight(1f))

                    Text(
                        text = movie.name,
                        style = MaterialTheme.typography.body2,
                        color = colorResource(id = com.finalproject.util.R.color.white),
                        fontSize = 24.sp,
                        fontWeight = FontWeight.W900,
                        modifier = Modifier
                            .padding(bottom = 8.dp, start = 10.dp),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )

                    Spacer(modifier = Modifier.weight(1f))

                    BasketButton(
                        navController = navController,
                        movieDetailViewModel = movieDetailViewModel
                    )


                    IconButton(onClick = {
                        if (!movie.isFavorite) {
                            movieDetailViewModel.addMovieToFavorites(movie)
                        } else {
                            movieDetailViewModel.removeFromFavorites(movie.id)
                        }
                    }) {
                        Icon(
                            imageVector = if (movie.isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                            contentDescription = "Favorite",
                            modifier = Modifier
                                .padding(end = 8.dp, top = 6.dp)
                                .size(28.dp),
                            tint = if (movie.isFavorite) colorResource(id = com.finalproject.util.R.color.colorAccent) else colorResource(
                                id = com.finalproject.util.R.color.colorAccent
                            )
                        )
                    }
                }


                if (!isZoomed) {
                    Column(
                        modifier = Modifier
                            .padding(top = 380.dp)
                            .fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        DetailItem(
                            label = "Year",
                            value = movie.year.toString(),
                            textColor = Color.White,
                            fontWeight = FontWeight.W900,
                            image = painterResource(id = R.drawable.ic_date)
                        )

                        Spacer(modifier = Modifier.height(2.dp))

                        DetailItem(
                            label = "Category",
                            value = movie.category.toString(),
                            textColor = Color.White,
                            fontWeight = FontWeight.W900,
                            image = painterResource(id = R.drawable.ic_category)
                        )

                        Spacer(modifier = Modifier.height(2.dp))

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 84.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {

                            Button(
                                onClick = { showVideo = !showVideo },
                                modifier = Modifier.padding(0.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = colorResource(
                                        id = com.finalproject.util.R.color.prime2
                                    )
                                ),
                                shape = RoundedCornerShape(22.dp)
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.ic_play),
                                        contentDescription = "Play Icon",
                                        tint = colorResource(id = com.finalproject.util.R.color.colorAccent),
                                        modifier = Modifier.size(22.dp)
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text(
                                        text = "Fragman",
                                        color = colorResource(id = com.finalproject.util.R.color.colorAccent),
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.Bold,
                                        textAlign = TextAlign.Center
                                    )
                                }
                            }


                            Row(
                                modifier = Modifier
                                    .wrapContentWidth()
                                    .padding(start = 20.dp),

                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Image(
                                    painter = painterResource(id = R.drawable.ic_star_yellow_detail),
                                    contentDescription = "Rating Icon",
                                    modifier = Modifier
                                        .size(28.dp)
                                        .padding(end = 4.dp)
                                )

                                Text(
                                    text = movie.rating.toString(),
                                    color = colorResource(id = com.finalproject.util.R.color.darkYellow),
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.padding(start = 4.dp, end = 14.dp),


                                    )
                            }


                        }


                    }


                }
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.Center)
                        .padding(top = 486.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 20.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(
                            modifier = Modifier
                                .weight(1f)
                                .padding(end = 8.dp)
                        ) {

                            Text(
                                text = "Özet:",
                                fontSize = 17.sp,
                                color = Color.White,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(bottom = 2.dp)
                            )
                            Text(
                                text = movie.description + movie.description,
                                fontSize = 12.5.sp,
                                color = Color.White
                            )

                            Text(
                                text = "Oyuncular:",
                                fontSize = 18.sp,
                                color = Color.White,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(bottom = 4.dp, top = 10.dp)
                            )
                            CastComponent(movie.name.toString())
                        }

                    }

                }

                if (showVideo) {
                    AlertDialog(
                        onDismissRequest = { showVideo = false },
                        title = {
                            Text(
                                "İyi Seyirler !",
                                color = colorResource(id = com.finalproject.util.R.color.colorAccent)
                            )
                        },
                        text = {
                            YoutubePlayer(
                                youtubeVideoId = "JTSoD4BBCJc",
                                lifecycleOwner = LocalLifecycleOwner.current,
                                youTubePlayer = null
                            )
                        },
                        confirmButton = {
                            TextButton(
                                onClick = { showVideo = false }
                            ) {
                                Text(
                                    "Kapat",
                                    color = colorResource(id = com.finalproject.util.R.color.colorAccent)
                                )
                            }
                        },
                        modifier = Modifier
                            .height(350.dp)
                            .width(600.dp)
                            .background(colorResource(id = com.finalproject.util.R.color.black)),
                        containerColor = colorResource(id = com.finalproject.util.R.color.black)
                    )
                }



                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.BottomCenter)
                        .padding(bottom = 16.dp)
                ) {
                    MovieDetailButton(movie = movie, movieDetailViewModel = movieDetailViewModel)
                }
            }
        }

    }
}


@Composable
fun YoutubePlayer(
    youtubeVideoId: String,
    lifecycleOwner: LifecycleOwner,
    youTubePlayer: YouTubePlayer?
) {
    AndroidView(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(8.dp)
            .background(colorResource(id = com.finalproject.util.R.color.prime2))
            .clip(RoundedCornerShape(16.dp)),
        factory = { context ->
            YouTubePlayerView(context).apply {
                lifecycleOwner.lifecycle.addObserver(this)
                addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
                    override fun onReady(youTubePlayer: YouTubePlayer) {
                        youTubePlayer.loadVideo(youtubeVideoId, 8f)
                        matchParent()
                    }
                })

            }
        }
    )
}

