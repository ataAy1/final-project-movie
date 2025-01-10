package com.finalproject.coupon.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.finalproject.coupon.CouponScreen


fun NavGraphBuilder.couponNavigation(navController: NavHostController) {
    composable("coupon_screen") {
        CouponScreen(navController = navController)
    }
}

