package com.mustafacan.ui_cats.feature.catdetail

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.mustafacan.domain.model.cats.Cat
import com.mustafacan.ui_cats.R
import com.mustafacan.ui_cats.feature.catdetail.pager.PagerCatDetail
import com.mustafacan.ui_common.components.image.CircleImage
import com.mustafacan.ui_common.components.image.ImageViewer
import com.mustafacan.ui_common.components.lottie.LikeAnimation
import com.mustafacan.ui_common.components.settings.SettingsDialog
import com.mustafacan.ui_common.components.tab.ScrollableTabRowWithCustomIndicator
import com.mustafacan.ui_common.components.tab.ScrollableTabRowWithDefaultIndicator
import com.mustafacan.ui_common.components.toolbar.Toolbar
import com.mustafacan.ui_common.components.toolbar.ToolbarAction
import com.mustafacan.ui_common.model.enums.ViewTypeForTab

@Composable
fun CatDetailScreen(navController: NavController, viewModel: CatDetailViewModel) {
    println("cat id: " + viewModel.cat?.name)
    val state = viewModel.state.collectAsStateWithLifecycle()
    val scope = rememberCoroutineScope()
    val pagerState = androidx.compose.foundation.pager.rememberPagerState(initialPage = 0) { 4 }

    val actionListForToolbar =
        listOf(ToolbarAction.OpenSettings(action = { viewModel.navigateToSettings() }))

    LaunchedEffect(key1 = viewModel.cat!!.id) {
        viewModel.load(pagerState, scope)
    }

    Column(
        Modifier
            .fillMaxWidth()
            .padding(0.dp)
    ) {

        Toolbar(onBackPressed = { navController.popBackStack() }, actionList = actionListForToolbar)

        if (state.value.initialLoaded) {
            CatImage(
                updateIsFavorite = {
                    viewModel.updateIsFavorite()
                },
                cat = state.value.cat ?: viewModel.cat,
                isSelectedFavIcon = state.value.isSelectedFavIcon,
                showBigImage = { viewModel.showBigImage() }
            )
            CreateTabBar(state, onClickTab = {
                viewModel.onClickTab(it)
            })

            state.value.pagerState?.let {
                PagerCatDetail(pagerState = it, cat = state.value.cat ?: viewModel.cat)
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

            if (state.value.showBigImage) {
                ImageViewer(
                    imageUrl = (state.value.cat?.image ?: viewModel.cat?.image) ?: "",
                    (state.value.cat?.name ?: viewModel.cat?.name) ?: "",
                    (state.value.cat?.temperament ?: viewModel.cat?.temperament) ?: "",
                    isSelectedFavIcon = state.value.cat?.isFavorite?: false,
                    onDismiss = { viewModel.closeBigImage() },
                    updateFavorite = { viewModel.updateIsFavorite() }
                )

            }
        }


    }
}

@Composable
fun CatImage(
    updateIsFavorite: () -> Unit,
    cat: Cat,
    isSelectedFavIcon: Boolean,
    showBigImage: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = colorResource(id = R.color.statusbar_color)),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        ConstraintLayout(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
        ) {
            val (image, favoriteIcon, animationIcon) = createRefs()

            CircleImage(
                url = cat.image
                    ?: "https://cdn.pixabay.com/photo/2023/09/06/17/03/maine-coon-8237571_1280.jpg",
                modifier = Modifier
                    .size(100.dp)
                    .clip(CircleShape)
                    .constrainAs(image) {
                        centerHorizontallyTo(parent)
                    }
                    .clickable { showBigImage() }
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
    state: State<CatDetailScreenUiStateManager.CatDetailScreenState>,
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
