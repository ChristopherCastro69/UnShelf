package com.example.unshelf.view.SellerBottomNav.main

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
import com.example.unshelf.view.SellerBottomNav.screens.dashboard.AddProducts
import com.example.unshelf.view.SellerBottomNav.screens.dashboard.Dashboard
import com.example.unshelf.view.SellerBottomNav.screens.dashboard.sellerId
import com.example.unshelf.view.SellerBottomNav.screens.dashboard.storeId
import com.example.unshelf.view.SellerBottomNav.screens.listings.Listings

import com.example.unshelf.view.SellerBottomNav.screens.orders.Orders
import com.example.unshelf.view.SellerBottomNav.screens.store.Store


@RequiresApi(Build.VERSION_CODES.O)
@Preview
@Composable
fun AppNavigationPreview() {

    AppNavigation() // This will show a preview of your AppNavigation composable

}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AppNavigation(){
    val navController = rememberNavController()

    Scaffold (

        bottomBar = {

            NavigationBar (
                containerColor = Color.White,
                tonalElevation = 5.dp,


            ) {

                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination

                listOfNavItems.forEach{
                    navItem ->
                    NavigationBarItem(
                        selected = currentDestination?.hierarchy?.any {
                            it.route == navItem.route
                        } == true,
                        onClick = {

                            navController.navigate(navItem.route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }

                        },
                        icon = {
                            if (navItem.iconResId != 0 ) {
                                // Use custom icon if the resource ID is provided
                                if (currentDestination?.hierarchy?.any {
                                        it.route == navItem.route
                                    } == true){
                                    Icon(
                                        painter = painterResource(navItem.activeIcon),
                                        contentDescription = null,
                                        tint = DeepMossGreen,

                                    )
                                }
                                Icon(
                                    painter = painterResource(navItem.iconResId),
                                    contentDescription = null,
                                    tint = PalmLeaf,
                                )

                            } else {
                                // Use default icon if no custom resource ID is provided
                                Icon(
                                    imageVector = navItem.icon,
                                    contentDescription = null,
                                    tint = PalmLeaf
                                )
                            }
                        },
                        label = {
                            Text(
                                text = navItem.label,
                                color = if (currentDestination?.hierarchy?.any {
                                        it.route == navItem.route
                                    } == true) {
                                    // Use a different color for the selected item
                                        DeepMossGreen
                                } else {
                                    // Use the default color for unselected items
                                    PalmLeaf // Change this to your desired color
                                }
                            )
                        },
                        colors = NavigationBarItemDefaults.colors(

                            indicatorColor = White,

                        )
                    )

                }
            }
        }
    ){paddingValues->
        NavHost(
            navController = navController,
            startDestination = Screens.DashboardScreen.name,
            modifier = Modifier
                .padding(paddingValues)){
            composable(route = Screens.DashboardScreen.name){
                Dashboard()
            }

            composable(route = Screens.OrderScreen.name){
                Orders()
            }
            composable(route = Screens.ListingScreen.name){
                Listings(navController, sellerId.value, storeId.value)
            }
            composable(route = Screens.StoreScreen.name){
                Store()
            }
            composable(route = "addProduct") {
                AddProducts() // AddProducts composable
            }
        }
    }


}


