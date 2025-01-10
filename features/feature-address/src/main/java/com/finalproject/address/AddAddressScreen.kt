package com.finalproject.address


import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.location.Geocoder
import android.os.Looper
import android.util.Log
import android.view.animation.AnticipateOvershootInterpolator
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.navigation.NavHostController
import com.finalproject.domain.model.user.AddressModel
import com.finalproject.util.LoadingAnimation
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.mapbox.common.MapboxOptions
import com.mapbox.geojson.Point
import com.mapbox.maps.CameraOptions
import com.mapbox.maps.MapView
import com.mapbox.maps.Style
import com.mapbox.maps.dsl.cameraOptions
import com.mapbox.maps.plugin.animation.CameraAnimatorOptions.Companion.cameraAnimatorOptions
import com.mapbox.maps.plugin.animation.MapAnimationOptions
import com.mapbox.maps.plugin.animation.camera
import com.mapbox.maps.plugin.annotation.annotations
import com.mapbox.maps.plugin.annotation.generated.PointAnnotation
import com.mapbox.maps.plugin.annotation.generated.PointAnnotationManager
import com.mapbox.maps.plugin.annotation.generated.PointAnnotationOptions
import com.mapbox.maps.plugin.annotation.generated.createPointAnnotationManager
import com.mapbox.maps.plugin.gestures.addOnMapClickListener
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddAddressScreen(navController: NavHostController) {
    val context = LocalContext.current
    val mapboxAccessToken = stringResource(id = R.string.mapbox_access_token)
    val latitude = remember { mutableStateOf(0.0) }
    val longitude = remember { mutableStateOf(0.0) }

    val fullName = remember { mutableStateOf("") }
    val phoneNumber = remember { mutableStateOf("") }
    val apartmentNo = remember { mutableStateOf("") }
    val floorNo = remember { mutableStateOf("") }
    val homeNo = remember { mutableStateOf("") }
    val addressDescription = remember { mutableStateOf("") }
    val addressType = remember { mutableStateOf(0) }
    val addressLabel = remember { mutableStateOf("") }
    val address = remember { mutableStateOf("") }
    val mapView = remember { MapView(context) }
    val fusedLocationClient = remember { LocationServices.getFusedLocationProviderClient(context) }
    val permissionsGranted = remember { mutableStateOf(false) }
    val viewModel: AddressViewModel = hiltViewModel()
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current

    val latitudeUser = remember { mutableStateOf(40.99864374531845) }
    val longitudeUser = remember { mutableStateOf(29.040798919574684) }

    val requestPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { isGranted ->
            permissionsGranted.value = isGranted
        }
    )

    MapboxOptions.accessToken = mapboxAccessToken

    val state = viewModel.saveAddressState.collectAsState().value


    LaunchedEffect(Unit) {
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)
            == PackageManager.PERMISSION_GRANTED
        ) {
            permissionsGranted.value = true
            val annotationManager = mapView.annotations.createPointAnnotationManager()
            annotationManager.deleteAll()

            val istanbulPoint = Point.fromLngLat(longitudeUser.value,latitudeUser.value)
             updateMap(mapView, istanbulPoint)

        }
    }


    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    androidx.compose.material.Text(
                        text = "Adres Ekleme",
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(end = 54.dp),
                        fontSize = 22.sp,
                        color = colorResource(id = com.finalproject.util.R.color.colorAccent),
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Image(
                            painterResource(id = R.drawable.ic_arrow),
                            modifier = Modifier
                                .size(40.dp)
                                .padding(top = 4.dp),
                            contentDescription = "Back",
                        )
                    }
                },
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    colorResource(id = com.finalproject.util.R.color.prime)
                ),
                modifier = Modifier.padding(bottom = 6.dp)
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFF1F1D2B))
                .padding(innerPadding)
                .padding(2.dp)
        ) {
            if (permissionsGranted.value) {
                AndroidView(
                    factory = { mapView },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp)
                ) {
                    setupMapInteraction(it, context, latitude, longitude, address)
                }

                Spacer(modifier = Modifier.height(2.dp))

                when (state) {
                    is AddressState.Loading -> {
                        LoadingAnimation(
                            circleSize = 25.dp,
                            circleColor = colorResource(id = com.finalproject.util.R.color.colorAccent),
                            spaceBetween = 10.dp,
                            travelDistance = 20.dp
                        )
                    }

                    is AddressState.Saving -> {
                        if (state.isSaving) {
                            LoadingAnimation(
                                circleSize = 25.dp,
                                circleColor = colorResource(id = com.finalproject.util.R.color.colorAccent),
                                spaceBetween = 10.dp,
                                travelDistance = 20.dp
                            )
                        } else {
                            Toast.makeText(context, "Address saved successfully!", Toast.LENGTH_SHORT).show()
                        }
                    }

                    is AddressState.Error -> {
                        Toast.makeText(context, state.message, Toast.LENGTH_SHORT).show()
                    }

                    is AddressState.Success -> {
                        Toast.makeText(context, "Kayıt Gerçekleşti!", Toast.LENGTH_SHORT).show()
                    }

                    AddressState.Idle -> {
                        Text("Ready to add an address")
                    }
                }


                OutlinedTextField(
                    value = address.value,
                    onValueChange = {},
                    label = {
                        Text(
                            "Adres",
                            color = colorResource(id = com.finalproject.util.R.color.colorAccent)
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 14.dp, end = 14.dp, top = 6.dp)
                        .border(
                            1.dp,
                            Color.Gray,
                            RoundedCornerShape(8.dp)
                        ),
                    enabled = false,
                    textStyle = TextStyle(
                        color = Color.White,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Normal
                    ),

                    shape = RoundedCornerShape(8.dp),
                )
            }

            Spacer(modifier = Modifier.height(2.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 8.dp, start = 4.dp)
                        .height(60.dp)
                ) {
                    OutlinedTextField(
                        value = apartmentNo.value,
                        onValueChange = { apartmentNo.value = it },
                        label = {
                            Text(
                                "Bina No",
                                style = TextStyle(
                                    fontSize = 14.sp,
                                    color = colorResource(id = com.finalproject.util.R.color.colorAccent)
                                )
                            )
                        },
                        modifier = Modifier.fillMaxSize(),
                        textStyle = TextStyle(
                            fontSize = 16.sp,
                            color = Color.White
                        )
                    )
                }

                Box(
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 8.dp, start = 4.dp)
                        .height(60.dp)
                ) {
                    OutlinedTextField(
                        value = floorNo.value,
                        onValueChange = { floorNo.value = it },
                        label = {
                            Text(
                                "Kat",
                                style = TextStyle(
                                    fontSize = 14.sp,
                                    color = colorResource(id = com.finalproject.util.R.color.colorAccent)
                                )
                            )
                        },
                        modifier = Modifier.fillMaxSize(),
                        textStyle = TextStyle(
                            fontSize = 16.sp,
                            color = Color.White
                        )
                    )


                }

                Box(
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = 8.dp)
                        .height(60.dp)
                ) {
                    OutlinedTextField(
                        value = homeNo.value,
                        onValueChange = { homeNo.value = it },
                        label = {
                            Text(
                                "Daire No",
                                style = TextStyle(
                                    fontSize = 14.sp,
                                    color = colorResource(id = com.finalproject.util.R.color.colorAccent)
                                )
                            )
                        },
                        modifier = Modifier.fillMaxSize(),
                        textStyle = TextStyle(
                            fontSize = 16.sp,
                            color = Color.White
                        )
                    )
                }
            }



            Spacer(modifier = Modifier.height(4.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(2.dp)
                    .background(Color(0xFF1F1D2B)),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {

                Box(
                    modifier = Modifier
                        .weight(1f)
                        .padding(8.dp)
                        .background(Color(0xFF1F1D2B))
                        .clickable {
                            addressType.value = 1
                        }
                        .background(
                            brush = Brush.verticalGradient(
                                colors = if (addressType.value == 1) {
                                    listOf(
                                        Color(0xB42E363C),
                                        Color(0xFF8AF0FF)
                                    )
                                } else {
                                    listOf(Color.White, Color.LightGray)
                                }
                            ),
                            shape = RoundedCornerShape(2.dp)
                        )
                        .border(
                            width = 2.dp,
                            color = if (addressType.value == 1) Color.Blue else Color.Transparent,
                            shape = RoundedCornerShape(2.dp)
                        )
                        .shadow(4.dp, RoundedCornerShape(2.dp))
                        .padding(6.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_adress_home),
                            contentDescription = "Home",
                            modifier = Modifier.size(40.dp)
                        )
                        Text(
                            text = "Ev",
                            modifier = Modifier.padding(top = 10.dp),
                            style = TextStyle(
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold
                            ),
                            color = colorResource(id = com.finalproject.util.R.color.prime)
                        )
                    }
                }

                Box(
                    modifier = Modifier
                        .weight(1f)
                        .padding(8.dp)
                        .background(Color(0xFF1F1D2B))
                        .clickable {
                            addressType.value = 2
                        }
                        .background(
                            brush = Brush.verticalGradient(
                                colors = if (addressType.value == 2) {
                                    listOf(
                                        Color(0xB42E363C),
                                        Color(0xFF8AF0FF)
                                    )
                                } else {
                                    listOf(Color.White, Color.LightGray)
                                }
                            ),
                            shape = RoundedCornerShape(2.dp)
                        )
                        .border(
                            width = 2.dp,
                            color = if (addressType.value == 2) Color.Blue else Color.Transparent,
                            shape = RoundedCornerShape(2.dp)
                        )
                        .shadow(4.dp, RoundedCornerShape(2.dp))
                        .padding(6.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_address_business),
                            contentDescription = "Business",
                            modifier = Modifier.size(40.dp)
                        )
                        Text(
                            text = "İş Yeri",
                            modifier = Modifier.padding(top = 8.dp),
                            style = TextStyle(
                                fontSize = 14.sp,
                                fontWeight = FontWeight.W900
                            ),
                            color = colorResource(id = com.finalproject.util.R.color.prime)
                        )
                    }
                }

                Box(
                    modifier = Modifier
                        .weight(1f)
                        .padding(8.dp)
                        .clickable {
                            addressType.value = 3
                        }
                        .background(
                            brush = Brush.verticalGradient(
                                colors = if (addressType.value == 3) {
                                    listOf(
                                        Color(0xB42E363C),
                                        Color(0xFF8AF0FF)
                                    )
                                } else {
                                    listOf(Color.White, Color.LightGray)
                                }
                            ),
                            shape = RoundedCornerShape(2.dp)
                        )
                        .border(
                            width = 2.dp,
                            color = if (addressType.value == 3) Color.Blue else Color.Transparent,
                            shape = RoundedCornerShape(2.dp)
                        )
                        .shadow(4.dp, RoundedCornerShape(2.dp))
                        .padding(6.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_other_adress),
                            contentDescription = "Other",
                            modifier = Modifier.size(40.dp)
                        )
                        Text(
                            text = "Diğer",
                            modifier = Modifier.padding(top = 8.dp),
                            style = TextStyle(
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold
                            ),
                            color = colorResource(id = com.finalproject.util.R.color.prime)
                        )
                    }
                }
            }


            Spacer(modifier = Modifier.height(2.dp))
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(2.dp)
            ) {

                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {


                }

                Spacer(modifier = Modifier.padding(bottom = 6.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 2.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {


                    OutlinedTextField(
                        value = addressDescription.value,
                        onValueChange = { addressDescription.value = it },
                        label = {
                            Text(
                                "Adres Tanımı",
                                style = TextStyle(
                                    fontSize = 14.sp,
                                    color = colorResource(id = com.finalproject.util.R.color.colorAccent)
                                )
                            )
                        },

                        modifier = Modifier
                            .weight(1f)
                            .height(60.dp)
                            .padding(end = 25.dp, start = 15.dp),
                        textStyle = TextStyle(
                            fontSize = 16.sp,
                            color = Color.White
                        )


                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    val addressModel = AddressModel(
                        id = "",
                        userNameSurname = fullName.value,
                        userTelephoneNumber = phoneNumber.value,
                        userAddress = address.value,
                        apartmentNo = apartmentNo.value,
                        floor = floorNo.value.toIntOrNull() ?: 0,
                        homeNo = homeNo.value,
                        addressType = addressType.value,
                        addressDescription = addressDescription.value,
                        latitude = latitude.value,
                        longitude = longitude.value
                    )

                    viewModel.saveAddress(addressModel)

                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 14.dp, end = 26.dp, start = 26.dp),
                shape = RoundedCornerShape(26.dp),
                colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = com.finalproject.util.R.color.prime))
            ) {
                androidx.compose.material.Text(
                    text = AnnotatedString("Kaydet"),
                    color = colorResource(id = com.finalproject.util.R.color.colorAccent),
                    fontSize = 20.sp,
                    modifier = Modifier.padding(bottom = 2.dp),
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )
            }

        }
    }
}


