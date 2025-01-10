package com.finalproject.basket.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.Card
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import com.finalproject.basket.R
import com.finalproject.domain.model.payment.BasketItem
import com.finalproject.util.Constants.getImageUrl

@Composable
fun BasketItemCard(
    item: BasketItem,
    onIncreaseQuantity: () -> Unit,
    onDecreaseQuantity: () -> Unit
) {
    val backdropUrl = getImageUrl(item.image)

    Card(
        modifier = Modifier
            .padding(start = 18.dp, end = 14.dp, top = 12.dp)
            .background(colorResource(id = com.finalproject.util.R.color.darkAccent))
            .fillMaxWidth(),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(colorResource(id = com.finalproject.util.R.color.darkAccent))
                .padding(top = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = rememberImagePainter(backdropUrl),
                contentDescription = item.name,
                modifier = Modifier
                    .size(120.dp)
                    .padding(bottom = 10.dp)
            )

            Spacer(modifier = Modifier.width(2.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(text = item.name,
                    modifier = Modifier.padding(bottom = 40.dp, end = 4.dp),
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp,
                    style = MaterialTheme.typography.h6,
                    color = Color.White)

                Text(text = "\$${item.price * item.orderAmount}",
                    style = MaterialTheme.typography.body2,
                    modifier = Modifier.padding(bottom = 4.dp),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = colorResource(id = com.finalproject.util.R.color.darkYellow)
                )

            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
            ) {

                if (item.orderAmount == 1) {
                    IconButton(onClick = onDecreaseQuantity) {
                        Image(
                            painterResource(id = R.drawable.ic_delete_basket),
                            modifier = Modifier.size(32.dp),
                            contentDescription = "Increase Quantity"
                        )
                    }
                } else {
                    IconButton(onClick = onDecreaseQuantity) {
                        Image(
                            painterResource(id = R.drawable.ic_decrease),
                            modifier = Modifier.size(34.dp),
                            contentDescription = "Delete Item"
                        )
                    }
                }
                Text(
                    text = "${item.orderAmount}",
                    modifier = Modifier.background(
                        colorResource(id = com.finalproject.util.R.color.darkBlue)
                    ).size(width = 40.dp, height = 24.dp).padding(start = 14.dp)

                    ,
                    style = MaterialTheme.typography.body1,
                    color = Color.White
                )

                IconButton(onClick = onIncreaseQuantity) {
                    Image(
                        painterResource(id = R.drawable.ic_increase),
                        modifier = Modifier.size(34.dp),
                        contentDescription = "Decrease Quantity"
                    )
                }
            }
        }

    }
}