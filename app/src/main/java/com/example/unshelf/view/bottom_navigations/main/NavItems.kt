package com.example.unshelf.view.bottom_navigations.main

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.ui.graphics.vector.ImageVector

data class NavItem(
    val label: String,
    val icon: ImageVector,
    val route: String
)

val listOfNavItems = listOf(
    NavItem(
        label = "Dashboard",
        icon = Icons.Default.Menu,
        route = Screens.DashboardScreen.name
    ),
    NavItem(
        label = "Listings",
        icon = Icons.Default.List,
        route = Screens.ListingScreen.name
    ),
    NavItem(
        label = "Orders",
        icon = Icons.Default.ShoppingCart,
        route = Screens.OrderScreen.name
    ),
    NavItem(
        label = "Store",
        icon = Icons.Default.Home,
        route = Screens.StoreScreen.name
    ),
)