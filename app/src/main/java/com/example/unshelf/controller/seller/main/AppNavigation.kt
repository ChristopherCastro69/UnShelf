package com.example.unshelf.controller.seller.main

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.unshelf.ui.theme.DeepMossGreen
import com.example.unshelf.ui.theme.PalmLeaf
import com.example.unshelf.ui.theme.White
import com.example.unshelf.view.SellerBottomNav.screens.listings.AddProducts
import com.example.unshelf.view.SellerBottomNav.screens.dashboard.Dashboard
import com.example.unshelf.view.SellerBottomNav.screens.dashboard.sellerId
import com.example.unshelf.view.SellerBottomNav.screens.dashboard.storeId
import com.example.unshelf.view.SellerBottomNav.screens.listings.Listings
import com.example.unshelf.view.SellerBottomNav.screens.orders.OrderApproval

import com.example.unshelf.view.SellerBottomNav.screens.orders.Orders
import com.example.unshelf.view.SellerBottomNav.screens.store.Store


@RequiresApi(Build.VERSION_CODES.O)
@Preview
@Composable
fun AppNavigation(){
    val navController = rememberNavController()

    Scaffold (
        bottomBar = {
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentDestination = navBackStackEntry?.destination

            // Check if the current screen is 'AddProducts'
            val isAddProductScreen = currentDestination?.route?.startsWith("addProduct") == true

            if (!isAddProductScreen) {
                NavigationBar (
                    containerColor = Color.White,
                    tonalElevation = 5.dp,
                ) {
                    listOfNavItems.forEach { navItem ->
                        NavigationBarItem(
                            selected = currentDestination?.hierarchy?.any {
                                it.route == navItem.route
                            } == true,
                            onClick = {
                                // Navigate to the selected screen
                                navController.navigate(navItem.route) {
                                    popUpTo(navController.graph.findStartDestination().id) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            },
                            icon = {
                                // Set the icon for each navigation item
                                Icon(
                                    painter = painterResource(if (currentDestination?.hierarchy?.any { it.route == navItem.route } == true) navItem.activeIcon else navItem.iconResId),
                                    contentDescription = null,
                                    tint = if (currentDestination?.hierarchy?.any { it.route == navItem.route } == true) DeepMossGreen else PalmLeaf
                                )
                            },
                            label = {
                                // Set the label for each navigation item
                                Text(
                                    text = navItem.label,
                                    color = if (currentDestination?.hierarchy?.any { it.route == navItem.route } == true) DeepMossGreen else PalmLeaf
                                )
                            },
                            colors = NavigationBarItemDefaults.colors(
                                indicatorColor = White
                            )
                        )
                    }
                }
            }
        }
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = Screens.DashboardScreen.name,
            modifier = Modifier.padding(paddingValues)
        ) {
            composable(route = Screens.DashboardScreen.name) {
                Dashboard()
            }

            composable(route = Screens.OrderScreen.name) {
                Orders()
            }

            composable(route = Screens.ListingScreen.name) {
                Listings(navController, sellerId.value, storeId.value)
            }

            composable(route = Screens.StoreScreen.name) {
                Store()
            }

            composable("addProduct/{productId}") { backStackEntry ->
                val productId = backStackEntry.arguments?.getString("productId")
                AddProducts(productId, navController)
            }

            composable(route = "listings") {
                Listings(navController, sellerId.value, storeId.value)
            }

        }
    }
}