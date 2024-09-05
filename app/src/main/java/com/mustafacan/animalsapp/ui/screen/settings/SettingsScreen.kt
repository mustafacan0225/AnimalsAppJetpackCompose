package com.mustafacan.animalsapp.ui.screen.settings

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
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
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
import androidx.compose.ui.window.Dialog
import com.mustafacan.animalsapp.R
import com.mustafacan.animalsapp.ui.model.enums.ViewTypeForList
import com.mustafacan.animalsapp.ui.model.enums.ViewTypeForSettings
import com.mustafacan.animalsapp.ui.screen.dogs.DogsScreenReducer

@Composable
fun SettingsScreenWithPopup(
    currentViewTypeForList: ViewTypeForList,
    currentViewTypeForSettings: ViewTypeForSettings,
    saveSettings: (ViewTypeForList, ViewTypeForSettings) -> Unit,
    onDismiss: () -> Unit
) {
    Dialog(onDismissRequest = { onDismiss() }
    ) {
        SettingsContent(currentViewTypeForList, currentViewTypeForSettings, saveSettings, onDismiss)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreenWithBottomSheet(
    currentViewTypeForList: ViewTypeForList,
    currentViewTypeForSettings: ViewTypeForSettings,
    saveSettings: (ViewTypeForList, ViewTypeForSettings) -> Unit,
    onDismiss: () -> Unit
) {
    val modalBottomSheetState = rememberModalBottomSheetState()

    ModalBottomSheet(
        onDismissRequest = { onDismiss() },
        sheetState = modalBottomSheetState,
        dragHandle = { BottomSheetDefaults.DragHandle() },
    ) {
        SettingsContent(currentViewTypeForList, currentViewTypeForSettings, saveSettings, onDismiss)
    }
}

@Composable
fun SettingsContent(
    currentViewTypeForList: ViewTypeForList,
    currentViewTypeForSettings: ViewTypeForSettings,
    saveSettings: (ViewTypeForList, ViewTypeForSettings) -> Unit,
    onDismiss: () -> Unit
) {
    var stateOfViewTypeForList by remember { mutableStateOf(currentViewTypeForList) }
    val viewTypeForListOptions = listOf(ViewTypeForList.LAZY_COLUMN, ViewTypeForList.LAZY_VERTICAL_GRID)

    var stateOfViewTypeSettings by remember { mutableStateOf(currentViewTypeForSettings) }
    val viewTypeForSettingsOptions = listOf(ViewTypeForSettings.POPUP, ViewTypeForSettings.BOTTOM_SHEET)


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
                    Text(
                        text = "Settings",
                        style = TextStyle(
                            fontSize = 24.sp,
                            fontFamily = FontFamily.Default,
                            fontWeight = FontWeight.Bold
                        )
                    )

                    if (currentViewTypeForSettings == ViewTypeForSettings.POPUP) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "",
                            tint = Color.DarkGray,
                            modifier = Modifier
                                .width(30.dp)
                                .height(30.dp)
                                .clickable { onDismiss() }
                        )
                    }

                }

                Spacer(modifier = Modifier.height(20.dp))

                Text(
                    text = stringResource(id = R.string.view_mode_description),
                    fontWeight = FontWeight.Bold
                )

                Column(Modifier.selectableGroup()) {
                    viewTypeForListOptions.forEach { type ->
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .selectable(
                                    selected = if (type == ViewTypeForList.LAZY_COLUMN) {
                                        stateOfViewTypeForList == ViewTypeForList.LAZY_COLUMN
                                    } else {
                                        stateOfViewTypeForList == ViewTypeForList.LAZY_VERTICAL_GRID
                                    },
                                    onClick = { stateOfViewTypeForList = type
                                              Log.d("selected", type.name)},
                                    role = Role.RadioButton
                                )
                                .padding(top = 5.dp)
                        ) {
                            RadioButton(
                                selected = if (type == ViewTypeForList.LAZY_COLUMN) {
                                    stateOfViewTypeForList == ViewTypeForList.LAZY_COLUMN
                                } else {
                                    stateOfViewTypeForList == ViewTypeForList.LAZY_VERTICAL_GRID
                                },
                                onClick = null
                            )
                            Text(
                                text = stringResource(id = type.resId),
                                style = MaterialTheme.typography.bodyMedium,
                                modifier = Modifier.padding(start = 4.dp)
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = stringResource(id = R.string.view_mode_settings_description),
                    fontWeight = FontWeight.Bold
                )

                Column(Modifier.selectableGroup()) {
                    viewTypeForSettingsOptions.forEach { type ->
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .selectable(
                                    selected = if (type == ViewTypeForSettings.POPUP) {
                                        stateOfViewTypeSettings == ViewTypeForSettings.POPUP
                                    } else {
                                        stateOfViewTypeSettings == ViewTypeForSettings.BOTTOM_SHEET
                                    },
                                    onClick = { stateOfViewTypeSettings = type },
                                    role = Role.RadioButton
                                )
                                .padding(top = 5.dp)
                        ) {
                            RadioButton(
                                selected = if (type == ViewTypeForSettings.POPUP) {
                                    stateOfViewTypeSettings == ViewTypeForSettings.POPUP
                                } else {
                                    stateOfViewTypeSettings == ViewTypeForSettings.BOTTOM_SHEET
                                },
                                onClick = null
                            )
                            Text(
                                text = stringResource(id = type.resId),
                                style = MaterialTheme.typography.bodyMedium,
                                modifier = Modifier.padding(start = 4.dp)
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))
                Button(modifier = Modifier
                    .padding(10.dp, 5.dp, 10.dp, if (currentViewTypeForSettings == ViewTypeForSettings.POPUP) 5.dp else 25.dp)
                    .align(alignment = Alignment.CenterHorizontally),
                    onClick = { saveSettings(stateOfViewTypeForList, stateOfViewTypeSettings) }) {
                    Text(
                        text = stringResource(id = R.string.settings_apply),
                        color = Color.White
                    )
                }

            }
        }
    }
}