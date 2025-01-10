package com.finalproject.coupon

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.finalproject.coupon.component.CouponItem


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CouponScreen(
    navController: NavController,
    viewModel: CouponViewModel = hiltViewModel()
) {
    val couponState = viewModel.couponState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.getCoupons()
    }

    Column(
        modifier = Modifier
            .background(colorResource(id = com.finalproject.util.R.color.prime2))
            .fillMaxSize()
            .padding(bottom = 66.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TopBar(navController = navController)
        if (couponState.value.isLoading) {
            CircularProgressIndicator(modifier = Modifier.fillMaxSize())
        } else if (couponState.value.error != null) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(colorResource(id = com.finalproject.util.R.color.darkAccent))
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "Error: ${couponState.value.error}")
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .background(colorResource(id = com.finalproject.util.R.color.darkAccent)),
                contentPadding = PaddingValues(24.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                val sortedCoupons = couponState.value.coupons.sortedByDescending { it.date }

                items(sortedCoupons) { coupon ->
                    CouponItem(coupon)
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
                text = "Kodlarım",
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
            IconButton(onClick = { navController.popBackStack()}) {
                Image(
                    painterResource(id = R.drawable.ic_arrow),
                    modifier = Modifier
                        .size(40.dp)
                        .padding(top = 4.dp),
                    contentDescription = "Back",
                )
            }
        },
        colors = TopAppBarDefaults.mediumTopAppBarColors(
            colorResource(id = com.finalproject.util.R.color.prime)
        ),
        modifier = Modifier.height(62.dp)
    )
}
