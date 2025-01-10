package com.finalproject.movie.detail.component

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.finalproject.movie.detail.MovieDetailViewModel
import com.finalproject.movie.detail.R

@Composable
fun BasketButton(navController: NavController,movieDetailViewModel: MovieDetailViewModel) {
    val basketItems by movieDetailViewModel.basketItems.collectAsState()

    val totalItems = basketItems.size
    val totalPrice = basketItems.sumOf { it.price * it.orderAmount.toDouble() }


    IconButton(onClick = {
        navController.navigate("basket_screen") {
            launchSingleTop = true
            restoreState = true
        }
    }) {
        Box {
            Icon(
                painter = painterResource(id = R.drawable.ic_shopping),
                contentDescription = "Add to Basket",
                modifier = Modifier
                    .size(32.dp)
                    .padding(top = 6.dp, start = 6.dp),
                tint = colorResource(id = com.finalproject.util.R.color.colorAccent)
            )

            if (totalItems > 0) {
                Text(
                    text = totalItems.toString(),
                    color = colorResource(id = com.finalproject.util.R.color.darkYellow),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.W900,
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .background(
                            color = colorResource(id = com.finalproject.util.R.color.prime),
                            shape = CircleShape
                        )
                        .padding(4.dp)
                        .size(13.dp)
                        .wrapContentSize(align = Alignment.Center)
                )

            }

            if (totalItems > 0) {
                Text(
                    text = "${"%.2f".format(totalPrice)}$",
                    color = Color.White,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.W900,
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(top = 33.dp)
                )
            }
        }
    }
}