package com.mustafacan.core.components.tab

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.Interaction
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.LeadingIconTab
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TabPosition
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.mustafacan.core.R
import com.mustafacan.core.model.enums.ViewTypeForTab
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

@Composable
fun TabWithIcon(title: String, iconResId: Int, currentPage: Int, tabIndex: Int, onClickTab: (index: Int) -> Unit) {
    Tab(
        onClick = { onClickTab(tabIndex) },
        text = { Text(title, fontWeight = FontWeight.Bold) },
        selected = currentPage == tabIndex,
        interactionSource = DisabledInteractionSource(),
        modifier = Modifier
            .clip(shape = CircleShape)
            .zIndex(2f),
        icon = {
            Icon(
                modifier = Modifier.size(25.dp),
                painter = painterResource(id = iconResId),
                contentDescription = title
            )
        }

    )
}

@Composable
fun TabWithLeadingIcon(title: String, iconResId: Int, currentPage: Int, tabIndex: Int, onClickTab: (index: Int) -> Unit) {
    LeadingIconTab(
        onClick = { onClickTab(tabIndex) },
        text = { Text(title, fontWeight = FontWeight.Bold) },
        selected = currentPage == tabIndex,
        interactionSource = DisabledInteractionSource(),
        modifier = Modifier
            .clip(shape = CircleShape)
            .zIndex(2f),
        icon = {
            Icon(
                modifier = Modifier.size(25.dp),
                painter = painterResource(id = iconResId),
                contentDescription = title
            )
        }

    )
}

@Composable
fun TabWithOutIcon(title: String, currentPage: Int, tabIndex: Int, onClickTab: (index: Int) -> Unit) {
    Tab(
        onClick = { onClickTab(tabIndex) },
        text = { Text(title, fontWeight = FontWeight.Bold) },
        selected = currentPage == tabIndex,
        interactionSource = DisabledInteractionSource(),
        modifier = Modifier
            .clip(shape = CircleShape)
            .zIndex(2f)

    )
}

@Composable
fun ScrollableTabRowWithCustomIndicator(pagerState: PagerState?, tabList: List<Pair<Int,Int?>>?,
                     onClickTab: (index: Int) -> Unit, viewType: ViewTypeForTab) {
    pagerState?.let { pager ->
        tabList?.let { tabItems ->

            val indicator = @Composable { tabPositions: List<TabPosition> ->
                CustomTabIndicator(
                    Modifier.TabIndicatorOffset(tabPositions[pager.currentPage])
                )
            }

            val modifier = if (viewType == ViewTypeForTab.TAB_TYPE_CUSTOM_INDICATOR_WITHOUT_ICON) {
                Modifier
                    .wrapContentHeight()
                    .border(BorderStroke(1.dp, Color.White), shape = CircleShape)
                    .padding(10.dp)
            } else {
                Modifier
                    .wrapContentHeight()
                    .padding(top = 10.dp, bottom = 10.dp)
            }

            ScrollableTabRow(
                selectedTabIndex = pager.currentPage,
                indicator = indicator,
                containerColor = colorResource(id = R.color.statusbar_color),
                contentColor = Color.White,
                divider = {},
                modifier = modifier,
                edgePadding = 2.dp
            ) {
                tabItems.forEachIndexed { index, tab ->
                    if (viewType == ViewTypeForTab.TAB_TYPE_CUSTOM_INDICATOR_WITH_ICON) {
                        TabWithIcon(title = stringResource(id = tab.first), iconResId = tab.second?: 0, currentPage = pager.currentPage, tabIndex = index) {
                            onClickTab(it)
                        }
                    } else if (viewType == ViewTypeForTab.TAB_TYPE_CUSTOM_INDICATOR_WITH_LEADING_ICON) {
                        TabWithLeadingIcon(title = stringResource(id = tab.first), iconResId = tab.second?: 0, currentPage = pager.currentPage, tabIndex = index) {
                            onClickTab(it)
                        }
                    } else {
                        TabWithOutIcon(title = stringResource(id = tab.first), currentPage = pager.currentPage, tabIndex = index) {
                            onClickTab(it)
                        }
                    }
                }
            }
        }
    }


}

class DisabledInteractionSource : MutableInteractionSource {
    override val interactions: Flow<Interaction> = emptyFlow()
    override suspend fun emit(interaction: Interaction) {}
    override fun tryEmit(interaction: Interaction) = true
}