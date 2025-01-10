package com.finalproject.movie.detail.component

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.TabRowDefaults.Divider
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Face
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.finalproject.domain.model.movie.Movie
import com.finalproject.movie.detail.MovieDetailViewModel
import com.finalproject.movie.detail.R
import com.finalproject.util.LoadingAnimation

@Composable
fun MovieDetailButton(
    movie: Movie,
    movieDetailViewModel: MovieDetailViewModel
) {
    val basketState by movieDetailViewModel.basketState.collectAsState()
    var selectedAmount by remember { mutableStateOf(1) }
    var isDropdownExpanded by remember { mutableStateOf(false) }
    val context = LocalContext.current

    val totalPrice = movie.price * selectedAmount




    Button(
        onClick = {
            movieDetailViewModel.addMovieToBasket(movie, selectedAmount, "deneme1234d")

        },
        modifier = Modifier
            .padding(bottom = 2.dp, start = 100.dp, end = 100.dp)
            .fillMaxWidth(),
        colors = ButtonDefaults.buttonColors(
            containerColor = colorResource(id = com.finalproject.util.R.color.prime),
            contentColor = Color.White
        )
    ) {
        if (basketState.isLoading) {
            LoadingAnimation(
                circleSize = 35.dp,
                circleColor = colorResource(id = com.finalproject.util.R.color.colorAccent),
                spaceBetween = 10.dp,
                travelDistance = 20.dp
            )
        } else {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box {
                    Text(
                        text = "$selectedAmount Adet",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        modifier = Modifier
                            .padding(start = 46.dp, top = 5.dp)
                    )
                    Image(
                        painter = painterResource(id = R.drawable.ic_shopping),
                        contentDescription = "Open amount selection",
                        modifier = Modifier
                            .size(30.dp)
                            .padding(start = 4.dp)
                            .clickable { isDropdownExpanded = true }
                    )


                    DropdownMenu(
                        expanded = isDropdownExpanded,
                        onDismissRequest = {
                            isDropdownExpanded = false
                        },
                        modifier = Modifier.background(colorResource(id = com.finalproject.util.R.color.prime))
                    ) {
                        Column(modifier = Modifier.padding(4.dp)) {
                            (1..10).forEachIndexed { index, amount ->
                                DropdownMenuItem(
                                    onClick = {
                                        selectedAmount = amount
                                        isDropdownExpanded = false

                                    },
                                    modifier = Modifier.background(colorResource(id = com.finalproject.util.R.color.prime))
                                ) {
                                    Text(
                                        text = amount.toString(),
                                        textAlign = TextAlign.Center,
                                        modifier = Modifier.padding(start = 30.dp),
                                        color = colorResource(id = com.finalproject.util.R.color.colorAccent)
                                    )
                                }
                                if (index < 9) {
                                    Divider(color = Color.White, thickness = 1.dp)
                                }
                            }
                        }
                    }
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "$totalPrice$",
                        fontSize = 20.sp,
                        modifier = Modifier.padding(end = 10.dp),
                        fontWeight = FontWeight.W900,
                        color = colorResource(id = com.finalproject.util.R.color.darkYellow)
                    )
                }
            }
        }
    }
}
