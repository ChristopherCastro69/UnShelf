package com.example.unshelf.view.SellerBottomNav.main
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.rounded.ShoppingCart
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.unshelf.R
import androidx.compose.ui.graphics.Color
import com.example.unshelf.ui.theme.PalmLeaf


data class NavItem(
    val label: String,
    val icon: ImageVector,
    val route: String,
    val iconResId: Int,  // Resource ID for the custom icon
    val activeIcon: Int,
    val textColor : Color,
)


val listOfNavItems = listOf(
    NavItem(
        label = "Dashboard",
        icon = Icons.Default.Info,
        route = Screens.DashboardScreen.name,
        iconResId = R.drawable.bottomnav_dashboard,
        activeIcon = R.drawable.ic_active_dashboard,
        textColor = PalmLeaf
        // Replace with your custom icon resource ID
    ),
    NavItem(
        label = "Orders",
        icon = Icons.Rounded.ShoppingCart,
        route = Screens.OrderScreen.name,
        iconResId = R.drawable.bottomnav_orders,
        activeIcon = R.drawable.ic_active_orders,
        textColor = PalmLeaf// Replace with your custom icon resource ID

    ),
    NavItem(
        label = "Listings",
        icon = Icons.Default.List,
        route = Screens.ListingScreen.name,
        iconResId = R.drawable.bottomnav_listing, // Replace with your custom icon resource ID
        activeIcon = R.drawable.ic_active_listing,
        textColor = PalmLeaf
    ),

    NavItem(
        label = "Store",
        icon = Icons.Default.Home,
        route = Screens.StoreScreen.name,
        iconResId = R.drawable.bottomnav_store,
        activeIcon = R.drawable.ic_active_store,
        textColor = PalmLeaf// Replace with your custom icon resource ID
    ),
)
