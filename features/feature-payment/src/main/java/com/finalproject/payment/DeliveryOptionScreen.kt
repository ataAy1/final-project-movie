package com.finalproject.payment

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material.TabRowDefaults
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.finalproject.domain.model.payment.OrderModel
import com.google.gson.Gson

@Composable
fun DeliveryOptionScreen(
    orderModelList: List<OrderModel>,
    navController: NavController,
    paymentViewModel: PaymentViewModel = hiltViewModel()
) {

    var updatedOrderModelList = orderModelList

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = com.finalproject.util.R.color.prime2))
    ) {
        TopBar(navController = navController)

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(colorResource(id = com.finalproject.util.R.color.prime2))
                .padding(horizontal = 42.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {

            Button(
                onClick = {
                    updatedOrderModelList = updatedOrderModelList.map {
                        it.copy(deliveryOption = "cargo")
                    }

                    val orderJson = Gson().toJson(updatedOrderModelList)

                    navController.navigate("payment_screen/$orderJson")
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(400.dp)
                    .border(width = 1.dp, colorResource(id = com.finalproject.util.R.color.white))
                    .padding(top = 6.dp, bottom = 16.dp)
                    .background(color = colorResource(id = com.finalproject.util.R.color.darkAccent)),
                contentPadding = PaddingValues(0.1.dp)
            ) {
                val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.cargo))

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .fillMaxSize()
                        .background(color = colorResource(id = com.finalproject.util.R.color.darkAccent)),
                ) {
                    LottieAnimation(
                        composition = composition,
                        iterations = LottieConstants.IterateForever,
                        modifier = Modifier.size(280.dp)
                    )
                    Spacer(modifier = Modifier.height(2.dp))

                    TabRowDefaults.Divider(
                        color = colorResource(id = com.finalproject.util.R.color.white),
                        thickness = 2.dp,
                        modifier = Modifier.padding(bottom = 6.dp)
                    )
                    Text("Kargo Yöntemi",
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(start = 20.dp),
                        fontSize = 24.sp,
                        color = colorResource(id = com.finalproject.util.R.color.colorAccent),
                        textAlign = TextAlign.Center)
                }
            }

            Button(
                onClick = {
                    updatedOrderModelList = updatedOrderModelList.map {
                        it.copy(deliveryOption = "coupon")
                    }

                    val orderJson = Gson().toJson(updatedOrderModelList)

                    navController.navigate("payment_screen/$orderJson")
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(400.dp)
                    .border(
                        width = 1.dp,
                        color = colorResource(id = com.finalproject.util.R.color.colorAccent)
                    )
                    .padding(top = 2.dp, bottom = 16.dp)
                    .background(color = colorResource(id = com.finalproject.util.R.color.darkAccent)),
                contentPadding = PaddingValues(0.2.dp)
            ) {
                val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.ticket_1))

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .fillMaxSize()

                        .background(color = colorResource(id = com.finalproject.util.R.color.darkAccent)),
                ) {
                    LottieAnimation(
                        composition = composition,
                        iterations = LottieConstants.IterateForever,
                        modifier = Modifier.size(280.dp)
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    TabRowDefaults.Divider(
                        color = colorResource(id = com.finalproject.util.R.color.white),
                        thickness = 2.dp,
                        modifier = Modifier.padding(bottom = 6.dp)
                    )
                    Text("Kupon Yöntemi",
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(start = 20.dp),
                        fontSize = 24.sp,
                        color = colorResource(id = com.finalproject.util.R.color.colorAccent))
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(navController: NavController) {
    TopAppBar(
        title = {
            Text(
                text = "Teslimat Yöntemi",
                modifier = Modifier
                    .fillMaxWidth()
                    .background(colorResource(id = com.finalproject.util.R.color.prime))
                    .padding(top = 10.dp, end = 54.dp),
                fontSize = 22.sp,
                color = colorResource(id = com.finalproject.util.R.color.colorAccent),
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
        },
        navigationIcon = {
            IconButton(onClick = { navController.navigateUp() }) {
                Image(
                    painterResource(id = R.drawable.ic_arrow),
                    modifier = Modifier
                        .size(40.dp)
                        .background(Color(0xFF1F1D2B))
                        .padding(bottom = 4.dp),
                    contentDescription = "Back",
                )
            }
        },
        colors = TopAppBarDefaults.mediumTopAppBarColors(
            colorResource(id = com.finalproject.util.R.color.prime)
        ),
        modifier = Modifier.height(70.dp)

    )
}
