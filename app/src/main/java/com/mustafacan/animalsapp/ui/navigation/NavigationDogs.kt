package com.mustafacan.animalsapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.dialog
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.mustafacan.animalsapp.ui.screen.dogs.DogsScreen
import com.mustafacan.animalsapp.ui.screen.dogs.detail.DogDetailScreen
import com.mustafacan.animalsapp.ui.screen.dogs.detail.DogDetailViewModel
import com.mustafacan.domain.model.dogs.Dog
import kotlin.reflect.typeOf

@Composable
fun NavigationDogs() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = NavDestinationItem.Dogs
    ) {
        composable<NavDestinationItem.Dogs> {
            //onChangeVisibilityBottomMenu?.invoke(true)
            DogsScreen(navController)
        }

        composable<NavDestinationItem.DogDetailScreen>(typeMap = mapOf(typeOf<Dog>() to NavType.fromCustom<Dog>())) {
            //onChangeVisibilityBottomMenu?.invoke(true)
            val viewModel = hiltViewModel<DogDetailViewModel>()
            DogDetailScreen(navController, viewModel)
        }

    }
}