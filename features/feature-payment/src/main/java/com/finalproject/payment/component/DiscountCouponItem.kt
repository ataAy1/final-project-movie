package com.finalproject.payment.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.finalproject.domain.model.payment.DiscountCouponModel
import java.text.SimpleDateFormat
import java.util.*


@Composable
fun DiscountCouponItem(
    coupon: DiscountCouponModel,
    onCouponSelected: (DiscountCouponModel) -> Unit,
    selectedCoupon: DiscountCouponModel?
) {
    var isDetailVisible by remember { mutableStateOf(true) }

    val backgroundColor = if (selectedCoupon == coupon) {
        colorResource(id = com.finalproject.util.R.color.green)
    } else {
        colorResource(id = com.finalproject.util.R.color.white)
    }

    Column(
        modifier = Modifier
            .padding(start = 16.dp, end = 16.dp, top = 6.dp)
            .border(1.2.dp, backgroundColor, shape = MaterialTheme.shapes.medium)
            .background(colorResource(id = com.finalproject.util.R.color.darkAccent))
            .clickable { isDetailVisible = !isDetailVisible },
        horizontalAlignment = Alignment.Start
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Button(
                onClick = { onCouponSelected(coupon) },
                modifier = Modifier
                    .padding(4.dp)
                    .height(36.dp)
                    .width(124.dp),
                colors = ButtonDefaults.buttonColors(backgroundColor = colorResource(id = com.finalproject.util.R.color.prime2))
            ) {
                Text(
                    text = if (selectedCoupon == coupon) "Kaldır" else "Uygula",
                    color = if (selectedCoupon == coupon) colorResource(id = com.finalproject.util.R.color.darkRed)
                    else colorResource(id = com.finalproject.util.R.color.colorAccent),
                    fontWeight = FontWeight.Bold
                )
            }

            Text(
                text = coupon.code,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                modifier = Modifier.padding(8.dp),
                fontSize = 16.sp
            )

            IconButton(onClick = { isDetailVisible = !isDetailVisible }) {
                androidx.compose.material3.Icon(
                    imageVector = androidx.compose.material.icons.Icons.Default.ArrowDropDown,
                    contentDescription = "Expand/Collapse",
                    modifier = Modifier
                        .size(40.dp)
                        .padding(bottom = 2.dp),
                    tint = colorResource(id = com.finalproject.util.R.color.colorAccent)
                )
            }
        }

        if (isDetailVisible) {
            Column(
                modifier = Modifier
                    .padding(start = 6.dp)
                    .fillMaxWidth()
            ) {

                Text(
                    text = "${coupon.details}",
                    color = Color.Black,
                    fontWeight = FontWeight.W900,
                    fontSize = 14.sp,
                    style = MaterialTheme.typography.body2,
                    modifier = Modifier
                        .padding(start = 8.dp, end = 14.dp)
                        .background(
                            color = colorResource(id = com.finalproject.util.R.color.skyBlue),
                            shape = RoundedCornerShape(2.dp)
                        )
                        .padding(8.dp) // Inner padding for the text within the background
                )


                Text(
                    text = "Son Tarih: ${
                        SimpleDateFormat(
                            "d MMMM yyyy",
                            Locale("tr", "TR")
                        ).format(coupon.expirationDate)
                    }",
                    color = colorResource(id = com.finalproject.util.R.color.white),
                    modifier = Modifier.padding(start = 100.dp, bottom = 4.dp, top = 8.dp),
                )


            }
        }
    }
}
