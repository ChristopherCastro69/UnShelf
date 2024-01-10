package com.example.unshelf.view.SellerBottomNav.screens.store

import JostFontFamily
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForwardIos
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.ShoppingBag
import androidx.compose.material.icons.filled.Store
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.unshelf.R
import com.example.unshelf.controller.User.UserController
import com.example.unshelf.model.admin.logoutBuyer
import com.example.unshelf.model.admin.logoutUser
import com.example.unshelf.model.firestore.seller.StoreProfileModel.fetchStoreDetails
import com.example.unshelf.ui.theme.DeepMossGreen
import com.example.unshelf.ui.theme.PalmLeaf
import com.example.unshelf.ui.theme.WatermelonRed
import com.example.unshelf.view.SellerBottomNav.screens.dashboard.sellerId
import com.example.unshelf.view.SellerBottomNav.screens.dashboard.storeId
import kotlinx.coroutines.launch

var showLogoutConfirmationDialog = mutableStateOf(false)
var storeName = mutableStateOf("")
var rating = mutableStateOf(1.0)
var followers = mutableStateOf(0)
var address = mutableStateOf("")

//@Preview
@Composable
fun Store() {
    val context = LocalContext.current
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFE8F5E9))
            .padding(top = 32.dp, start = 16.dp, end = 16.dp)
    ) {

        ProfileHeader()
        Spacer(modifier = Modifier.height(24.dp))
        ProfileOptions(context)
    }
}
@Composable
fun ProfileHeader() {
//    val storeProfileModel : StoreProfileModel = viewModel()
//    val stores by storeProfileModel.store.collectAsState()
    val IDseller = sellerId.value
    Log.d(
        "Profile",
        "LaunchedEffect Seller ID: ${IDseller}, Store ID: ${storeId.value}"
    )
    var isLoadingDetails by remember { mutableStateOf(false) }
    LaunchedEffect(IDseller){
        IDseller?.let {
            isLoadingDetails = true
            fetchStoreDetails(it, onSuccess = { store ->
                storeName.value = store.storeName
                rating.value = store.rating
                followers.value = store.followers
                address.value = store.address

                isLoadingDetails = false
            }, onFailure = { exception ->
                Log.e(
                    "AddProducts",
                    "Error fetching product details: ${exception.message}",
                    exception
                )
                isLoadingDetails = false
            })
        }
    }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Profile picture
        Image(
            painter = painterResource(id = R.drawable.avatar1),
            contentDescription = "Profile Picture",
            modifier = Modifier
                .size(80.dp)
                .clip(CircleShape)
                .background(DeepMossGreen),
        )
        Spacer(modifier = Modifier.width(24.dp))
        // Profile info
        Column(
            modifier = Modifier.weight(1f)
        ) {
            val seller = UserController.seller!!
            Text(
                text = "${seller.storeName}",
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                color = DeepMossGreen
            )
            Text(
                text = "${seller.address}",
                fontSize = 14.sp,
                color = DeepMossGreen
            )
            Text(
                text = "⭐ ${rating.value} Rating",
                fontSize = 14.sp,
                color = DeepMossGreen
            )
            Text(
                text = "${followers.value} Followers",
                fontSize = 12.sp,
                color = DeepMossGreen
            )
        }
        Spacer(modifier = Modifier.width(12.dp))
        // Edit button - replace with your actual edit icon
        IconButton(
            onClick = { /* TODO: Handle edit action */ },
            modifier = Modifier.size(24.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Edit,
                contentDescription = "Edit Profile",
                tint = DeepMossGreen
            )
        }
    }
}

@Composable
fun ProfileOptions(context: Context) {
    val options = listOf(
        "Edit Profile" to Icons.Default.Store,

//        "Delivery Settings" to Icons.Default.DeliveryDining,
        "Pickup Settings" to Icons.Default.ShoppingBag,
//        "Store Locations" to Icons.Default.LocationOn,
//        "Promotions" to Icons.Default.LocalOffer,
//        "Settings" to Icons.Default.Settings,
//        "Language" to Icons.Default.Language,
        "Log Out" to Icons.Default.ExitToApp

    )

    Column {
        options.forEach { (option, icon) ->
            ProfileOptionItem(option, icon, context)
        }
    }
}

@Composable
fun ProfileOptionItem(option: String, icon: ImageVector, context: Context) {
    val coroutineScope = rememberCoroutineScope()
    // State to track whether the logout confirmation dialog is shown



    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                /* Handle click */
                if (option == "Edit Profile") {
                    // Launch the Profile activity using Intent
                    val intent = Intent(context, SellerProfile::class.java)
                    context.startActivity(intent)
                } else if (option == "Log Out") {
                    // Show the confirmation dialog
                    showLogoutConfirmationDialog.value = true
                } else {
                    // Handle other options
                }
            }
            .padding(vertical = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null, // Decorative icon
            modifier = Modifier.size(24.dp),
            tint = DeepMossGreen
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = option,
            modifier = Modifier.weight(1f),
            fontSize = 18.sp,
            color = DeepMossGreen
        )
        Icon(
            imageVector = Icons.Default.ArrowForwardIos,
            contentDescription = "Go to $option",
            modifier = Modifier.size(20.dp),
            tint = DeepMossGreen
        )
    }

    // Confirmation dialog
    if (showLogoutConfirmationDialog.value) {
        AlertDialog(
            onDismissRequest = {
                // Dismiss the dialog if the user cancels
                showLogoutConfirmationDialog.value = false
            },
            title = {
                Text(text = "Logout")
            },
            text = {
                Text(text = "Are you sure you want to log out?")
            },
            confirmButton = {
                Button(
                    onClick = {
                        // Perform logout action
                        coroutineScope.launch {
                            logoutUser(context)
                        }
                        showLogoutConfirmationDialog.value = false
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = PalmLeaf)
                ) {
                    Text("Yes")
                }
            },
            dismissButton = {
                Button(
                    onClick = {
                        // Dismiss the dialog if the user cancels
                        showLogoutConfirmationDialog.value = false
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = WatermelonRed)
                ) {
                    Text("No")
                }
            }
        )
    }
}


@Composable
fun CustomTopBar() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(DeepMossGreen)
            .padding(18.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        IconButton(
            onClick = { /* TODO: Handle wallet action */ },
            modifier = Modifier.size(32.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.button_wallet), // Your wallet icon
                contentDescription = "Wallet",
            )
        }
        Spacer(modifier = Modifier.weight(1f))
        Text(
            text = "PROFILE",
            fontFamily = JostFontFamily,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )
        Spacer(modifier = Modifier.weight(1f))
        IconButton(
            onClick = { /* TODO: Handle notifications action */ },
            modifier = Modifier.size(32.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_notification_bell), // Your notification icon
                contentDescription = "Notifications"
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewStoreScreen() {

    Column {
        CustomTopBar()
        Store()

    }
}