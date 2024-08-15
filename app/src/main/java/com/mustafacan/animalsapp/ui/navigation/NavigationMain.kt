package com.mustafacan.animalsapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.mustafacan.animalsapp.ui.screen.birds.BirdsScreen
import com.mustafacan.animalsapp.ui.screen.cats.CatsScreen
import com.mustafacan.animalsapp.ui.screen.dogs.DogsScreen

@Composable
fun NavigationMain(
    navController: NavHostController = rememberNavController()
) {

    NavHost(
        navController = navController,
        startDestination = NavDestinationItem.Dogs.route
    ) {

        composable(NavDestinationItem.Dogs.route) {
            NavigationDogs()
        }

        composable(NavDestinationItem.Cats.route) {
            CatsScreen()
        }

        composable(NavDestinationItem.Birds.route) {
            BirdsScreen()
        }

    }
}