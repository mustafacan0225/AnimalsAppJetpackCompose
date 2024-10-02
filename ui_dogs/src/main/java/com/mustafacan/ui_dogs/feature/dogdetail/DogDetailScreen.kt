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
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController

import com.mustafacan.domain.model.dogs.Dog
import com.mustafacan.ui_common.components.image.CircleImage
import com.mustafacan.ui_common.components.lottie.LikeAnimation
import com.mustafacan.ui_common.components.tab.ScrollableTabRowWithCustomIndicator
import com.mustafacan.ui_common.components.tab.ScrollableTabRowWithDefaultIndicator
import com.mustafacan.ui_common.components.toolbar.Toolbar
import com.mustafacan.ui_common.components.toolbar.ToolbarAction
import com.mustafacan.ui_common.model.enums.ViewTypeForTab
import com.mustafacan.ui_dogs.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DogDetailScreen(navController: NavController, viewModel: DogDetailViewModel) {
    println("dog id: " + viewModel.dog?.name)
    val state = viewModel.state.collectAsStateWithLifecycle()
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

        if (state.value.initialLoaded) {
            DogImage(updateIsFavorite = {
                viewModel.updateIsFavorite()
                if (!state.value.dog!!.isFavorite!!) {
                    viewModel.showLikeAnimation()
                } else {
                    viewModel.hideLikeAnimation()
                }
            }, dog = state.value.dog?: viewModel.dog, isSelectedFavIcon = state.value.isSelectedFavIcon, animationVisibility = state.value.likeAnimationVisibility)
            CreateTabBar(state, onClickTab = {
                viewModel.onClickTab(it)
            })
            DogDetailContent(uiState = state, viewModel)

            if (state.value.showSettings) {
                Dialog(onDismissRequest = { viewModel.closeSettings() }
                ) {
                    SettingsDialog(state.value.currentViewTypeForTab!!, saveSettings = {
                        viewModel.settingsUpdated(it)
                    }, onDismiss = {
                        viewModel.closeSettings()
                    })
                }
            }
        }



    }
}

@Composable
fun DogImage(updateIsFavorite: () -> Unit, dog: Dog, isSelectedFavIcon: Boolean, animationVisibility: Boolean) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = colorResource(id = R.color.statusbar_color)),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        ConstraintLayout(modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()) {
            val (image, favoriteIcon, animationIcon) = createRefs()

            CircleImage(
                url = "https://cdn.pixabay.com/photo/2016/02/19/15/46/labrador-retriever-1210559_1280.jpg",
                modifier = Modifier
                    .size(100.dp)
                    .clip(CircleShape)
                    .constrainAs(image) {
                        centerHorizontallyTo(parent)
                    }
            )

            Icon(imageVector = if (isSelectedFavIcon) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                contentDescription = "favorite",
                tint = if (isSelectedFavIcon) Color.Red else Color.White,
                modifier = Modifier
                    .size(50.dp)
                    .constrainAs(favoriteIcon) {
                        centerHorizontallyTo(parent)
                        top.linkTo(image.bottom)
                        bottom.linkTo(image.bottom)
                    }
                    .clickable {
                        updateIsFavorite()
                    }

            )

            if (dog.isFavorite?: false) {
                LikeAnimation(modifier = Modifier
                    .size(50.dp)
                    .constrainAs(animationIcon) {
                        centerHorizontallyTo(parent)
                        top.linkTo(image.bottom)
                        bottom.linkTo(image.bottom)
                    })
            }

        }





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
                state.value.currentViewTypeForTab!!
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
                state.value.currentViewTypeForTab!!
            )
        }

    }


}


@Composable
fun DogDetailContent(
    uiState: State<DogDetailScreenReducer.DogDetailScreenState>,
    viewModel: DogDetailViewModel
) {
    uiState.value.pagerState?.let {
        HorizontalPager(state = it, modifier = Modifier.fillMaxSize()) { index ->
            when (index) {
                0 -> {
                    GeneralContent((uiState.value.dog?: viewModel.dog)!!.description?: "")
                }

                1 -> {
                    InfoContent((uiState.value.dog?: viewModel.dog)!!)
                }

                2 -> {
                    TemperamentContent(viewModel.getTemperament(uiState.value.dog?: viewModel.dog!!))
                }

                3 -> {
                    ColorsContent((uiState.value.dog?: viewModel.dog)!!.colors)
                }

            }

        }
    }

}

@Composable
fun GeneralContent(description: String) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 15.dp, end = 15.dp, top = 10.dp, bottom = 10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            text = stringResource(id = R.string.description)
        )
        Text(fontSize = 14.sp, text = description, textAlign = TextAlign.Center)
    }
}

@Composable
fun InfoContent(dog: Dog) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            text = stringResource(id = R.string.name)
        )
        Text(fontSize = 14.sp, text = dog.name ?: "")
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            text = stringResource(id = R.string.breed_group)
        )
        Text(fontSize = 14.sp, text = dog.breedGroup ?: "")
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            text = stringResource(id = R.string.origin)
        )
        Text(fontSize = 14.sp, text = dog.origin ?: "")
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            text = stringResource(id = R.string.lifespan)
        )
        Text(fontSize = 14.sp, text = dog.lifespan ?: "")
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            text = stringResource(id = R.string.size)
        )
        Text(fontSize = 14.sp, text = dog.size ?: "")
    }
}

@Composable
fun TemperamentContent(temperamentList: List<String>) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            fontSize = 14.sp,
            text = stringResource(id = R.string.temperament_desc)
        )
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            text = stringResource(id = R.string.tab_temperament)
        )
        temperamentList.forEach {
            Spacer(modifier = Modifier.height(10.dp))
            Text(fontSize = 14.sp, text = it, textAlign = TextAlign.Center)
        }


    }
}

@Composable
fun ColorsContent(colors: List<String>) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            fontSize = 14.sp,
            text = stringResource(id = R.string.colors_desc)
        )
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            text = stringResource(id = R.string.tab_colors)
        )
        colors.forEach {
            Spacer(modifier = Modifier.height(10.dp))
            Text(fontSize = 14.sp, text = it, textAlign = TextAlign.Center)
        }


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
