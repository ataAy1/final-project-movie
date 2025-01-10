package com.finalproject.basket

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.finalproject.basket.component.BasketItemCard
import com.finalproject.domain.model.payment.OrderModel
import com.google.gson.Gson
import java.util.Date
import java.util.UUID

@Composable
fun BasketScreen(
    navController: NavController,
    basketViewModel: BasketViewModel = hiltViewModel()
) {
    val uiState by basketViewModel.uiState.collectAsState()
    val userName = "deneme1234d"

    LaunchedEffect(key1 = userName) {
        basketViewModel.getBasketItems(userName)
    }


    Column(
        modifier = Modifier
            .background(colorResource(id = com.finalproject.util.R.color.prime))
            .fillMaxSize()
            .padding(bottom = 66.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TopBar(navController = navController)


        when (uiState) {
            is BasketState.Idle -> {

            }
            is BasketState.Success -> {
                val items = (uiState as BasketState.Success).items
                val sortedItems = items.sortedBy { it.name }

                if (items.isEmpty()) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("Sepetiniz Boş..")
                        val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.coupon_generator))
                        val progress by animateLottieCompositionAsState(composition, iterations = LottieConstants.IterateForever)

                        LottieAnimation(
                            composition = composition,
                            progress = progress,
                            modifier = Modifier.size(220.dp)
                        )
                    }
                } else {
                    LazyColumn(
                        modifier = Modifier.weight(1f)
                    ) {
                        items(sortedItems.size) { index ->
                            val item = sortedItems[index]
                            BasketItemCard(
                                item = item,
                                onIncreaseQuantity = {
                                    basketViewModel.addOrUpdateBasketItem(
                                        userName,
                                        item.copy(orderAmount = item.orderAmount + 1)
                                    )
                                },
                                onDecreaseQuantity = {
                                    if (item.orderAmount > 1) {
                                        basketViewModel.addOrUpdateBasketItem(
                                            userName,
                                            item.copy(orderAmount = item.orderAmount - 1)
                                        )
                                    } else {
                                        basketViewModel.removeBasketItem(item.cartId, userName)
                                    }
                                }
                            )
                        }

                    }
                }

                Divider(
                    modifier = Modifier.padding(start = 10.dp, end = 10.dp, bottom = 8.dp),
                    color = colorResource(id = com.finalproject.util.R.color.colorAccent),
                    thickness = 2.dp
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(2.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {

                    val totalPrice = sortedItems.sumOf { it.price * it.orderAmount }

                    Text(
                        text = "Toplam:  $${totalPrice}",
                        color = Color.White,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .padding(start = 16.dp)
                    )


                    Button(
                    onClick = {
                        val orderId = UUID.randomUUID().toString()

                        val summarizedItems = (uiState as BasketState.Success).items
                            .groupBy { it.name }
                            .map { (name, items) ->
                                val totalQuantity = items.sumOf { it.orderAmount }
                                val totalPrice = items.sumOf { it.price * it.orderAmount }
                                val orderNo =orderId
                                OrderModel(
                                    name = name,
                                    image = items.first().image,
                                    price = items.first().price,
                                    orderAmount = totalQuantity,
                                    totalPrice = totalPrice,
                                    userName = "Ataberk Aydın",
                                    movieID = items.first().cartId,
                                    date = Date(),
                                    orderNo =orderNo,
                                    deliveryOption = ""
                                )
                            }
                        val orderJson = Gson().toJson(summarizedItems)


                        navController.navigate("delivery_option_screen/$orderJson")
                    },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 30.dp, end = 20.dp),
                        colors = ButtonDefaults.buttonColors(colorResource(id = com.finalproject.util.R.color.darkAccent))
                    ) {
                        Text(
                            "Ödemeye Geç",
                            fontWeight = FontWeight.W900,
                            fontSize = 16.sp,
                            color = colorResource(id = com.finalproject.util.R.color.colorAccent)
                        )
                    }
            }
            }

            is BasketState.Error -> {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxSize()
                ) {
                    Text(
                        text = "Sepetiniz Boş ",
                        color = colorResource(id = com.finalproject.util.R.color.colorAccent),
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                    )
                    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.empty_basket))
                    val progress by animateLottieCompositionAsState(composition, iterations = LottieConstants.IterateForever)

                    LottieAnimation(
                        composition = composition,
                        progress = progress,
                        modifier = Modifier.size(300.dp)
                    )
                }
            }
        }



    }
}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(navController: NavController) {
    androidx.compose.material3.TopAppBar(
        title = {
            androidx.compose.material.Text(
                text = "Sepetim",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 54.dp),
                fontSize = 22.sp,
                color = colorResource(id = com.finalproject.util.R.color.white),
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
        },
        navigationIcon = {
            androidx.compose.material3.IconButton(onClick = { navController.navigateUp() }) {

            }
        },
        colors = TopAppBarDefaults.mediumTopAppBarColors(
            colorResource(id = com.finalproject.util.R.color.prime)
        ),
        modifier = Modifier.height(62.dp)
    )
}
