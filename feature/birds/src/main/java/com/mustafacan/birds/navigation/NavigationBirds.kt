package com.mustafacan.birds.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.mustafacan.domain.model.birds.Bird
import com.mustafacan.birds.feature.birds.BirdsScreen
import com.mustafacan.birds.feature.detail.BirdDetailScreen
import com.mustafacan.birds.feature.detail.BirdDetailViewModel
import com.mustafacan.ui_common.navigation.root.NavDestinationItem
import com.mustafacan.ui_common.navigation.root.fromCustom
import kotlin.reflect.typeOf

@Composable
fun NavigationBirds(callbackBottomMenuVisibility: (visibility: Boolean) -> Unit) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = NavDestinationItem.Birds
    ) {
        composable<NavDestinationItem.Birds> {
            callbackBottomMenuVisibility(true)
            BirdsScreen(navController = navController)
        }

        composable<NavDestinationItem.BirdDetailScreen>(typeMap = mapOf(typeOf<Bird>() to NavType.fromCustom<Bird>())) {
            callbackBottomMenuVisibility(false)
            val viewModel = hiltViewModel<BirdDetailViewModel>()
            BirdDetailScreen(navController, viewModel)
        }

    }
}