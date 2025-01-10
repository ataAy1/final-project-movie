package com.finalproject.address

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.finalproject.domain.usecase.profile.address.GetAddressUseCase
import com.finalproject.domain.usecase.profile.address.SaveAddressUseCase
import androidx.hilt.navigation.compose.hiltViewModel
import com.finalproject.domain.model.user.AddressModel
import com.finalproject.util.LoadingAnimation

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddressScreen(
    navController: NavController,
    viewModel: AddressViewModel = hiltViewModel()
) {
    val addressState by viewModel.getAddressState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.getAddresses()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    androidx.compose.material.Text(
                        text = "Adreslerim",
                        modifier = Modifier.fillMaxWidth(),
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
                    containerColor = colorResource(id = com.finalproject.util.R.color.prime)
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.navigate("add_address_screen") }
            ) {
                Icon(imageVector = Icons.Filled.Add, contentDescription = "Add Address")
            }
        }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 80.dp)
                .background(colorResource(id = com.finalproject.util.R.color.prime2)),
            contentAlignment = Alignment.Center
        ) {
            when (addressState) {
                is AddressState.Loading -> {
                    LoadingAnimation(
                        circleSize = 35.dp,
                        circleColor = colorResource(id = com.finalproject.util.R.color.colorAccent),
                        spaceBetween = 10.dp,
                        travelDistance = 20.dp
                    )
                }
                is AddressState.Error -> {
                    Text(
                        text = (addressState as AddressState.Error).message,
                        color = Color.Red
                    )
                }
                is AddressState.Success -> {
                    val addresses = (addressState as AddressState.Success).addresses
                    if (addresses.isEmpty()) {
                        Text("No addresses found")
                    } else {
                        LazyColumn(
                            modifier = Modifier.fillMaxSize(),
                            verticalArrangement = Arrangement.spacedBy(8.dp),
                            contentPadding = PaddingValues(16.dp)
                        ) {
                            items(addresses) { address ->
                                AddressRow(
                                    address = address,
                                    onDelete = {}
                                )
                            }
                        }
                    }
                }
                is AddressState.Saving -> {
                }
                AddressState.Idle -> {
                    Text("Ready to manage addresses.")
                }
            }

        }
    }
}


@Composable
fun AddressRow(address: AddressModel, onDelete: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(colorResource(id = com.finalproject.util.R.color.darkAccent))
            .border(
                width = 1.dp,
                color = colorResource(id = com.finalproject.util.R.color.brown),
                shape = RoundedCornerShape(8.dp)
            )
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        val icon = when (address.addressType) {
            1 -> R.drawable.ic_adress_home
            2 -> R.drawable.ic_address_other
            3 -> R.drawable.ic_address_business
            else -> R.drawable.ic_adress_home
        }

        Image(
            painter = painterResource(id = icon),
            contentDescription = "Address Type",
            modifier = Modifier.size(30.dp)
        )

        Spacer(modifier = Modifier.width(16.dp))

        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = address.addressDescription,
                color = colorResource(id = com.finalproject.util.R.color.colorAccent),
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = address.userAddress,
                style = MaterialTheme.typography.bodyMedium,
                color = Color.White
            )
        }

        Spacer(modifier = Modifier.width(8.dp))

            Image(
                painterResource(id  = R.drawable.ic_delete3),
                contentDescription = "Delete Address",
                modifier = Modifier.size(28.dp)
                    .padding(end = 4.dp)

            )

    }
}
