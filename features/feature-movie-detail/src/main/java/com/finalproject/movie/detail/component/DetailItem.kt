package com.finalproject.movie.detail.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun DetailItem(
    label: String,
    value: String,
    textColor: Color = Color.White,
    fontWeight: FontWeight = FontWeight.W900,
    image: Painter
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 120.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Image(
            painter = image,
            contentDescription = "Detail image",
            modifier = Modifier
                .size(38.dp)
                .padding(start = 14.dp)
        )

        Text(
            text = value,
            fontSize = 16.sp,
            modifier = Modifier.padding(start = 8.dp),
            fontWeight = fontWeight,
            color = textColor
        )
    }
}
