package com.mustafacan.dogs.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.mustafacan.domain.model.dogs.Dog
import com.mustafacan.core.navigation.root.NavDestinationItem
import com.mustafacan.core.navigation.root.fromCustom
import com.mustafacan.dogs.feature.dogdetail.DogDetailScreen
import com.mustafacan.dogs.feature.dogdetail.DogDetailViewModel
import com.mustafacan.dogs.feature.dogs.DogsScreen
import kotlin.reflect.typeOf

@Composable
fun NavigationDogs(callbackBottomMenuVisibility: (visibility: Boolean) -> Unit) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = NavDestinationItem.Dogs
    ) {
        composable<NavDestinationItem.Dogs> {
            callbackBottomMenuVisibility(true)
            DogsScreen(navController)
        }

        composable<NavDestinationItem.DogDetailScreen>(typeMap = mapOf(typeOf<Dog>() to NavType.fromCustom<Dog>())) {
            callbackBottomMenuVisibility(false)
            val viewModel = hiltViewModel<DogDetailViewModel>()
            DogDetailScreen(navController, viewModel)
        }

    }
}