private fun setupMapInteraction(
    mapView: MapView,
    context: Context,
    latitude: MutableState<Double>,
    longitude: MutableState<Double>,
    address: MutableState<String>
) {
    val mapboxMap = mapView.mapboxMap

    val annotationManager = mapView.annotations.createPointAnnotationManager()

    mapboxMap.loadStyle(Style.STANDARD) { style ->

        val markerBitmap = BitmapFactory.decodeResource(context.resources, R.drawable.ic_cinema)
        val resizedBitmap = Bitmap.createScaledBitmap(markerBitmap, 100, 100, false)
        annotationManager.deleteAll()

        style.addImage("marker-icon", resizedBitmap)


        mapboxMap.addOnMapClickListener { clickedPoint ->

            annotationManager.annotations.forEach { annotation ->
                annotationManager.delete(annotation)
            }

            addMarker(annotationManager, clickedPoint)

            latitude.value = clickedPoint.latitude()
            longitude.value = clickedPoint.longitude()

            CoroutineScope(Dispatchers.IO).launch {
                fetchAddressFromCoordinates(context, clickedPoint, address)
            }

            true
        }
    }
}


private fun addMarker(annotationManager: PointAnnotationManager, point: Point): PointAnnotation {
    annotationManager.deleteAll()

    val pointAnnotationOptions = PointAnnotationOptions()
        .withPoint(point)
        .withIconImage("marker-icon")

    val annotation = annotationManager.create(pointAnnotationOptions)
    return annotation
}


private fun updateMap(mapView: MapView, coordinates: Point) {
    val mapboxMap = mapView.getMapboxMap()
    val cameraAnimationsPlugin = mapView.camera

    val targetCameraPosition = cameraOptions {
        center(coordinates)
        zoom(15.5)
        pitch(45.0)
        bearing(-17.6)
    }

    val animationOptions = MapAnimationOptions.mapAnimationOptions {
        duration(5200)
    }

    cameraAnimationsPlugin.flyTo(targetCameraPosition, animationOptions)

    val annotationManager = mapView.annotations.createPointAnnotationManager()
    annotationManager.deleteAll()

}


private fun fetchAddressFromCoordinates(
    context: Context,
    point: Point,
    address: MutableState<String>
) {
    try {
        val geocoder = Geocoder(context)
        val addresses = geocoder.getFromLocation(point.latitude(), point.longitude(), 1)
        address.value = addresses?.firstOrNull()?.getAddressLine(0) ?: "Adres Yok"
    } catch (e: Exception) {
        address.value = "Hata"
    }
}


