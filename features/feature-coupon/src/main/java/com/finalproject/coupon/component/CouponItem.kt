package com.finalproject.coupon.component

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.TabRowDefaults.Divider
import androidx.compose.material3.Card
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.finalproject.coupon.R
import com.finalproject.domain.model.payment.CouponModel

@Composable
fun CouponItem(coupon: CouponModel) {
    val formattedDate = DateUtils.formatToTurkishDateTime(coupon.date)
    val clipboardManager = LocalClipboardManager.current
    val context = LocalContext.current

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, colorResource(id = com.finalproject.util.R.color.darkGrey))
            .background(colorResource(id = com.finalproject.util.R.color.darkAccent))
            .padding(12.dp, bottom = 0.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(colorResource(id = com.finalproject.util.R.color.darkAccent))
                .padding(6.dp)
        ) {

            Text(
                text = "Kupon Kodunuz:",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 2.dp),
                textAlign = TextAlign.Center,
                style = androidx.compose.ui.text.TextStyle(
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = colorResource(id = com.finalproject.util.R.color.darkYellow)
                )
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "${coupon.couponCode}",
                    modifier = Modifier
                        .background(colorResource(id = com.finalproject.util.R.color.prime))
                        .padding(start = 4.dp)
                        .weight(1f),
                    textAlign = TextAlign.Center,
                    style = androidx.compose.ui.text.TextStyle(
                        fontWeight = FontWeight.Medium,
                        fontSize = 15.sp,
                        color = colorResource(id = com.finalproject.util.R.color.colorAccent)
                    )
                )

                IconButton(
                    onClick = {
                        clipboardManager.setText(AnnotatedString(coupon.couponCode))
                        Toast.makeText(context, "Kodunuz Kopyalandı !", Toast.LENGTH_SHORT).show()

                    },
                    modifier = Modifier
                        .size(40.dp)
                        .padding(bottom = 2.dp, end = 6.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_copy),
                        contentDescription = "Copy Coupon Code",
                    )
                }
            }
            Divider(
                color = colorResource(id = com.finalproject.util.R.color.white),
                thickness = 1.dp,
                modifier = Modifier.padding(top = 4.dp)
            )
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "$formattedDate\nSipariş no: ${coupon.orderNo}",
                    modifier = Modifier
                        .weight(1f)
                        .padding(top = 4.dp),
                    textAlign = TextAlign.Center,
                    style = TextStyle(
                        fontWeight = FontWeight.Bold,
                        fontSize = 13.5.sp,
                        color = colorResource(id = com.finalproject.util.R.color.white)
                    )
                )
            }
        }
    }
}
