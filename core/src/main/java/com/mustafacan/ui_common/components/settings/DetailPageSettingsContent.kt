package com.mustafacan.ui_common.components.settings

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mustafacan.ui_common.model.enums.ViewTypeForTab

@Composable
fun SettingsDialog(
    currentViewTypeForTab: ViewTypeForTab,
    saveSettings: (ViewTypeForTab) -> Unit,
    onDismiss: () -> Unit
) {
    var stateOfViewTypeForTab by remember { mutableStateOf(currentViewTypeForTab) }
    val viewTypeForTabOptions = listOf(
        ViewTypeForTab.TAB_TYPE_CUSTOM_INDICATOR_WITH_ICON,
        ViewTypeForTab.TAB_TYPE_CUSTOM_INDICATOR_WITH_LEADING_ICON,
        ViewTypeForTab.TAB_TYPE_CUSTOM_INDICATOR_WITHOUT_ICON,
        ViewTypeForTab.TAB_TYPE_DEFAULT_INDICATOR_WITH_ICON,
        ViewTypeForTab.TAB_TYPE_DEFAULT_INDICATOR_WITH_LEADING_ICON,
        ViewTypeForTab.TAB_TYPE_DEFAULT_INDICATOR_WITHOUT_ICON
    )

    Surface(
        shape = RoundedCornerShape(16.dp),
        color = Color.White
    ) {
        Box(
            contentAlignment = Alignment.Center
        ) {
            Column(modifier = Modifier.padding(20.dp)) {

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    androidx.compose.material3.Text(
                        text = "Settings",
                        style = TextStyle(
                            fontSize = 24.sp,
                            fontFamily = FontFamily.Default,
                            fontWeight = FontWeight.Bold
                        )
                    )

                    androidx.compose.material3.Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "",
                        tint = Color.DarkGray,
                        modifier = Modifier
                            .width(30.dp)
                            .height(30.dp)
                            .clickable { onDismiss() }
                    )

                }

                Spacer(modifier = Modifier.height(20.dp))

                androidx.compose.material3.Text(
                    text = stringResource(id = com.mustafacan.ui_common.R.string.view_mode_tab_description),
                    fontWeight = FontWeight.Bold
                )

                Column(Modifier.selectableGroup()) {
                    viewTypeForTabOptions.forEach { type ->
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .selectable(
                                    selected = if (type == ViewTypeForTab.TAB_TYPE_CUSTOM_INDICATOR_WITH_ICON) {
                                        stateOfViewTypeForTab == ViewTypeForTab.TAB_TYPE_CUSTOM_INDICATOR_WITH_ICON
                                    } else if (type == ViewTypeForTab.TAB_TYPE_CUSTOM_INDICATOR_WITH_LEADING_ICON) {
                                        stateOfViewTypeForTab == ViewTypeForTab.TAB_TYPE_CUSTOM_INDICATOR_WITH_LEADING_ICON
                                    } else if (type == ViewTypeForTab.TAB_TYPE_CUSTOM_INDICATOR_WITHOUT_ICON) {
                                        stateOfViewTypeForTab == ViewTypeForTab.TAB_TYPE_CUSTOM_INDICATOR_WITHOUT_ICON
                                    } else if (type == ViewTypeForTab.TAB_TYPE_DEFAULT_INDICATOR_WITH_ICON) {
                                        stateOfViewTypeForTab == ViewTypeForTab.TAB_TYPE_DEFAULT_INDICATOR_WITH_ICON
                                    } else if (type == ViewTypeForTab.TAB_TYPE_DEFAULT_INDICATOR_WITH_LEADING_ICON) {
                                        stateOfViewTypeForTab == ViewTypeForTab.TAB_TYPE_DEFAULT_INDICATOR_WITH_LEADING_ICON
                                    } else {
                                        stateOfViewTypeForTab == ViewTypeForTab.TAB_TYPE_DEFAULT_INDICATOR_WITHOUT_ICON
                                    },
                                    onClick = {
                                        stateOfViewTypeForTab = type
                                        Log.d("selected", type.name)
                                    },
                                    role = Role.RadioButton
                                )
                                .padding(top = 5.dp)
                        ) {
                            RadioButton(
                                selected = if (type == ViewTypeForTab.TAB_TYPE_CUSTOM_INDICATOR_WITH_ICON) {
                                    stateOfViewTypeForTab == ViewTypeForTab.TAB_TYPE_CUSTOM_INDICATOR_WITH_ICON
                                } else if (type == ViewTypeForTab.TAB_TYPE_CUSTOM_INDICATOR_WITH_LEADING_ICON) {
                                    stateOfViewTypeForTab == ViewTypeForTab.TAB_TYPE_CUSTOM_INDICATOR_WITH_LEADING_ICON
                                } else if (type == ViewTypeForTab.TAB_TYPE_CUSTOM_INDICATOR_WITHOUT_ICON) {
                                    stateOfViewTypeForTab == ViewTypeForTab.TAB_TYPE_CUSTOM_INDICATOR_WITHOUT_ICON
                                } else if (type == ViewTypeForTab.TAB_TYPE_DEFAULT_INDICATOR_WITH_ICON) {
                                    stateOfViewTypeForTab == ViewTypeForTab.TAB_TYPE_DEFAULT_INDICATOR_WITH_ICON
                                } else if (type == ViewTypeForTab.TAB_TYPE_DEFAULT_INDICATOR_WITH_LEADING_ICON) {
                                    stateOfViewTypeForTab == ViewTypeForTab.TAB_TYPE_DEFAULT_INDICATOR_WITH_LEADING_ICON
                                } else {
                                    stateOfViewTypeForTab == ViewTypeForTab.TAB_TYPE_DEFAULT_INDICATOR_WITHOUT_ICON
                                },
                                onClick = null
                            )
                            androidx.compose.material3.Text(
                                text = stringResource(id = type.resId),
                                style = MaterialTheme.typography.bodyMedium,
                                modifier = Modifier.padding(start = 4.dp)
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                Button(modifier = Modifier
                    .padding(
                        10.dp,
                        5.dp,
                        10.dp,
                        5.dp
                    )
                    .align(alignment = Alignment.CenterHorizontally),
                    onClick = { saveSettings(stateOfViewTypeForTab) }) {
                    androidx.compose.material3.Text(
                        text = stringResource(id = com.mustafacan.ui_common.R.string.settings_apply),
                        color = Color.White
                    )
                }


            }
        }
    }

}