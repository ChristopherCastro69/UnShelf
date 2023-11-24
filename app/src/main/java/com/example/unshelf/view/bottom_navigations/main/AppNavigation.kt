package com.example.unshelf.view.bottom_navigations.main

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.unshelf.view.bottom_navigations.screens.Dashboard
import com.example.unshelf.view.bottom_navigations.screens.Listings
import com.example.unshelf.view.bottom_navigations.screens.Orders
import com.example.unshelf.view.bottom_navigations.screens.Store

@Preview
@Composable
fun AppNavigationPreview() {
    AppNavigation() // This will show a preview of your AppNavigation composable
}

@Composable
fun AppNavigation(){
    val navController = rememberNavController()


    Scaffold (
        bottomBar = {
            NavigationBar {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination

                listOfNavItems.forEach{
                    navItem ->
                    NavigationBarItem(
                        selected = currentDestination?.hierarchy?.any{
                            it.route == navItem.route
                        } == true,
                        onClick = {
                                  navController.navigate(navItem.route){
                                      popUpTo(navController.graph.findStartDestination().id){
                                          saveState = true
                                      }
                                      launchSingleTop = true
                                      restoreState = true
                                  }
                        },
                        icon = {
                            Icon(
                                imageVector = navItem.icon,
                                contentDescription = null
                            )
                        },
                        label = {
                            Text(text = navItem.label)
                        }
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
            composable(route = Screens.ListingScreen.name){
                Listings()
            }
            composable(route = Screens.OrderScreen.name){
                Orders()
            }
            composable(route = Screens.StoreScreen.name){
                Store()
            }
        }
    }


}
