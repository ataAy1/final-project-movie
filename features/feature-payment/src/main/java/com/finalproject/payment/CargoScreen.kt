import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Handler
import android.os.Looper
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.mapbox.geojson.Point
import com.mapbox.maps.MapView
import com.mapbox.maps.plugin.annotation.generated.PointAnnotation
import com.mapbox.maps.plugin.annotation.generated.PointAnnotationOptions
import com.mapbox.maps.plugin.annotation.generated.createPointAnnotationManager
import com.finalproject.payment.R
import com.mapbox.common.MapboxOptions
import com.mapbox.maps.dsl.cameraOptions
import com.mapbox.maps.plugin.animation.MapAnimationOptions
import com.mapbox.maps.plugin.animation.camera
import com.mapbox.maps.plugin.annotation.annotations
import com.mapbox.maps.plugin.logo.generated.LogoSettings

@Composable
fun CargoScreen(lat: Double, lon: Double, navController: NavController) {
    val context = LocalContext.current
    val mapboxAccessToken = stringResource(id = R.string.mapbox_access_token)
    val mapView = remember { MapView(context) }
    var showDialog by remember { mutableStateOf(false) }

    MapboxOptions.accessToken = mapboxAccessToken

    Scaffold(
        modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth()
    ) { paddingValues ->

        AndroidView(
            factory = { mapView },
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(paddingValues)
        ) {


            setupMapInteraction(it, lat, lon)

            val coordinates = Point.fromLngLat(lon, lat)
            addMarkerToMap(context, it, coordinates)
        }

        LaunchedEffect(Unit) {
            kotlinx.coroutines.delay(20500)
            showDialog = true
        }

        if (showDialog) {
            showDialog(navController)
        }
    }
}

private fun setupMapInteraction(mapView: MapView, latitude: Double, longitude: Double) {
    val coordinates = Point.fromLngLat(longitude, latitude)
    updateMap(mapView, coordinates)
}

private fun updateMap(mapView: MapView, coordinates: Point) {
    val mapboxMap = mapView.getMapboxMap()
    val cameraAnimationsPlugin = mapView.camera

    val targetCameraPosition = cameraOptions {
        center(coordinates)
        zoom(15.5)
        pitch(45.0)
        bearing(-37.6)
    }

    val animationOptions = MapAnimationOptions.mapAnimationOptions {
        duration(18500)
    }

    cameraAnimationsPlugin.flyTo(targetCameraPosition, animationOptions)
}


@Composable
fun showDialog(navController: NavController) {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.orderr))
    Box(
    ) {
        AlertDialog(
            onDismissRequest = { },
            title = {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Siparişinizi Aldık !",
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
                    Button(
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
                        Text(
                            text = "Siparişlerime Git",
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
}


private fun addMarkerToMap(
    context: Context, mapView: MapView, coordinates: Point
) {
    val markerBitmap = BitmapFactory.decodeResource(context.resources, R.drawable.ic_cinema)
    val resizedBitmap = Bitmap.createScaledBitmap(markerBitmap, 140, 140, false)

    val annotationManager = mapView.annotations.createPointAnnotationManager()

    val pointAnnotationOptions = PointAnnotationOptions()
        .withPoint(coordinates)
        .withIconImage(resizedBitmap)

    annotationManager.create(pointAnnotationOptions)
}
