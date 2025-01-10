package com.finalproject.home.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.finalproject.domain.model.movie.Movie
import com.finalproject.home.HomeViewModel
import com.finalproject.util.Constants.getImageUrl
import com.finalproject.util.R
import com.skydoves.landscapist.animation.circular.CircularRevealPlugin
import com.skydoves.landscapist.coil.CoilImage
import com.skydoves.landscapist.components.rememberImageComponent

@Composable
fun MostPopular(
    navController: NavController,
    movie: Movie,
    homeViewModel: HomeViewModel = hiltViewModel()
) {
    Card(
        modifier = Modifier
            .fillMaxWidth(0.5f)
            .height(300.dp)
            .padding(2.dp, end = 6.dp)
            .clickable {
                navController.navigate("movie_detail_screen/${movie.id}")
            },
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Black)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(colorResource(id = R.color.darkAccent))
        ) {
            val imageUrl = getImageUrl(movie.image)

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(220.dp)
                    .width(150.dp)
            ) {
                CoilImage(
                    imageModel = { imageUrl },
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(RoundedCornerShape(8.dp)),
                    component = rememberImageComponent {
                        +CircularRevealPlugin(duration = 550)
                    },
                    failure = {
                        Text(text = "Image load failed.")
                    }
                )

                Text(
                    text = "$${movie.price}",
                    color = colorResource(id = R.color.darkYellow),
                    style = MaterialTheme.typography.bodyLarge,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.W700,
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(6.dp)
                        .background(
                            Color.Black.copy(alpha = 0.8f),
                            RoundedCornerShape(4.dp)
                        )
                        .padding(horizontal = 8.dp, vertical = 2.dp)
                )
            }

            Text(
                text = movie.name,
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 1,
                color = Color.White,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    .padding(4.dp)
                    .fillMaxWidth()
            )

            Text(
                text = "${movie.year} - ${movie.category}",
                fontSize = 13.sp,
                color = Color.White,
                modifier = Modifier.padding(start = 4.dp, end = 4.dp, bottom = 4.dp)
            )

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(start = 8.dp, end = 8.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Star,
                    contentDescription = "Star Icon",
                    tint = Color.Yellow,
                    modifier = Modifier.size(16.dp)
                )
                Text(
                    text = "${movie.rating}",
                    color = Color.White,
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.padding(start = 4.dp)
                )
            }
        }
    }
}
