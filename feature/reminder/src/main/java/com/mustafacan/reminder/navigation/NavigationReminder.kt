package com.mustafacan.reminder.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.mustafacan.core.navigation.root.NavDestinationItem
import com.mustafacan.reminder.feature.reminder.ReminderScren

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