package com.mustafacan.animalsapp.ui.toolbar

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector

sealed class ToolbarAction(
    val imageVector: ImageVector,
    val onAction: () -> Unit
) {

    data class Notifications(val action: () -> Unit) : ToolbarAction(
        imageVector = Icons.Filled.Notifications,
        onAction = action
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