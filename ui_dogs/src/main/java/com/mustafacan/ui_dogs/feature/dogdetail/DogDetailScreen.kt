package com.mustafacan.ui_dogs.feature.dogdetail

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController

import com.mustafacan.domain.model.dogs.Dog
import com.mustafacan.ui_common.components.image.CircleImage
import com.mustafacan.ui_common.components.tab.ScrollableTabRowWithCustomIndicator
import com.mustafacan.ui_common.components.tab.ScrollableTabRowWithDefaultIndicator
import com.mustafacan.ui_common.components.toolbar.Toolbar
import com.mustafacan.ui_common.components.toolbar.ToolbarAction
import com.mustafacan.ui_common.model.enums.ViewTypeForTab
import com.mustafacan.ui_common.util.rememberFlowWithLifecycle
import com.mustafacan.ui_dogs.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DogDetailScreen(navController: NavController, viewModel: DogDetailViewModel) {
    println("dog id: " + viewModel.dog?.name)
    val state = viewModel.state.collectAsStateWithLifecycle()
    val effect = rememberFlowWithLifecycle(viewModel.effect)
    val scope = rememberCoroutineScope()
    val pagerState = androidx.compose.foundation.pager.rememberPagerState(initialPage = 0) { 4 }

    val actionListForToolbar =
        listOf(ToolbarAction.OpenSettings(action = { viewModel.navigateToSettings() }))

    LaunchedEffect(key1 = viewModel.dog!!.id) {
        viewModel.load(pagerState, scope)
    }

    Column(
        Modifier
            .fillMaxWidth()
            .padding(0.dp)
    ) {

        Toolbar(onBackPressed = { navController.popBackStack() }, actionList = actionListForToolbar)
        DogImage()
        CreateTabBar(state, onClickTab = {
            viewModel.onClickTab(it)
        })
        DogDetailContent(uiState = state)


        if (state.value.showSettings) {
            Dialog(onDismissRequest = { viewModel.closeSettings() }
            ) {
                SettingsDialog(state.value.currentViewTypeForTab, saveSettings = {
                    viewModel.settingsUpdated(it)
                }, onDismiss = {
                    viewModel.closeSettings()
                })
            }
        }

    }
}

@Composable
fun DogImage() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = colorResource(id = R.color.statusbar_color)),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        CircleImage(
            url = "https://cdn.pixabay.com/photo/2016/02/19/15/46/labrador-retriever-1210559_1280.jpg",
            modifier = Modifier
                .size(100.dp)
                .clip(CircleShape)
        )


    }
}

@Composable
fun CreateTabBar(
    state: State<DogDetailScreenReducer.DogDetailScreenState>,
    onClickTab: (index: Int) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(colorResource(id = R.color.statusbar_color))
            .padding(10.dp)
    ) {
        if (state.value.currentViewTypeForTab == ViewTypeForTab.TAB_TYPE_CUSTOM_INDICATOR_WITH_ICON
            || state.value.currentViewTypeForTab == ViewTypeForTab.TAB_TYPE_CUSTOM_INDICATOR_WITH_LEADING_ICON
            || state.value.currentViewTypeForTab == ViewTypeForTab.TAB_TYPE_CUSTOM_INDICATOR_WITHOUT_ICON
        ) {

            ScrollableTabRowWithCustomIndicator(
                pagerState = state.value.pagerState,
                tabList = state.value.tabList,
                onClickTab = {
                    onClickTab(it)
                },
                state.value.currentViewTypeForTab
            )
        }

        if (state.value.currentViewTypeForTab == ViewTypeForTab.TAB_TYPE_DEFAULT_INDICATOR_WITH_ICON
            || state.value.currentViewTypeForTab == ViewTypeForTab.TAB_TYPE_DEFAULT_INDICATOR_WITH_LEADING_ICON
            || state.value.currentViewTypeForTab == ViewTypeForTab.TAB_TYPE_DEFAULT_INDICATOR_WITHOUT_ICON
        ) {
            ScrollableTabRowWithDefaultIndicator(
                state.value.pagerState,
                state.value.tabList,
                onClickTab = {
                    onClickTab(it)
                },
                state.value.currentViewTypeForTab
            )
        }

    }


}




@Composable
fun DogDetailContent(uiState: State<DogDetailScreenReducer.DogDetailScreenState>) {
    uiState.value.pagerState?.let {
        HorizontalPager(state = it, modifier = Modifier.fillMaxSize()) { index ->
            when (index) {
                0 -> {
                    GeneralContent(uiState.value.dog!!)
                }

                1 -> {
                    Text(text = stringResource(id = uiState.value?.tabList!![index].first))
                }

                2 -> {
                    Text(text = stringResource(id = uiState.value?.tabList!![index].first))
                }

                3 -> {
                    Text(text = stringResource(id = uiState.value?.tabList!![index].first))
                }

            }

        }
    }

}

@Composable
fun GeneralContent(dog: Dog) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            text = stringResource(id = R.string.name)
        )
        Text(fontSize = 18.sp, text = dog.name ?: "")
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            text = stringResource(id = R.string.breed_group)
        )
        Text(fontSize = 18.sp, text = dog.breedGroup ?: "")
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            text = stringResource(id = R.string.origin)
        )
        Text(fontSize = 18.sp, text = dog.origin ?: "")
    }
}

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
