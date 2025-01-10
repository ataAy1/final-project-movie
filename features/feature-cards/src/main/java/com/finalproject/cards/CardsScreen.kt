package com.finalproject.cards

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.finalproject.domain.model.user.CardsModel
import com.finalproject.util.LoadingAnimation

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CardsScreen(
    navController: NavController,
    viewModel: CardsViewModel = hiltViewModel()
) {
    val cardsState by viewModel.cardsState.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.fetchCards()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        TopAppBar(
            title = {
                androidx.compose.material.Text(
                    text = "Kartlarım",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(end = 54.dp),
                    fontSize = 22.sp,
                    color = colorResource(id = com.finalproject.util.R.color.white),
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )
            },
            navigationIcon = {
                IconButton(onClick = { navController.navigateUp() }) {
                    Image(
                        painterResource(id = R.drawable.ic_arrow),
                        modifier = Modifier
                            .size(40.dp)
                            .padding(top = 4.dp),
                        contentDescription = "Back",
                    )
                }
            },
            colors = TopAppBarDefaults.mediumTopAppBarColors(
                colorResource(id = com.finalproject.util.R.color.prime)
            ),
        )

        when (val state = cardsState) {
            is CardsState.Loading -> Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                LoadingAnimation(
                    circleSize = 25.dp,
                    circleColor = colorResource(id = com.finalproject.util.R.color.colorAccent),
                    spaceBetween = 10.dp,
                    travelDistance = 20.dp
                )
            }

            is CardsState.Error -> Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = state.message,
                    color = Color.Red,
                    fontSize = 16.sp
                )
            }

            is CardsState.Success -> {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(colorResource(id = com.finalproject.util.R.color.prime2))
                ) {
                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                            .padding(horizontal = 16.dp, vertical = 8.dp)
                    ) {
                        items(state.cards.size) { index ->
                            CardItem(card = state.cards[index])
                        }
                    }

                    Button(
                        onClick = { navController.navigate("add_card_screen") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 46.dp, start = 40.dp, end = 40.dp),
                        shape = RoundedCornerShape(46.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = colorResource(id = com.finalproject.util.R.color.prime)
                        )
                    ) {
                        androidx.compose.material.Text(
                            text = AnnotatedString("Kart Ekleme"),
                            color = colorResource(id = com.finalproject.util.R.color.colorAccent),
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }

            is CardsState.AddSuccess -> {


            }

            CardsState.Saving -> {

                Toast.makeText(context, "Başarılı Kayıt", Toast.LENGTH_SHORT).show()

            }
        }
    }
}


@Composable
fun CardItem(card: CardsModel) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .background(colorResource(id = com.finalproject.util.R.color.darkAccent))
            .border(
                width = 2.dp,
                color = colorResource(id = com.finalproject.util.R.color.brown),
                shape = RoundedCornerShape(8.dp)
            )
            .padding(4.dp, top = 5.dp)
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
                    text = card.cardName,
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
                IconButton(onClick = {}) {
                    Image(
                        painterResource(id = R.drawable.ic_delete3),
                        contentDescription = "Delete",
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
        }
    }
}


