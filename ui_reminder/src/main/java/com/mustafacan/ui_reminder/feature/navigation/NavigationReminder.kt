package com.mustafacan.ui_reminder.feature.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.mustafacan.domain.model.dogs.Dog
import com.mustafacan.ui_common.navigation.root.NavDestinationItem
import com.mustafacan.ui_common.navigation.root.fromCustom
import com.mustafacan.ui_reminder.feature.reminder.ReminderScren
import kotlin.reflect.typeOf

@Composable
fun NavigationReminder() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = NavDestinationItem.Reminder
    ) {
        composable<NavDestinationItem.Reminder> {
            ReminderScren()
        }

    }
}