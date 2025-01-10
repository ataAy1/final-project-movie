import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.finalproject.domain.model.payment.OrderModel
import com.finalproject.payment.CouponGeneratorScreen
import com.finalproject.payment.DeliveryOptionScreen
import com.finalproject.payment.PaymentScreen

fun NavGraphBuilder.paymentNavigation(navController: NavHostController) {
    composable(
        "delivery_option_screen/{orderJson}",
        arguments = listOf(
            navArgument("orderJson") {
                type = NavType.StringType
            }
        )
    ) { backStackEntry ->
        val orderJson = backStackEntry.arguments?.getString("orderJson")

        val orderList = if (orderJson != null) {
            val listType = object : TypeToken<List<OrderModel>>() {}.type
            Gson().fromJson<List<OrderModel>>(orderJson, listType)
        } else {
            emptyList<OrderModel>()
        }
        DeliveryOptionScreen(orderModelList = orderList, navController = navController)
    }



    composable("payment_screen/{orderJson}") { backStackEntry ->
        val orderJson = backStackEntry.arguments?.getString("orderJson")

        val orderList = if (orderJson != null) {
            val listType = object : TypeToken<List<OrderModel>>() {}.type
            Gson().fromJson<List<OrderModel>>(orderJson, listType)
        } else {
            emptyList<OrderModel>()
        }

        PaymentScreen(orderModelList = orderList, navController = navController)
    }

    composable(
        route = "cargo_screen/{lat}/{lon}",
        arguments = listOf(
            navArgument("lat") { type = NavType.StringType },
            navArgument("lon") { type = NavType.StringType }
        )
    ) { backStackEntry ->
        val lat = backStackEntry.arguments?.getString("lat")?.toDouble() ?: 0.0
        val lon = backStackEntry.arguments?.getString("lon")?.toDouble() ?: 0.0
        CargoScreen(lat = lat, lon = lon, navController)
    }

    composable(
        route = "coupon_generator_screen/{orderNo}",
        arguments = listOf(navArgument("orderNo") { type = NavType.StringType })
    ) { backStackEntry ->
        val orderNo = backStackEntry.arguments?.getString("orderNo")
        CouponGeneratorScreen(navController = navController, orderNo = orderNo)
    }

}
