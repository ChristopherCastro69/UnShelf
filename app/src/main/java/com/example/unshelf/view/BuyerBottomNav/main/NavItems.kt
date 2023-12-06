package com.example.unshelf.view.BuyerBottomNav.main
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.rounded.LocationOn
import androidx.compose.material.icons.rounded.ShoppingCart
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.Color
import com.example.unshelf.ui.theme.PalmLeaf


data class BuyerNavItem(
    val label: String,
    val icon: ImageVector,
    val route: String,
    val textColor : Color,
)


val listOfBuyerNavItems = listOf(
    BuyerNavItem(
        label = "Marketplace",
        icon = Icons.Default.Home,
        route = BuyerScreens.Marketplace.name,
        textColor = PalmLeaf
        // Replace with your custom icon resource ID
    ),
    BuyerNavItem(
        label = "Near Me",
        icon = Icons.Rounded.LocationOn,
        route = BuyerScreens.NearMe.name,
        textColor = PalmLeaf// Replace with your custom icon resource ID

    ),
    BuyerNavItem(
        label = "Profile",
        icon = Icons.Default.Person,
        route = BuyerScreens.Profile.name,
        // Replace with your custom icon resource ID
        textColor = PalmLeaf
    ),

)
