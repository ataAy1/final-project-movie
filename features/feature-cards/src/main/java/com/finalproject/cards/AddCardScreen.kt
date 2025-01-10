package com.finalproject.cards


import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.aritra.compose_cards.component.BackgroundCard
import com.aritra.compose_cards.component.CardNumberFilter
import com.aritra.compose_cards.component.InputTextField
import com.aritra.compose_cards.ui.CreditCard
import androidx.compose.runtime.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.navigation.NavHostController
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.finalproject.domain.model.user.CardsModel
import com.finalproject.util.LoadingAnimation


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddCardScreen(
    navController: NavHostController,
    viewModel: CardsViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    var cardName by remember { mutableStateOf("") }
    var cardNumber by remember { mutableStateOf("") }
    var cardHolderName by remember { mutableStateOf("") }
    var expiryDate by remember { mutableStateOf("") }
    var cardCVV by remember { mutableStateOf("") }
    var selectedColor by remember { mutableStateOf(com.aritra.compose_cards.R.drawable.violet_bg) }
    var nameError by remember { mutableStateOf("") }

    val savingState by viewModel.savingState.collectAsState()


    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_arrow),
                            contentDescription = "Back",
                        )
                    }
                },
                title = {
                    Text(
                        text = "Kart Ekleme",
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(end = 60.dp),
                        fontSize = 22.sp,
                        color = colorResource(id = com.finalproject.util.R.color.colorAccent),
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center
                    )
                },
                colors = TopAppBarDefaults.mediumTopAppBarColors(containerColor = colorResource(id = com.finalproject.util.R.color.prime)),
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .background(colorResource(id = com.finalproject.util.R.color.prime2))
                .fillMaxSize()
        ) {
            when (val state = savingState) {

                is CardsState.Saving -> {

                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .wrapContentSize()
                    ) {
                        LoadingAnimation(
                            circleSize = 35.dp,
                            circleColor = colorResource(id = com.finalproject.util.R.color.colorAccent),
                            spaceBetween = 10.dp,
                            travelDistance = 20.dp
                        )
                    }
                }
                is CardsState.AddSuccess -> {
                    if (state.isSuccess) {
                        Toast.makeText(context, "Başarılı Kayıt", Toast.LENGTH_SHORT).show()

                        LaunchedEffect(state) {
                            navController.popBackStack()

                            viewModel.resetState()
                        }
                    }
                }
                is CardsState.Error -> {

                    Toast.makeText(context,"Error",Toast.LENGTH_SHORT).show()
                }

                else -> {
                    CreditCard(
                        cardNumber = cardNumber,
                        holderName = cardHolderName,
                        expiryDate = expiryDate,
                        cardCVV = cardCVV,
                        selectedBackground = selectedColor
                    )


                    Spacer(modifier = Modifier.height(10.dp))

                    Row(
                        modifier = Modifier
                            .padding(16.dp)
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        BackgroundCard(drawableResId = com.aritra.compose_cards.R.drawable.bg4) {
                            selectedColor = com.aritra.compose_cards.R.drawable.bg4
                        }
                        BackgroundCard(drawableResId = com.aritra.compose_cards.R.drawable.bg3) {
                            selectedColor = com.aritra.compose_cards.R.drawable.bg3
                        }
                        BackgroundCard(drawableResId = com.aritra.compose_cards.R.drawable.red_bg) {
                            selectedColor = com.aritra.compose_cards.R.drawable.red_bg
                        }
                        BackgroundCard(drawableResId = com.aritra.compose_cards.R.drawable.circle_bg) {
                            selectedColor = com.aritra.compose_cards.R.drawable.circle_bg
                        }
                    }

                    Column(
                        modifier = Modifier
                            .padding(16.dp)
                            .fillMaxWidth()
                    ) {



                        OutlinedTextField(
                            value = cardNumber,
                            onValueChange = {
                                if (it.length <= 16) {
                                    cardNumber = it
                                }
                            },
                            label = {
                                androidx.compose.material3.Text(
                                    "Kart Numarası",
                                    style = TextStyle(
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.W900,
                                        color = colorResource(id = com.finalproject.util.R.color.colorAccent)
                                    )
                                )
                            },
                            modifier = Modifier.fillMaxWidth(),
                            textStyle = TextStyle(
                                fontSize = 16.sp,
                                color = Color.White
                            ),
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Number,
                                imeAction = ImeAction.Next
                            )
                        )



                        OutlinedTextField(
                            value = cardHolderName,
                            onValueChange = { cardHolderName = it.uppercase() },
                            label = {
                                androidx.compose.material3.Text(
                                    "Kart Sahibi",
                                    style = TextStyle(
                                        fontSize = 16.sp,
                                        color = colorResource(id = com.finalproject.util.R.color.colorAccent)
                                    )
                                )
                            },
                            modifier = Modifier.fillMaxWidth(),
                            textStyle = TextStyle(
                                fontSize = 20.sp,
                                color = Color.White
                            ),
                            visualTransformation = VisualTransformation { text ->
                                val uppercaseText = text.text.uppercase()
                                TransformedText(
                                    text = AnnotatedString(uppercaseText),
                                    offsetMapping = object : OffsetMapping {
                                        override fun originalToTransformed(offset: Int): Int = offset
                                        override fun transformedToOriginal(offset: Int): Int = offset
                                    }
                                )
                            }
                        )


                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 5.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            OutlinedTextField(
                                value = expiryDate,
                                onValueChange = { expiryDate = it },
                                label = {
                                    androidx.compose.material3.Text(
                                        "Son Kullanma Tarihi",
                                        style = TextStyle(
                                            fontSize = 14.sp,
                                            color = colorResource(id = com.finalproject.util.R.color.colorAccent)
                                        )
                                    )
                                },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .weight(0.6f),
                                textStyle = TextStyle(
                                    fontSize = 16.sp,
                                    color = Color.White
                                )
                            )

                            OutlinedTextField(
                                value = cardCVV,
                                onValueChange = { cardCVV = it },
                                label = {
                                    androidx.compose.material3.Text(
                                        "CVV",
                                        style = TextStyle(
                                            fontSize = 14.sp,
                                            color = colorResource(id = com.finalproject.util.R.color.colorAccent)
                                        )
                                    )
                                },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .weight(0.4f),
                                textStyle = TextStyle(
                                    fontSize = 16.sp,
                                    color = Color.White
                                )
                            )
                        }


                        OutlinedTextField(
                            value =cardName,
                            onValueChange = { cardName = it },

                            label = {
                                androidx.compose.material3.Text(
                                    "Kart Adı",
                                    style = TextStyle(
                                        fontSize = 14.sp,
                                        color = colorResource(id = com.finalproject.util.R.color.colorAccent)
                                    )
                                )
                            },
                            modifier = Modifier.fillMaxWidth(),
                            textStyle = TextStyle(
                                fontSize = 16.sp,
                                color = Color.White
                            )
                        )

                        Spacer(modifier = Modifier.weight(1f))

                        Button(
                            onClick = {
                                if (cardName.isNotEmpty() && cardNumber.isNotEmpty() && cardHolderName.isNotEmpty() && expiryDate.isNotEmpty() && cardCVV.isNotEmpty()) {
                                    val newCard = CardsModel(
                                        cardId = "",
                                        cardName = cardName,
                                        cardHolderName = cardHolderName,
                                        cardNumber = cardNumber,
                                        cardCVV = cardCVV,
                                        cardExpiryDate = expiryDate
                                    )
                                    viewModel.saveCard(newCard)
                                } else {
                                    nameError = "All fields must be filled!"
                                }
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 26.dp),
                            shape = RoundedCornerShape(16.dp),
                            colors = ButtonDefaults.buttonColors(backgroundColor = colorResource(id = com.finalproject.util.R.color.prime))
                        ) {
                            Text(
                                text = AnnotatedString("Kaydet"),
                                color = colorResource(id = com.finalproject.util.R.color.colorAccent),
                                fontSize = 24.sp,
                                modifier = Modifier.padding(bottom = 2.dp),
                                fontWeight = FontWeight.Bold,
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }
            }
        }
    }
}
