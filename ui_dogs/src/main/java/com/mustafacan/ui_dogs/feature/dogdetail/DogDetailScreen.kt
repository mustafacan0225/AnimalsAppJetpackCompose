package com.mustafacan.ui_dogs.feature.dogdetail

import android.util.Log
import androidx.compose.foundation.BorderStroke
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
import androidx.compose.runtime.MutableState
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
import com.mustafacan.ui_common.components.image.ImageViewer
import com.mustafacan.ui_common.components.image.ShowImage
import com.mustafacan.ui_common.components.lottie.LikeAnimation
import com.mustafacan.ui_common.components.settings.SettingsContent
import com.mustafacan.ui_common.components.settings.SettingsDialog
import com.mustafacan.ui_common.components.tab.ScrollableTabRowWithCustomIndicator
import com.mustafacan.ui_common.components.tab.ScrollableTabRowWithDefaultIndicator
import com.mustafacan.ui_common.components.toolbar.Toolbar
import com.mustafacan.ui_common.components.toolbar.ToolbarAction
import com.mustafacan.ui_common.model.enums.ViewTypeForTab
import com.mustafacan.ui_dogs.R
import com.mustafacan.ui_dogs.feature.dogdetail.pager.PagerDogDetail

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
            }, dog = state.value.dog?: viewModel.dog, isSelectedFavIcon = state.value.isSelectedFavIcon)
            CreateTabBar(state, onClickTab = {
                viewModel.onClickTab(it)
            })

            state.value.pagerState?.let {
                PagerDogDetail(pagerState = it, dog = state.value.dog?: viewModel.dog)
            }

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
        
        ImageViewer(imageUrl = (state.value.dog?.image?: viewModel.dog?.image)!!)


    }
}

@Composable
fun DogImage(updateIsFavorite: () -> Unit, dog: Dog, isSelectedFavIcon: Boolean) {
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
                url = dog.image?: "https://cdn.pixabay.com/photo/2016/02/19/15/46/labrador-retriever-1210559_1280.jpg",
                modifier = Modifier
                    .size(100.dp)
                    .clip(CircleShape)
                    .constrainAs(image) {
                        centerHorizontallyTo(parent)
                    }
                    .clickable { }
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

            if (isSelectedFavIcon) {
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




