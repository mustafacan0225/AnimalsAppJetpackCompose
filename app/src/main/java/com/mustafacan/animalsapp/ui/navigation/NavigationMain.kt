package com.mustafacan.animalsapp.ui.navigation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
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
            BirdsScreen()
        }

        composable<NavDestinationItem.Reminder> {
            Column(modifier = Modifier.fillMaxSize()) {
                Text(text = "REMINDER PAGE")
            }
        }

    }
}