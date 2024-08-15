package com.mustafacan.animalsapp.ui.toolbar

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Notifications
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
                /*Icon(
                    modifier = Modifier.size(48.dp).fillMaxWidth().padding(end = 8.dp),
                    imageVector = Icons.Filled.AccountCircle,
                    contentDescription = "profile picture",
                )*/
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
                if (action.imageVector == Icons.Filled.Notifications) {
                    Box(modifier = Modifier.padding(12.dp)) {
                        IconButton(onClick = { action.onAction() }) {
                            Icon(
                                modifier = Modifier.size(42.dp),
                                imageVector = action.imageVector,
                                contentDescription = "",
                            )
                        }
                        Badge(
                            modifier = Modifier
                                .border(1.dp, color = Color.White, shape = CircleShape)
                                .align(Alignment.TopEnd)
                                .clip(CircleShape)
                        ) {
                            Text(text = "5")
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