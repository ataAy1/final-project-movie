package com.finalproject.orders.component

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.finalproject.domain.model.payment.OrderModel
import com.finalproject.orders.R
import com.finalproject.util.Constants
import java.util.*

@Composable
fun OrderItem(orderList: List<OrderModel>, orderNo: String) {
    val isExpanded = remember { mutableStateOf(false) }
    val order = orderList.first()
    val orderDate: Date = order.date
    val formattedDate = DateUtils.formatToTurkishDate(orderDate)
    val price = orderList.sumOf { it.price * it.orderAmount }
    val context = LocalContext.current

    var totalOrderPrice =
        orderList.sumOf { it.totalPrice } + Constants.CARGO_DELIVERY_PRICE - orderList.get(0).discount

    if (orderList.get(0).deliveryOption == "coupon") {
        totalOrderPrice =  totalOrderPrice - Constants.CARGO_DELIVERY_PRICE
    }


    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 14.dp, start = 20.dp, end = 20.dp)
            .background(colorResource(id = com.finalproject.util.R.color.darkAccent)),
        shape = RoundedCornerShape(14.dp),
    ) {
        Column(
            modifier = Modifier
                .background(colorResource(id = com.finalproject.util.R.color.darkAccent))
                .border(
                    width = 1.dp,
                    color = colorResource(id = com.finalproject.util.R.color.mistyRose),
                    shape = RoundedCornerShape(14.dp)
                )

        ) {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp, vertical = 2.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                Image(
                    painter = if (order.deliveryOption == "cargo") {
                        painterResource(id = R.drawable.ic_cargo)
                    } else {
                        painterResource(id = R.drawable.ic_profile_coupon)
                    },
                    modifier = Modifier
                        .size(72.dp)
                        .padding(top = 22.dp),
                    contentDescription = "Kargo Yöntemi"
                )

                Text(
                    text = order.userName,
                    color = Color.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(start = 9.dp, top = 7.dp)
                )

                Column(
                    horizontalAlignment = Alignment.End
                ) {
                    Text(
                        text = formattedDate,
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(top = 6.dp, end = 10.dp),
                        fontSize = 12.sp
                    )
                    Text(
                        text = "\$${totalOrderPrice}",
                        color = Color.White,
                        fontSize = 16.sp,
                        modifier = Modifier.padding(top = 4.dp, end = 13.dp),
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Icon(
                imageVector = androidx.compose.material.icons.Icons.Default.ArrowDropDown,
                contentDescription = "Expand/Collapse",
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .size(40.dp)
                    .padding(bottom = 10.dp)
                    .clickable { isExpanded.value = !isExpanded.value },
                tint = colorResource(id = com.finalproject.util.R.color.colorAccent)
            )

            if (isExpanded.value) {
                Spacer(modifier = Modifier.height(8.dp))

                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(260.dp)
                        .padding(horizontal = 2.dp)
                ) {
                    item {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = "Fotoğraf",
                                color = Color.White,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier
                                    .weight(1f)
                                    .padding(start = 5.dp)
                            )
                            Text(
                                text = "Ürün",
                                color = Color.White,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.weight(1f)
                            )
                            Text(
                                text = "Adet",
                                color = Color.White,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.weight(1f)
                            )
                            Text(
                                text = "Fiyat",
                                color = Color.White,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.weight(1f)
                            )
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                    }


                    items(orderList) { item ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp)
                                .drawBehind {

                                    drawLine(
                                        color = Color.Gray,
                                        start = Offset(
                                            0f,
                                            size.height
                                        ),
                                        end = Offset(
                                            size.width,
                                            size.height
                                        ),
                                        strokeWidth = 6f
                                    )
                                }
                                .padding(8.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            AsyncImage(
                                model = "${Constants.IMAGE_BASE_URL}${item.image}",
                                contentDescription = "Fotoğraflar",
                                modifier = Modifier
                                    .size(70.dp)
                                    .padding(end = 24.dp, top = 10.dp)
                                    .weight(0.9f)
                            )

                            Text(
                                text = "${item.name}",
                                color = colorResource(id = com.finalproject.util.R.color.colorAccent),
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier
                                    .weight(1f)
                                    .padding(end = 16.dp),
                            )


                            Box(
                                modifier = Modifier
                                    .weight(1f)
                            )
                            {
                                Text(
                                    text = "${item.orderAmount}",
                                    color = colorResource(id = com.finalproject.util.R.color.darkYellow),
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier
                                        .size(height = 24.dp, width = 44.dp)
                                        .background(
                                            color = Color.DarkGray,
                                            shape = CircleShape
                                        )
                                        .padding(start = 16.dp),
                                )

                            }

                            Text(
                                text = "\$${item.price}",
                                color = Color.White,
                                modifier = Modifier
                                    .weight(1f)
                                    .padding(start = 14.dp),
                            )
                        }
                        Spacer(modifier = Modifier.height(2.dp))


                    }

                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 4.dp, vertical = 14.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Text(
                        text = "Sipariş No: ${orderNo.take(10)}",
                        color = Color.White,
                        modifier = Modifier
                            .padding(start = 8.dp),
                        fontWeight = FontWeight.Bold
                    )

                    val deliveryMessage = if (order.deliveryOption != "coupon") {
                        "\$15 kargo ücreti ve"
                    } else {
                        ""
                    }

                    Text(
                        text = "Toplam: \$${totalOrderPrice}",
                        color = Color.White,
                        modifier = Modifier
                            .padding(end = 18.dp)
                            .clickable {
                                Toast
                                    .makeText(
                                        context,
                                        "Bu siparişe: ${deliveryMessage} $${order.discount} kupon indirimi uygulanmıştır",
                                        Toast.LENGTH_LONG
                                    )
                                    .show()
                            },
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}