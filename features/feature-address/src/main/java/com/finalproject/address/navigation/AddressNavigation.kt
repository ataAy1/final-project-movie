package com.finalproject.address.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.finalproject.address.AddAddressScreen
import com.finalproject.address.AddressScreen

fun NavGraphBuilder.addressNavigation(navController: NavHostController) {
    composable("address_screen") {
        AddressScreen(navController = navController)
    }
    composable("add_address_screen") {
        AddAddressScreen(navController = navController)
    }
}
