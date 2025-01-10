package com.finalproject.payment.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.finalproject.domain.model.user.CardsModel
import com.finalproject.payment.R


@Composable
fun CardItem(card: CardsModel, onClick: () -> Unit) {

    androidx.compose.material.Text(
        text = "Kart Bilgileri:",
        color = Color.White,
        modifier = Modifier.padding(top = 4.dp, start = 14.dp),
        fontWeight = FontWeight.Bold,
        style = androidx.compose.material.MaterialTheme.typography.h6
    )

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp)
            .border(
                width = 1.dp,
                color = colorResource(id = com.finalproject.util.R.color.white),
                shape = RoundedCornerShape(8.dp)
            )
            .clickable { onClick() }
            .background(
                colorResource(id = com.finalproject.util.R.color.darkAccent)
            )
    ) {


        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = colorResource(id = com.finalproject.util.R.color.darkAccent),
                    shape = RoundedCornerShape(8.dp)
                ),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start,
                modifier = Modifier.weight(1f)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_master),
                    contentDescription = "MasterCard",
                    modifier = Modifier
                        .size(54.dp)
                        .padding(start = 4.dp, top = 4.dp, bottom = 4.dp)
                )
            }

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.weight(2f)
            ) {
                Text(
                    text = card.cardHolderName,
                    fontSize = 18.sp,
                    color = colorResource(id = com.finalproject.util.R.color.colorAccent),
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 4.dp)
                )

                Text(
                    text = "**** **** **** ${card.cardNumber.takeLast(4)}",
                    fontSize = 16.sp,
                    color = Color.White
                )
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End,
                modifier = Modifier.weight(1f)
            ) {
                IconButton(onClick = { onClick() }) {
                    Image(
                        painterResource(id = R.drawable.ic_change_item1),
                        contentDescription = "Delete",
                        modifier = Modifier
                            .size(28.dp)

                        )
                }
            }
        }
    }
}


