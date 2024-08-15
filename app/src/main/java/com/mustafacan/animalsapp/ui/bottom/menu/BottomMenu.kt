package com.mustafacan.animalsapp.ui.bottom.menu

import androidx.compose.foundation.layout.size
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import com.mustafacan.animalsapp.R
import com.mustafacan.animalsapp.ui.navigation.NavDestinationItem

@Composable
fun BottomMenu(
    navController: NavController
) {
    val items = listOf(
        NavDestinationItem.Dogs,
        NavDestinationItem.Birds,
        NavDestinationItem.Cats)

    BottomAppBar(
        containerColor = colorResource(id = R.color.statusbar_color),
        //cutoutShape = RoundedCornerShape(50)
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        items.forEachIndexed { index, item ->
            val route = item.route
            val context = LocalContext.current
            NavigationBarItem(selected = currentRoute?.contains(item.route)?: false,
                label = { Text(text = stringResource(id = item.titleResource), maxLines = 1) },
                onClick = {
                    navController.navigate(item.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        // Avoid multiple copies of the same destination when re-selecting the same item
                        launchSingleTop = true
                        // Restore state when re-selecting a previously selected item
                        restoreState = true
                    }
                }, icon = {
                    Icon(painter = painterResource(id = item.icon!!),
                        contentDescription = null,
                        modifier = Modifier.size(24.dp) ) },
                alwaysShowLabel = true,
                colors = NavigationBarItemDefaults.colors(selectedIconColor = Color.White,
                    selectedTextColor = Color.White,
                    indicatorColor = colorResource(id = R.color.bottom_menu_indicator_color),
                    unselectedIconColor = colorResource(id = R.color.dark_gray),
                    unselectedTextColor = colorResource(id = R.color.dark_gray)
                )
            )

        }
    }


}