    package com.finalproject.feature

    import androidx.compose.foundation.Image
    import androidx.compose.foundation.background
    import androidx.compose.foundation.layout.*
    import androidx.compose.foundation.shape.RoundedCornerShape
    import androidx.compose.material3.Button
    import androidx.compose.material3.ButtonDefaults
    import androidx.compose.material3.Text
    import androidx.compose.runtime.Composable
    import androidx.compose.runtime.remember
    import androidx.compose.ui.Alignment
    import androidx.compose.ui.Modifier
    import androidx.compose.ui.draw.scale
    import androidx.compose.ui.graphics.Color
    import androidx.compose.ui.graphics.ImageBitmap
    import androidx.compose.ui.graphics.ImageShader
    import androidx.compose.ui.graphics.ShaderBrush
    import androidx.compose.ui.graphics.TileMode
    import androidx.compose.ui.layout.ContentScale
    import androidx.compose.ui.res.colorResource
    import androidx.compose.ui.res.imageResource
    import androidx.compose.ui.res.painterResource
    import androidx.compose.ui.text.font.Font
    import androidx.compose.ui.text.font.FontFamily
    import androidx.compose.ui.text.font.FontWeight
    import androidx.compose.ui.unit.dp
    import androidx.compose.ui.unit.sp
    import androidx.navigation.NavController

    @Composable
    fun WelcomeScreen(navController: NavController) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(colorResource(id = com.finalproject.util.R.color.prime))
        ) {
            Image(
                painter = painterResource(id = R.drawable.img_welcome),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )

                Column(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(bottom = 82.dp)
                        .padding(horizontal = 26.dp)
                ) {
                    Button(
                        onClick = { navController.navigate("signin_screen") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(54.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = colorResource(id = com.finalproject.util.R.color.prime)
                        ),
                        shape = RoundedCornerShape(50.dp)
                    ) {
                        Text(
                            text = "Giriş Yap",
                            fontSize = 24.sp,
                            fontWeight = FontWeight.W900,
                            color = colorResource(id = com.finalproject.util.R.color.colorAccent)
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Button(
                        onClick = { navController.navigate("signup_screen") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(54.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = colorResource(id = com.finalproject.util.R.color.prime)
                        ),
                        shape = RoundedCornerShape(50.dp)
                    ) {
                        Text(
                            text = "Kayıt Ol",
                            fontSize = 22.sp,
                            fontWeight = FontWeight.W900,
                            color = colorResource(id = com.finalproject.util.R.color.colorAccent)
                        )
                    }
                }
            }
        }