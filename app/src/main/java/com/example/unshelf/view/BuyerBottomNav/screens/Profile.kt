package com.example.unshelf.view.BuyerBottomNav.screens

import JostFontFamily
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
import androidx.compose.material.icons.filled.Assignment
import androidx.compose.material.icons.filled.DeliveryDining
import androidx.compose.material.icons.filled.Discount
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.LocalOffer
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.ShoppingBag
import androidx.compose.material.icons.filled.Wallet
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.unshelf.R
import com.example.unshelf.ui.theme.DeepMossGreen
import com.example.unshelf.view.SellerBottomNav.screens.store.Store

@Composable
fun Profile() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFE8F5E9))
            .padding(top = 32.dp, start = 16.dp, end = 16.dp)
    ) {

        BuyerProfileHeader()
        Spacer(modifier = Modifier.height(24.dp))
        BuyerProfileOptions()
    }
}

@Composable
fun BuyerProfileHeader() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Profile picture
        Image(
            painter = painterResource(id = R.drawable.profile), // Replace with the actual profile picture resource ID
            contentDescription = "Profile Picture",
            modifier = Modifier
                .size(80.dp) // Adjust size as needed
                .clip(CircleShape)
                .background(DeepMossGreen) // Replace with the color of the profile picture background if needed
        )
        Spacer(modifier = Modifier.width(24.dp))
        // Profile info
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = "John Doe",
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                color = DeepMossGreen
            )
            Text(
                text = "Poblacion Street",
                fontSize = 14.sp,
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
fun BuyerProfileOptions() {
    val options = listOf(
//        "Edit Profile" to Icons.Default.Store,
        "Addresses" to Icons.Default.LocationOn,
        "Order Tracking" to Icons.Default.Assignment,
        "Payment Options" to Icons.Default.Wallet,
        "Vouchers" to Icons.Default.Discount,
        "Settings" to Icons.Default.Settings,
        "Language" to Icons.Default.Language,
        "Log Out" to Icons.Default.ExitToApp
    )

    Column {
        options.forEach { (option, icon) ->
            BuyerProfileOptionItem(option, icon)
        }
    }
}

@Composable
fun BuyerProfileOptionItem(option: String, icon: ImageVector) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { /* Handle click */ }
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
}

@Composable
fun BuyerCustomTopBar() {
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
fun PreviewProfileScreen() {

    Column {
        BuyerCustomTopBar()
        Profile()

    }
}
