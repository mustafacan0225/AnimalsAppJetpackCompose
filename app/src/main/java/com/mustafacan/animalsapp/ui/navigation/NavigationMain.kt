package com.mustafacan.animalsapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.mustafacan.animalsapp.ui.screen.birds.BirdsScreen
import com.mustafacan.animalsapp.ui.screen.cats.CatsScreen
import com.mustafacan.ui_common.navigation.root.NavDestinationItem
import com.mustafacan.ui_dogs.feature.navigation.NavigationDogs

@Composable
fun NavigationMain(
    navController: NavHostController = rememberNavController()
) {

    NavHost(
        navController = navController,
        startDestination = NavDestinationItem.Dogs
    ) {

        composable<NavDestinationItem.Dogs> {
            NavigationDogs()
        }

        composable<NavDestinationItem.Cats> {
            CatsScreen()
        }

        composable<NavDestinationItem.Birds> {
            BirdsScreen()
        }

    }
}