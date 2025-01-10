package com.finalproject.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.TabRowDefaults.Divider
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.skydoves.landscapist.coil.CoilImage
import com.skydoves.landscapist.components.rememberImageComponent
import com.skydoves.landscapist.placeholder.shimmer.Shimmer
import com.skydoves.landscapist.placeholder.shimmer.ShimmerPlugin

@Composable
fun ProfileScreen(navController: NavController, viewModel: ProfileViewModel = hiltViewModel()) {

    val profileState = viewModel.state.value

    LaunchedEffect(Unit) {
        viewModel.getUserSettings()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = com.finalproject.util.R.color.darkAccent)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TopBar()
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(240.dp)
                .background(
                    brush = Brush.linearGradient(
                        colors = listOf(
                            Color(0xFF0E3158),
                            Color(0xFF262233),
                        )
                    )
                ),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Bottom
        ) {

            CoilImage(
                imageModel = { profileState.userSettings?.profilePhotoUrl },
                modifier = Modifier
                    .size(140.dp)
                    .padding(top = 20.dp)
                    .clip(CircleShape)
                    .border(1.dp, Color.White, CircleShape),
                loading = {
                    Box(modifier = Modifier.matchParentSize()) {
                        CircularProgressIndicator(
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }
                },
                failure = {
                    Text(text = "Image request failed.")
                }
            )

            Spacer(modifier = Modifier.height(14.dp))

            Text(
                text = profileState.userSettings?.username ?: "",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(top = 12.dp, bottom = 10.dp),
                color = colorResource(id = com.finalproject.util.R.color.colorAccent)
            )

            Text(
                text = profileState.userSettings?.userMail ?: "",
                fontSize = 16.sp,
                modifier = Modifier.padding(bottom = 10.dp),
                color = Color.White
            )

        }



        Spacer(modifier = Modifier.height(4.dp))


        MenuOption(
            icon = R.drawable.ic_profile_order,
            title = "Siparişlerim",
            onClick = { navController.navigate("order_screen") }
        )
        MenuOption(
            icon = R.drawable.ic_profile_adress,
            title = "Kayıtlı Adreslerim",
            onClick = { navController.navigate("address_screen") }
        )
        MenuOption(
            icon = R.drawable.ic_profile_card,
            title = "Ödeme Yöntemlerim",
            onClick = { navController.navigate("cards_screen") }
        )
        MenuOption(
            icon = R.drawable.ic_profile_coupon,
            title = "Kuponlarım",
            onClick = { navController.navigate("coupon_screen") }
        )
        MenuOption(
            icon = R.drawable.ic_profile_settings,
            title = "Hesap Ayarları",
            onClick = { navController.navigate("settings_screen") }
        )
        MenuOption(
            icon = R.drawable.ic_profile_logout,
            title = "Çıkış Yap",
            onClick = {
                viewModel.signOut(navController=navController)

            }
        )

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(top = 20.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Info,
                contentDescription = "Version Info",
                tint = colorResource(id = com.finalproject.util.R.color.colorAccent),
                modifier = Modifier.size(18.dp)
            )


            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = "Versiyon v1.0.0",
                fontSize = 16.sp,
                color = Color.White
            )
        }


    }
}

@Composable
fun MenuOption(
    icon: Int,
    title: String,
    onClick: () -> Unit
) {
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 22.dp, end = 32.dp, top = 14.dp, bottom = 10.dp)
                .height(52.dp)
                .background(colorResource(id = com.finalproject.util.R.color.darkAccent))
                .clickable(onClick = onClick),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = icon),
                contentDescription = null,
                modifier = Modifier.size(38.dp)
            )
            Spacer(modifier = Modifier.width(24.dp))
            Text(
                text = title,
                color = Color.White,
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.weight(1f)
            )
            Image(
                painter = painterResource(id = R.drawable.ic_arrow_forward),
                contentDescription = null,
                modifier = Modifier.size(32.dp)
            )
        }

        Divider(
            color = Color.Gray,
            thickness = 1.dp,
            modifier = Modifier.padding(
                start = 8.dp,
                end = 8.dp
            )
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar() {
    TopAppBar(
        title = {
            androidx.compose.material.Text(
                text = "Profil Ayarları",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 24.dp),
                fontSize = 24.sp,
                color = colorResource(id = com.finalproject.util.R.color.white),
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
        },

        colors = TopAppBarDefaults.mediumTopAppBarColors(
            colorResource(id = com.finalproject.util.R.color.prime)
        ),
        modifier = Modifier.height(62.dp)
    )
}

