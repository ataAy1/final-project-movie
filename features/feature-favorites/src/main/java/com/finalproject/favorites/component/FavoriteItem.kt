package com.finalproject.favorites.component


import android.media.Image
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.finalproject.domain.model.movie.Movie
import com.finalproject.favorites.FavoritesViewModel
import com.finalproject.favorites.R
import com.finalproject.util.Constants
import com.finalproject.util.Constants.getImageUrl
import com.skydoves.landscapist.animation.circular.CircularRevealPlugin
import com.skydoves.landscapist.coil.CoilImage
import com.skydoves.landscapist.components.rememberImageComponent


@Composable
fun FavoriteItem(
    navController: NavController,
    movie: Movie,
    viewModel: FavoritesViewModel = hiltViewModel()
) {
    val favoriteIcon = if (movie.isFavorite) {
        Icons.Default.Favorite
    } else {
        Icons.Default.FavoriteBorder
    }

    var rating by remember { mutableStateOf(movie.rating) }
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(170.dp)
            .padding(bottom = 8.dp)
            .background(colorResource(id = com.finalproject.util.R.color.darkAccent))
            .clickable {
                navController.navigate("movie_detail_screen/${movie.id}")
            },
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Row(
            modifier = Modifier
                .background(colorResource(id = com.finalproject.util.R.color.darkAccent))
                .fillMaxWidth()

        ) {
            val imageUrl = getImageUrl(movie.image)


            CoilImage(
                imageModel = { imageUrl },
                modifier = Modifier
                    .weight(0.3f)
                    .height(220.dp)
                    .padding(top = 10.dp, bottom = 10.dp, start = 4.dp)
                    .clip(RoundedCornerShape(18.dp)),
                component = rememberImageComponent {
                    +CircularRevealPlugin(duration = 250)
                },
                failure = {
                    Text(text = "Image load failed.")
                }
            )


            Column(
                modifier = Modifier
                    .weight(0.7f)
                    .background(colorResource(id = com.finalproject.util.R.color.darkAccent))
                    .padding(start = 8.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = movie.name,
                        color = Color.White,
                        style = MaterialTheme.typography.headlineSmall,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.weight(1f)
                    )

                    IconButton(
                        onClick = {
                            viewModel.addMovieToBasket(movie,1,"deneme1234d")
                        }
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_shopping),
                            contentDescription = "Favorite",
                            Modifier.padding(start = 14.dp).size(40.dp)
                        )
                    }

                    IconButton(
                        onClick = {
                            viewModel.removeFromFavorites(movie.id)
                        }
                    ) {
                        Icon(
                            imageVector = favoriteIcon,
                            contentDescription = "Favorite",
                            Modifier.padding(end = 14.dp),
                            tint = if (movie.isFavorite) colorResource(id = com.finalproject.util.R.color.colorAccent) else colorResource(
                                id = com.finalproject.util.R.color.colorAccent
                            )
                        )
                    }


                }

                Text(
                    text = "Kategori: ${movie.category}",
                    style = MaterialTheme.typography.bodySmall,
                    color = colorResource(id = com.finalproject.util.R.color.colorAccent),
                    modifier = Modifier.padding(top = 4.dp)
                )

                Text(
                    text = "Yayın Tarihi: ${movie.year}",
                    style = MaterialTheme.typography.bodySmall,
                    color = colorResource(id = com.finalproject.util.R.color.colorAccent),
                    modifier = Modifier.padding(top = 4.dp)
                )

                RatingBarComponent(
                    rating = rating,
                    maxStars = 5,
                    modifier = Modifier.padding(top = 28.dp)
                )

                Text(
                    text = "$${movie.price}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = colorResource(id = com.finalproject.util.R.color.darkYellow),
                    fontSize = 20.sp,
                    modifier = Modifier
                        .padding(start = 220.dp, bottom = 2.dp)
                        .background(
                            colorResource(id = com.finalproject.util.R.color.black).copy(alpha = 0.8f),
                            RoundedCornerShape(4.dp)
                        )
                )
            }
        }
    }
}


@Composable
fun RatingBarComponent(
    maxStars: Int = 5,
    rating: Double,
    modifier: Modifier = Modifier
) {
    val density = LocalDensity.current.density
    val starSize = (7f * density).dp
    val starSpacing = (0.5f * density).dp

    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        for (i in 1..maxStars) {
            val fullStars = (rating / 2).toInt()
            val isFullStar = i <= fullStars
            val isHalfStar = i == fullStars + 1 && rating % 2 != 0.0

            val starImage = when {
                isFullStar -> R.drawable.ic_star_full
                isHalfStar -> R.drawable.ic_star_half
                else -> R.drawable.ic_star_empty
            }

            Image(
                painter = painterResource(id = starImage),
                contentDescription = null,
                modifier = Modifier
                    .width(starSize)
                    .height(starSize)
            )

            if (i < maxStars) {
                Spacer(modifier = Modifier.width(starSpacing))
            }
        }

        Text(
            text = "- %.1f".format(rating),
            style = MaterialTheme.typography.bodySmall.copy(
                color = Color.White,
                fontSize = 14.sp,
            ),
            modifier = Modifier.padding(start = 4.dp)
        )
    }
}
