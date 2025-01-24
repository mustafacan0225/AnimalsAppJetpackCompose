package com.mustafacan.core.components.toolbar

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Toolbar(title: String = "", onBackPressed: (() -> Unit)? = null, actionList: List<ToolbarAction>? = null) {

    TopAppBar(title = { Row (verticalAlignment = Alignment.CenterVertically){
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
                    Box {
                        val value = actionWithBadge?.badgeValue?: 0
                        val startPadding = if (value < 10 ) 8.dp else if (value < 100) 13.dp else 18.dp
                        Icon(modifier = Modifier.padding(start = startPadding, 10.dp, 10.dp, 10.dp).clickable { action.onAction() },
                            imageVector = action.imageVector,
                            contentDescription = "",
                        )

                        if (actionWithBadge?.badgeValue?: 0 > 0) {
                            Badge(modifier = Modifier.align(Alignment.BottomStart)) {
                                Text(text = "${actionWithBadge?.badgeValue?: 0}")
                            }
                        }

                    }
                } else {
                    Icon(modifier = Modifier.clickable { action.onAction() },
                        imageVector = action.imageVector,
                        contentDescription = "",
                    )
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