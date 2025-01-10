package com.finalproject.payment.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.finalproject.domain.model.user.AddressModel
import com.finalproject.payment.R

@Composable
fun AddressSelectionDialog(
    addresses: List<AddressModel>,
    selectedAddress: AddressModel?,
    onAddressSelected: (AddressModel) -> Unit,
    onDismissRequest: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismissRequest,
        title = {
            Text(
                text = "Lütfen Adres Seçiniz",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.White,
                fontSize = 18.sp,
                modifier = Modifier.padding(start = 50.dp),

                )
        },
        text = {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.padding(4.dp)
            ) {
                items(addresses) { address ->
                    val isSelected = address == selectedAddress
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { onAddressSelected(address) }
                            .padding(vertical = 8.dp)
                            .border(2.dp, Color.Gray, RoundedCornerShape(8.dp)),
                        shape = RoundedCornerShape(8.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = if (isSelected) colorResource(id = com.finalproject.util.R.color.darkGreen) else
                                colorResource(id = com.finalproject.util.R.color.darkAccent)
                        )
                    ) {
                        Row(
                            modifier = Modifier
                                .padding(4.dp)
                                .fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Image(
                                painter = when (address.addressType) {
                                    1 -> painterResource(id = R.drawable.ic_adress_home)
                                   2 -> painterResource(id = R.drawable.ic_address_business)
                                    3 -> painterResource(id = R.drawable.ic_address_other)
                                    else -> painterResource(id = R.drawable.ic_adress_home)
                                },
                                contentDescription = "Address Icon",
                                modifier = Modifier
                                    .padding(start = 6.dp)
                                    .size(32.dp)
                                    .clip(RoundedCornerShape(8.dp))
                            )

                            Spacer(modifier = Modifier.width(12.dp))
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = address.addressDescription,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White
                                )
                                Text(
                                    text = address.userAddress,
                                    color = colorResource(id = com.finalproject.util.R.color.colorAccent)
                                )
                            }
                        }
                    }
                }
            }
        },
        confirmButton = {
            Button(
                onClick = onDismissRequest,
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorResource(id = com.finalproject.util.R.color.colorAccent)
                )
            ) {
                Text("Kapat", color = Color.Black)
            }
        },
        modifier = Modifier.background(Color.Black),
        containerColor = Color.Black
    )
}
