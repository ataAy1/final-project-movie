package com.finalproject.payment

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import com.finalproject.domain.model.payment.DiscountCouponModel
import com.finalproject.domain.model.payment.OrderModel
import com.finalproject.payment.component.AddressComponent
import com.finalproject.payment.component.AddressSelectionDialog
import com.finalproject.payment.component.CardItem
import com.finalproject.payment.component.CardSelectionDialog
import com.finalproject.payment.component.DiscountCouponItem
import com.finalproject.payment.component.PaymentItem
import com.finalproject.payment.component.UserInfoItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PaymentScreen(
    orderModelList: List<OrderModel>,
    navController: NavController,
    paymentViewModel: PaymentViewModel = hiltViewModel()
) {
    val state by paymentViewModel.state.collectAsState()
    val addresses by paymentViewModel.addresses.collectAsState()
    val cards by paymentViewModel.cards.collectAsState()

    val userSettings by paymentViewModel.userSettings.collectAsState()

    var selectedCard by remember(cards) { mutableStateOf(cards.firstOrNull()) }
    var selectedAddress by remember(addresses) { mutableStateOf(addresses.firstOrNull()) }
    var selectedCoupon by remember { mutableStateOf<DiscountCouponModel?>(null) }

    var showAddressDialog by remember { mutableStateOf(false) }
    var showCardDialog by remember { mutableStateOf(false) }

    val discountCouponState by paymentViewModel.discountCouponState.collectAsState()

    LaunchedEffect(cards) {
        if (selectedCard == null && cards.isNotEmpty()) {
            selectedCard = cards.first()
        }
    }

    LaunchedEffect(Unit) {
        paymentViewModel.getDiscountCoupons()
    }

    LaunchedEffect(addresses) {
        if (selectedAddress == null && addresses.isNotEmpty()) {
            selectedAddress = addresses.first()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Sipariş Onayı",
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(colorResource(id = com.finalproject.util.R.color.prime))
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
                                .background(Color(0xFF1F1D2B))
                                .padding(top = 4.dp),
                            contentDescription = "Back",
                        )
                    }
                },
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    colorResource(id = com.finalproject.util.R.color.prime)
                ),
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .background(Color(0xFF1F1D2B))
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            userSettings?.let {
                UserInfoItem(
                    navController = navController,
                    username = it.username,
                    userPhone = it.userPhone,
                    userMail = it.userMail,
                )
            }


            if (orderModelList.get(0).deliveryOption == "cargo") {
                selectedAddress?.let { address ->
                    AddressComponent(address = address) {
                        showAddressDialog = true
                    }
                }

            }

            selectedCard?.let { card ->
                CardItem(card = card) {
                    showCardDialog = true
                }
            }

            androidx.compose.material.Text(
                text = "İndirim Kuponları:",
                color = Color.White,
                modifier = Modifier.padding(top = 4.dp, start = 14.dp),
                fontWeight = FontWeight.Bold,
                style = androidx.compose.material.MaterialTheme.typography.h6
            )

            val onCouponSelected: (DiscountCouponModel) -> Unit = { coupon ->
                selectedCoupon = if (selectedCoupon == coupon) {
                    null
                } else {
                    coupon
                }
            }

            LazyRow(modifier = Modifier.fillMaxWidth()) {
                items(discountCouponState.discountCoupons) { coupon ->
                    DiscountCouponItem(
                        coupon = coupon,
                        selectedCoupon = selectedCoupon,
                        onCouponSelected = onCouponSelected
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            val cargoPrice = if (orderModelList.get(0).deliveryOption == "coupon") 0.0 else 15.0

            PaymentItem(
                navController = navController,
                orderModelList = orderModelList,
                cargoPrice = cargoPrice,
                paymentViewModel = paymentViewModel,
                selectedAddress = selectedAddress,
                        selectedCoupon = selectedCoupon
            )

            when (state) {
                is PaymentState.Idle -> {}

                is PaymentState.Saving -> Text("Saving your orders...", color = Color.Gray)
                is PaymentState.Success -> Text(
                    (state as PaymentState.Success).message,
                    color = Color.Green
                )

                is PaymentState.Error -> Text(
                    "Error: ${(state as PaymentState.Error).error}",
                    color = Color.Red
                )
            }
        }

        if (showCardDialog) {
            CardSelectionDialog(
                cards = cards,
                selectedCard = selectedCard,
                onCardSelected = { card ->
                    selectedCard = card
                },
                onDismissRequest = {
                    showCardDialog = false
                }
            )
        }
        
            if (showAddressDialog) {
                AddressSelectionDialog(
                    addresses = addresses,
                    selectedAddress = selectedAddress,
                    onAddressSelected = { address ->
                        selectedAddress = address
                    },
                    onDismissRequest = { showAddressDialog = false }
                )
            }
        }

    }







