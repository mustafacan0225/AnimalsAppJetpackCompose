package com.mustafacan.animalsapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.mustafacan.birds.navigation.NavigationBirds
import com.mustafacan.cats.navigation.NavigationCats
import com.mustafacan.core.navigation.root.NavDestinationItem
import com.mustafacan.dogs.navigation.NavigationDogs
import com.mustafacan.reminder.navigation.NavigationReminder

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
            NavigationCats (callbackBottomMenuVisibility = {
                callbackBottomMenuVisibility(it)
            })
        }

        composable<NavDestinationItem.Birds> {

            NavigationBirds(callbackBottomMenuVisibility = {
                callbackBottomMenuVisibility(it)
            })
        }

        composable<NavDestinationItem.Reminder> {
            NavigationReminder()
        }

    }
}