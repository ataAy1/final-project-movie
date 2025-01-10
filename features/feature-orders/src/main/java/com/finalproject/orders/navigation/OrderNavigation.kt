package com.finalproject.orders.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.finalproject.domain.model.user.UserModel
import com.finalproject.orders.OrderScreen
import com.finalproject.orders.R
import java.text.SimpleDateFormat
import java.util.*

fun NavGraphBuilder.ordersNavigation(navController: NavHostController) {
    composable("order_screen") {
        OrderScreen(navController = navController)
    }
}

