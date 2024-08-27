package com.mustafacan.animalsapp.ui.components.toolbar

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.State
import androidx.compose.ui.graphics.vector.ImageVector

sealed class ToolbarAction(
    val imageVector: ImageVector,
    val onAction: () -> Unit
) {

    sealed class WithBadge(val image: ImageVector, val badgeValue: State<Int>, val callback: () -> Unit) : ToolbarAction(
        imageVector = image,
        onAction = callback
    )

    data class Favorites(val action: () -> Unit, val badge: State<Int>) : WithBadge(
        image = Icons.Filled.Favorite,
        badgeValue = badge,
        callback = action,

    )

    data class RefreshData(val action: () -> Unit) : ToolbarAction(
        imageVector = Icons.Filled.Refresh,
        onAction = action
    )

    data class OpenSettings(val action: () -> Unit) : ToolbarAction(
        imageVector = Icons.Filled.Settings,
        onAction = action
    )
}