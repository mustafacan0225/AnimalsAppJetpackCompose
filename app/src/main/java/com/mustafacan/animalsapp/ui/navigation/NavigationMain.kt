package com.mustafacan.animalsapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.mustafacan.animalsapp.ui.screen.cats.CatsScreen
import com.mustafacan.ui_birds.navigation.NavigationBirds
import com.mustafacan.ui_common.navigation.root.NavDestinationItem
import com.mustafacan.ui_dogs.navigation.NavigationDogs
import com.mustafacan.ui_reminder.feature.navigation.NavigationReminder

@Composable
fun NavigationMain(
    navController: NavHostController = rememberNavController(),
    callbackBottomMenuVisibility: (visibility: Boolean) -> Unit
) {

    NavHost(
        navController = navController,
        startDestination = NavDestinationItem.Dogs
    ) {

        composable<NavDestinationItem.Dogs> {
            NavigationDogs(callbackBottomMenuVisibility = {
                callbackBottomMenuVisibility(it)
            })
        }

        composable<NavDestinationItem.Cats> {
            CatsScreen()
        }

        composable<NavDestinationItem.Birds> {
            //BirdsScreen()
            NavigationBirds(callbackBottomMenuVisibility = {
                callbackBottomMenuVisibility(it)
            })
        }

        composable<NavDestinationItem.Reminder> {
            NavigationReminder()
        }

    }
}