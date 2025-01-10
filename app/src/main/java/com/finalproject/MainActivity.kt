package com.finalproject

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.finalproject.navigation.SetupNavGraph
import com.finalproject.ui.theme.FinalProjectTheme
import com.finalproject.ui.theme.navigation.DropletButtonModel
import com.finalproject.ui.theme.navigation.NavigationBarWithAnimation
import dagger.hilt.android.AndroidEntryPoint
import androidx.compose.runtime.LaunchedEffect

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @OptIn(ExperimentalMaterialApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            FinalProjectTheme {
                val navController = rememberNavController()
                val selectedIndex = remember { mutableStateOf(0) }

                val dropletButtons = listOf(
                    DropletButtonModel(
                        icon = R.drawable.ic_home_bar,
                        contentDescription = "Home",
                        iconColor = R.color.blue,
                        dropletColor = com.finalproject.util.R.color.buttonColor,
                        size = 26.dp
                    ),
                    DropletButtonModel(
                        icon = R.drawable.heart,
                        contentDescription = "Fav",
                        iconColor = R.color.white,
                        dropletColor = com.finalproject.util.R.color.buttonColor,
                        size = 26.dp
                    ),
                    DropletButtonModel(
                        icon = R.drawable.cart,
                        contentDescription = "Basket",
                        iconColor = R.color.blue,
                        dropletColor = com.finalproject.util.R.color.buttonColor,
                        size = 26.dp
                    ),
                    DropletButtonModel(
                        icon = R.drawable.ic_profile,
                        contentDescription = "Profile",
                        iconColor = R.color.blue,
                        dropletColor = com.finalproject.util.R.color.buttonColor,
                        size = 22.dp
                    ),
                )

                val context = LocalContext.current

                val bottomNavRoutes = listOf(
                    Screen.Home.route,
                    "favorites_screen",
                    Screen.Basket.route,
                    "profile_screen"
                )

                val currentBackStackEntry = navController.currentBackStackEntryAsState()
                val currentRoute = currentBackStackEntry.value?.destination?.route
                val normalizedRoute = currentRoute?.substringBefore("/")


                LaunchedEffect(currentRoute) {
                    when (normalizedRoute) {
                        Screen.Home.route -> selectedIndex.value = 0
                        "favorites_screen" -> selectedIndex.value = 1
                        Screen.Basket.route -> selectedIndex.value = 2
                        "profile_screen" -> selectedIndex.value = 3
                    }
                }

                androidx.compose.foundation.layout.Box(modifier = Modifier.fillMaxSize()) {
                    SetupNavGraph(navController = navController)

                    if (normalizedRoute in bottomNavRoutes) {

                        NavigationBarWithAnimation(
                            modifier = Modifier
                                .align(Alignment.BottomCenter),
                            selectedIndex = selectedIndex.value,
                            dropletButtons = dropletButtons,
                            onItemSelected = { newIndex ->
                                selectedIndex.value = newIndex
                                when (newIndex) {
                                    0 -> navController.navigate(Screen.Home.route)
                                    1 -> navController.navigate("favorites_screen")
                                    2 -> navController.navigate(Screen.Basket.route)
                                    3 -> navController.navigate("profile_screen")
                                }
                            },
                            context = context
                        )
                    }
                }
            }
        }
    }
}


