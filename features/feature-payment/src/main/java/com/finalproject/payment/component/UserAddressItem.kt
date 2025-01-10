package com.finalproject.payment.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.finalproject.domain.model.user.AddressModel
import com.finalproject.payment.R

@Composable
fun AddressComponent(address: AddressModel, onClick: () -> Unit = {}) {

    androidx.compose.material.Text(
        text = "Adres Bilgileri:",
        color = Color.White,
        modifier = Modifier.padding(start = 14.dp),
        fontWeight = FontWeight.Bold,
        style = androidx.compose.material.MaterialTheme.typography.h6
    )

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp)
            .background(colorResource(id = com.finalproject.util.R.color.darkAccent))
            .border(
                width = 1.dp,
                color = colorResource(id = com.finalproject.util.R.color.white),
                shape = RoundedCornerShape(8.dp)
            )
            .clickable { onClick() },
        verticalAlignment = Alignment.CenterVertically
    ) {
        val icon = when (address.addressType) {
            1 -> R.drawable.ic_adress_home
            2 -> R.drawable.ic_address_business
            3 -> R.drawable.ic_address_other
            else -> R.drawable.ic_adress_home
        }

        Image(
            painter = painterResource(id = icon),
            contentDescription = "Address Type",
            modifier = Modifier.size(40.dp).padding(start = 4.dp)
        )

        Spacer(modifier = Modifier.width(16.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = address.addressDescription,
                color = colorResource(id = com.finalproject.util.R.color.colorAccent),
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = address.userAddress,
                style = MaterialTheme.typography.bodyMedium,
                color = Color.White
            )
        }

        Spacer(modifier = Modifier.width(8.dp))

        Image(
            painter = painterResource(id = R.drawable.ic_change_item1),
            contentDescription = "Adres Seç",
            modifier = Modifier
                .size(32.dp)
                .padding(end = 4.dp)
                .clickable { onClick() }
        )
    }
}


