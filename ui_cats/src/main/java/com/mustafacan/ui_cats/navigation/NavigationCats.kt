package com.mustafacan.ui_cats.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.mustafacan.domain.model.cats.Cat
import com.mustafacan.ui_cats.feature.catdetail.CatDetailScreen
import com.mustafacan.ui_cats.feature.catdetail.CatDetailViewModel
import com.mustafacan.ui_cats.feature.cats.CatsScreen
import com.mustafacan.ui_common.navigation.root.NavDestinationItem
import com.mustafacan.ui_common.navigation.root.fromCustom
import kotlin.reflect.typeOf

@Composable
fun NavigationCats(callbackBottomMenuVisibility: (visibility: Boolean) -> Unit) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = NavDestinationItem.Cats
    ) {
        composable<NavDestinationItem.Cats> {
            callbackBottomMenuVisibility(true)
            CatsScreen(navController)
        }

        composable<NavDestinationItem.CatDetailScreen>(typeMap = mapOf(typeOf<Cat>() to NavType.fromCustom<Cat>())) {
            callbackBottomMenuVisibility(false)
            val viewModel = hiltViewModel<CatDetailViewModel>()
            CatDetailScreen(navController, viewModel)
        }

    }
}