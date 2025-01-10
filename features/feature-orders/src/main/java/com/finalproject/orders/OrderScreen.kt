package com.finalproject.orders

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.finalproject.orders.component.OrderItem
import com.finalproject.util.LoadingAnimation


@Composable
fun OrderScreen(
    navController: NavController,
    viewModel: OrdersViewModel = hiltViewModel()
) {
    val ordersState by viewModel.ordersState.collectAsState()


    LaunchedEffect(Unit) {
        viewModel.fetchOrders()
    }

    Column(modifier = Modifier.fillMaxSize()) {

        TopBar(navController)

        when (ordersState) {
            is OrdersState.Loading -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .wrapContentSize()
                ) {
                    LoadingAnimation(
                        circleSize = 25.dp,
                        circleColor = colorResource(id = com.finalproject.util.R.color.colorAccent),
                        spaceBetween = 10.dp,
                        travelDistance = 20.dp
                    )
                }
            }

            is OrdersState.Empty -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                ) {
                    Text("Siparişiniz Yok")
                }
            }

            is OrdersState.Success -> {
                val orders = (ordersState as OrdersState.Success).orders


                val groupedOrders = orders.groupBy { it.orderNo }


                val sortedGroupedOrders =
                    groupedOrders.toList().sortedByDescending { (_, orderList) ->
                        orderList.maxOfOrNull { it.date }
                    }

                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(colorResource(id = com.finalproject.util.R.color.prime2))
                        .padding(2.dp),
                    verticalArrangement = Arrangement.spacedBy(2.dp)
                ) {
                    sortedGroupedOrders.forEach { (orderNo, orderList) ->
                        item {
                            OrderItem(orderList, orderNo)
                        }
                    }
                }
            }

            is OrdersState.Error -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                ) {
                    Text(
                        text = "Hata",
                        color = colorResource(id = com.finalproject.util.R.color.darkRed)
                    )
                }
            }

            is OrdersState.Idle -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                ) {
                    Text("Siparişler Bekleniyor")
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
            androidx.compose.material.Text(
                text = "Siparişlerim",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 54.dp),
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
                        .padding(top = 2.dp),
                    contentDescription = "Back",
                )
            }
        },
        colors = TopAppBarDefaults.mediumTopAppBarColors(
            colorResource(id = com.finalproject.util.R.color.prime)
        ),
    )
}