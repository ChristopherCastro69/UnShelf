package com.example.unshelf.view.BuyerBottomNav.main

import Marketplace
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.height
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
import com.example.unshelf.view.BuyerBottomNav.screens.NearMe
import com.example.unshelf.view.BuyerBottomNav.screens.PreviewProfileScreen
import com.example.unshelf.view.BuyerBottomNav.screens.Profile


@Preview
@Composable
fun BuyerAppNavigationPreview() {

    BuyerAppNavigation() // This will show a preview of your AppNavigation composable

}


@Composable
fun BuyerAppNavigation(){
    val navController = rememberNavController()

    Scaffold (

        bottomBar = {

            NavigationBar (
                containerColor = Color.White,
                modifier = Modifier.height(60.dp),
                tonalElevation = 5.dp,


            ) {

                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination

                listOfBuyerNavItems.forEach{
                    buyerNavItem ->
                    NavigationBarItem(
                        selected = currentDestination?.hierarchy?.any {
                            it.route == buyerNavItem.route
                        } == true,
                        onClick = {

                            navController.navigate(buyerNavItem.route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }

                        },
                        icon = {
                            Icon(
                                imageVector = buyerNavItem.icon,
                                contentDescription = null,
                                tint = PalmLeaf
                            )
                        },
                        label = {
                            Text(
                                text = buyerNavItem.label,
                                color = if (currentDestination?.hierarchy?.any {
                                        it.route == buyerNavItem.route
                                    } == true) {
                                        DeepMossGreen
                                } else {
                                    PalmLeaf 
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
            startDestination = BuyerScreens.Marketplace.name,
            modifier = Modifier
                .padding(paddingValues)){
            composable(route = BuyerScreens.Marketplace.name){
                Marketplace()
            }

            composable(route = BuyerScreens.NearMe.name){
                NearMe()
            }
            composable(route = BuyerScreens.Profile.name){
                //Listings(navController, sellerId.value, storeId.value)
                PreviewProfileScreen()
            }
            // composable("addProduct/{productId}") { backStackEntry ->
            //     val productId = backStackEntry.arguments?.getString("productId")
            //     //AddProducts(productId) // Adjust according to your actual implementation
            // }

        }
    }


}


