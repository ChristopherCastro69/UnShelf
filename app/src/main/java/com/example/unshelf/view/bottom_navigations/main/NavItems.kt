package com.example.unshelf.view.bottom_navigations.main
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Assignment
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.unshelf.R

data class NavItem(
    val label: String,
    val icon: ImageVector,
    val route: String,
    val iconResId: Int // Resource ID for the custom icon
)


val listOfNavItems = listOf(
    NavItem(
        label = "Dashboard",
        icon = Icons.Default.Info,
        route = Screens.DashboardScreen.name,
        iconResId = R.drawable.ic_dashboard // Replace with your custom icon resource ID
    ),
    NavItem(
        label = "Listings",
        icon = Icons.Default.List,
        route = Screens.ListingScreen.name,
        iconResId = R.drawable.ic_listing // Replace with your custom icon resource ID
    ),
    NavItem(
        label = "Orders",
        icon = Icons.Filled.Assignment,
        route = Screens.OrderScreen.name,
        iconResId = 0 // Replace with your custom icon resource ID
    ),
    NavItem(
        label = "Store",
        icon = Icons.Default.Home,
        route = Screens.StoreScreen.name,
        iconResId = R.drawable.ic_store // Replace with your custom icon resource ID
    ),
)
