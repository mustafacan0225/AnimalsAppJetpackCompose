package com.mustafacan.ui_common.components.toolbar

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Badge
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Toolbar(
    title: String = "Animals App",
    onBackPressed: (() -> Unit)? = null,
    actionList: List<ToolbarAction>? = null) {

    TopAppBar(
        title = {
            Row (
                verticalAlignment = Alignment.CenterVertically
            ){
                if (onBackPressed == null && actionList == null)
                    Text(text = title, modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center)
                else
                    Text(text = title)
            }
        },
        modifier = Modifier.background(MaterialTheme.colorScheme.primary),
        navigationIcon = {
            onBackPressed?.let {
                IconButton(onClick = { it() }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "back"
                    )
                }
            }

        },
        actions = {
            actionList?.forEach { action ->
                val actionWithBadge = action as? ToolbarAction.WithBadge
                if (actionWithBadge != null) {
                    Box(modifier = Modifier.padding(12.dp)) {
                        IconButton(onClick = { action.onAction() }) {
                            Icon(
                                //modifier = Modifier.size(42.dp),
                                imageVector = action.imageVector,
                                contentDescription = "",
                            )
                        }

                        if (actionWithBadge?.badgeValue?.value?: 0 > 0) {
                            Badge(
                                modifier = Modifier
                                    .border(1.dp, color = Color.White, shape = CircleShape)
                                    .align(Alignment.TopEnd)
                                    .clip(CircleShape)
                            ) {
                                Text(text = "${actionWithBadge?.badgeValue?.value?: 0}")
                            }
                        }

                    }
                } else {
                    IconButton(onClick = { action.onAction() }) {
                        Icon(
                            imageVector = action.imageVector,
                            contentDescription = "",
                        )
                    }
                }

            }

        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary,
            navigationIconContentColor = Color.White,
            titleContentColor = Color.White,
            actionIconContentColor = Color.White
        )
    )
}