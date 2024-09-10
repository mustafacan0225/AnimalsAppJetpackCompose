package com.mustafacan.ui_dogs.feature.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.mustafacan.domain.model.dogs.Dog
import com.mustafacan.ui_common.navigation.root.NavDestinationItem
import com.mustafacan.ui_common.navigation.root.fromCustom
import com.mustafacan.ui_dogs.feature.dogdetail.DogDetailScreen
import com.mustafacan.ui_dogs.feature.dogdetail.DogDetailViewModel
import com.mustafacan.ui_dogs.feature.dogs.DogsScreen
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