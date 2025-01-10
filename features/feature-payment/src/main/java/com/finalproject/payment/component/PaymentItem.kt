package com.finalproject.payment.component

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.material.MaterialTheme
import androidx.compose.material.TabRowDefaults.Divider
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.finalproject.domain.model.payment.DiscountCouponModel
import com.finalproject.domain.model.payment.OrderModel
import com.finalproject.domain.model.user.AddressModel
import com.finalproject.payment.PaymentViewModel

@Composable
fun PaymentItem(
    navController: NavController,
    orderModelList: List<OrderModel>,
    cargoPrice: Double,
    paymentViewModel: PaymentViewModel,
    selectedAddress: AddressModel?,
    selectedCoupon: DiscountCouponModel?
) {
    val totalPrice = orderModelList.sumOf { it.price * it.orderAmount }
    val totalPayable = totalPrice + cargoPrice
    val discount = selectedCoupon?.discountAmount?.toDouble() ?: 0.0
    val finalPrice = totalPayable - discount
    val deliveryOption = orderModelList.firstOrNull()?.deliveryOption

    Log.d("PaymentItem", "total price: $totalPrice")

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(end = 16.dp, bottom = 2.dp, start = 16.dp, top = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(330.dp)
                .align(Alignment.BottomStart)
        ) {

            Spacer(
                modifier = Modifier.height(
                    if (deliveryOption == "coupon") 86.dp else 4.dp
                )
            )

            if (selectedCoupon != null) {
                PriceItem(
                    label = "İndirim Miktarı:",
                    price = discount,
                    textColor = Color.Green
                )
            }

            PriceItem(label = "Teslimat Ücreti:", price = cargoPrice)

            PriceItem(label = "Ürünlerin Fiyatı:", price = totalPrice.toDouble())


            Spacer(
                modifier = Modifier.height(
                    if (deliveryOption == "coupon") 12.dp else 4.dp
                )
            )


            Divider(
                color = Color.White,
                thickness = 1.dp,
                modifier = Modifier.padding(vertical = 8.dp)
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Spacer(modifier = Modifier.height(6.dp))

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Toplam:",
                        color = Color.White,
                        modifier = Modifier.padding(top = 10.dp),
                        style = MaterialTheme.typography.h6
                    )

                    Text(
                        text = "$${"%.2f".format(finalPrice)}",
                        color = Color.White,
                        style = MaterialTheme.typography.h6.copy(
                            fontWeight = androidx.compose.ui.text.font.FontWeight.Bold
                        )
                    )
                }


                Button(
                    onClick = {

                        val discount = selectedCoupon?.discountAmount?.toDouble() ?: 0.0
                        val updatedOrderList = orderModelList.map { order ->
                            order.copy(
                              discount = discount.toInt()
                            )
                        }
                        if (updatedOrderList.get(0).deliveryOption=="cargo"){

                            paymentViewModel.saveOrders(updatedOrderList)
                            selectedAddress?.let {
                                navController.navigate("cargo_screen/${it.latitude}/${it.longitude}")
                            }
                        } else {
                            updatedOrderList?.let { list ->

                                paymentViewModel.saveOrders(updatedOrderList)
                                val orderNo = list.getOrNull(0)?.orderNo


                                if (!orderNo.isNullOrEmpty()) {
                                    navController.navigate("coupon_generator_screen/$orderNo")
                                } else {
                                }
                            }
                        }

                        selectedCoupon?.id?.let { couponId ->
                            paymentViewModel.markCouponAsInactive(couponId = couponId)
                        } ?: run {
                        }
                    },
                    modifier = Modifier
                        .padding(end = 20.dp, start = 4.dp, top = 10.dp)
                        .size(height = 44.dp, width = 250.dp)

                    ,
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = colorResource(id = com.finalproject.util.R.color.colorAccent)
                    )
                ) {
                    Text(
                        text = "Siparişi Onayla",
                        style = MaterialTheme.typography.button.copy(
                            fontSize = 18.sp,
                            fontWeight = androidx.compose.ui.text.font.FontWeight.W900
                        )
                    )
                }
            }
        }
    }
}

@Composable
fun PriceItem(
    label: String,
    price: Double,
    textColor: Color = Color.White
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 2.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            color = Color.White,
            style = MaterialTheme.typography.body1
        )
        Text(
            text = "$${"%.2f".format(price)}",
            color = textColor,
            style = MaterialTheme.typography.body1
        )
    }
}
