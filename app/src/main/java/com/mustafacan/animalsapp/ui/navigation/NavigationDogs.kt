package com.mustafacan.animalsapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.mustafacan.animalsapp.ui.screen.dogs.DogsScreen
import com.mustafacan.animalsapp.ui.screen.dogs.detail.DogDetailScreen

@Composable
fun NavigationDogs() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = NavDestinationItem.Dogs.route
    ) {
        composable(NavDestinationItem.Dogs.route) {
            //onChangeVisibilityBottomMenu?.invoke(true)
            DogsScreen(navController)
        }

        composable(NavDestinationItem.DogsDetail.route) {
            //onChangeVisibilityBottomMenu?.invoke(true)
            DogDetailScreen(navController)
        }
    }
}