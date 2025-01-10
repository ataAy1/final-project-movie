package com.finalproject.payment.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import com.finalproject.domain.model.user.CardsModel
import com.finalproject.payment.R

@Composable
fun CardSelectionDialog(
    cards: List<CardsModel>,
    selectedCard: CardsModel?,
    onCardSelected: (CardsModel) -> Unit,
    onDismissRequest: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismissRequest,
        title = {
            Text(
                text = "Lütfen Kart Seçiniz ",
                fontSize = 18.sp,
                modifier = Modifier.padding(start = 50.dp),
                style = MaterialTheme.typography.titleSmall,
                color = Color.White
            )
        },
        text = {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(2.dp),
                modifier = Modifier.padding(4.dp)
            ) {
                items(cards) { card ->
                    val isSelected = card == selectedCard
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                onCardSelected(card)
                            }
                            .padding(vertical = 8.dp)
                            .border(2.dp, Color.Gray, RoundedCornerShape(8.dp)),
                        shape = RoundedCornerShape(8.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = if (isSelected) colorResource(id = com.finalproject.util.R.color.sapphire) else
                                colorResource(id = com.finalproject.util.R.color.darkAccent)
                        )
                    ) {
                        Row(
                            modifier = Modifier
                                .padding(8.dp)
                                .fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Image(
                                painter = rememberImagePainter(R.drawable.ic_master),
                                contentDescription = null,
                                modifier = Modifier
                                    .padding(end = 20.dp)
                                    .size(44.dp)
                                    .clip(RoundedCornerShape(8.dp))
                            )
                            Spacer(modifier = Modifier.width(12.dp))
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = card.cardHolderName,
                                    style = MaterialTheme.typography.bodyLarge,
                                    color = Color.White
                                )
                                Text(
                                    text = "**** **** **** ${card.cardNumber.takeLast(4)}",
                                    style = MaterialTheme.typography.bodyLarge,
                                    fontWeight = FontWeight.Bold,
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
