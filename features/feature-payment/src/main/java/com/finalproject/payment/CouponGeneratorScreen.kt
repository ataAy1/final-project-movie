package com.finalproject.payment

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.imageResource
import androidx.navigation.NavController
import com.aritra.scratcheffect.ScratchEffect
import androidx.compose.ui.graphics.Path
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.finalproject.domain.model.payment.CouponModel
import java.util.Date
import java.util.UUID

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CouponGeneratorScreen(
    navController: NavController,
    orderNo: String?,
    viewModel: PaymentViewModel = hiltViewModel(),
) {
    val context = LocalContext.current
    val overlayImageBitmap = ImageBitmap.imageResource(context.resources, R.drawable.coupon_img)
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.coupon_generator))
    val progress by animateLottieCompositionAsState(
        composition,
        iterations = LottieConstants.IterateForever
    )

    val path = remember { Path() }
    var movedOffset by remember { mutableStateOf<Offset?>(null) }
    var isScratchingStarted by remember { mutableStateOf(false) }
    var isCouponSaved by remember { mutableStateOf(false) }
    var showDialog by remember { mutableStateOf(false) }

    val couponId = remember { UUID.randomUUID().toString() }



    LaunchedEffect(isScratchingStarted) {
        if (isScratchingStarted) {
            kotlinx.coroutines.delay(7500)

            val currentDate = Date()
            val couponModel = CouponModel(
                orderNo = orderNo
                    ?: "default-order-no",
                date = currentDate,
                couponCode = couponId
            )
            viewModel.saveCoupon(coupon = couponModel)

            showDialog = true
        }
    }

    if (showDialog) {
        androidx.compose.material.AlertDialog(
            onDismissRequest = {  },
            title = {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    androidx.compose.material.Text(
                        text = "Kodunuz Hazır !",
                        style = MaterialTheme.typography.h2.copy(
                            fontSize = 24.sp,
                            fontWeight = FontWeight.W900,
                            color = Color.White
                        )
                    )
                }
            },
            text = {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(240.dp),
                    contentAlignment = Alignment.Center
                ) {
                    LottieAnimation(
                        composition = composition,
                        iterations = LottieConstants.IterateForever,
                        modifier = Modifier.size(240.dp)
                    )
                }
            },
            confirmButton = {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    androidx.compose.material.Button(
                        onClick = {
                            navController.navigate("profile_screen") {
                                popUpTo("cargo_screen/{lat}/{lon}") { inclusive = true }
                                launchSingleTop = true
                            }
                        },
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = colorResource(id = com.finalproject.util.R.color.prime2),
                            contentColor = colorResource(id = com.finalproject.util.R.color.colorAccent)
                        ),
                        modifier = Modifier
                            .width(200.dp)
                            .height(40.dp)
                    ) {
                        androidx.compose.material.Text(
                            text = "Kuponlarıma Git",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.W900,
                            color = colorResource(id = com.finalproject.util.R.color.colorAccent)
                        )
                    }
                }

            },

            modifier = Modifier.background(Color.Black),
            backgroundColor = Color.Black
        )
    }

    ScratchEffect(
        overlayImage = overlayImageBitmap,
        movedOffset = movedOffset,
        onMovedOffset = { x, y ->
            if (!isScratchingStarted) {
                isScratchingStarted = true
            }
            movedOffset = Offset(x, y)
        },
        currentPath = path,
        currentPathThickness = 50f,
        background = {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = colorResource(id = com.finalproject.util.R.color.prime2))
            ) {

                Text(
                    couponId,
                    color = colorResource(id = com.finalproject.util.R.color.colorAccent),
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }
    )
}
