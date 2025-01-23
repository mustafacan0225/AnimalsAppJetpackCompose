package com.mustafacan.ui_common.components.tab

import android.util.Log
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.PagerState
import androidx.compose.material3.Icon
import androidx.compose.material3.LeadingIconTab
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.mustafacan.ui_common.R
import com.mustafacan.ui_common.model.enums.ViewTypeForTab

@Composable
fun DefaultTabWithIcon(
    title: String,
    iconResId: Int,
    currentPage: Int,
    tabIndex: Int,
    onClickTab: (index: Int) -> Unit
) {
    Tab(
        selected = currentPage == tabIndex,
        selectedContentColor = Color.White,
        unselectedContentColor = Color.Gray,
        onClick = {
            onClickTab(tabIndex)
        },
        text = { Text(text = title) },
        icon = {
            Icon(
                modifier = Modifier.size(30.dp),
                painter = painterResource(id = iconResId),
                contentDescription = title
            )
        },

        )
}

@Composable
fun DefaultTabWithLeadingIcon(
    title: String,
    iconResId: Int,
    currentPage: Int,
    tabIndex: Int,
    onClickTab: (index: Int) -> Unit
) {
    LeadingIconTab(
        selected = currentPage == tabIndex,
        selectedContentColor = Color.White,
        unselectedContentColor = Color.Gray,
        onClick = {
            onClickTab(tabIndex)
        },
        text = { Text(text = title) },
        icon = {
            Icon(
                modifier = Modifier.size(30.dp),
                painter = painterResource(id = iconResId),
                contentDescription = title
            )
        })
}

@Composable
fun DefaultTabWithOutIcon(
    title: String,
    currentPage: Int,
    tabIndex: Int,
    onClickTab: (index: Int) -> Unit
) {
    Tab(
        selected = currentPage == tabIndex,
        selectedContentColor = Color.White,
        unselectedContentColor = Color.Gray,
        onClick = {
            onClickTab(tabIndex)
        },
        text = { Text(text = title) })
}

@Composable
fun ScrollableTabRowWithDefaultIndicator(
    pagerState: PagerState?,
    tabList: List<Pair<Int, Int?>>?,
    onClickTab: (index: Int) -> Unit,
    viewTypeForTab: ViewTypeForTab
) {
    Log.d("pagerstate", "current page " + pagerState?.currentPage)
    androidx.compose.material3.ScrollableTabRow(
        selectedTabIndex = pagerState?.currentPage ?: 0,
        modifier = Modifier.fillMaxWidth(),
        containerColor = colorResource(id = R.color.statusbar_color),
        contentColor = Color.Gray,
        indicator = {},
        edgePadding = 2.dp,
        divider = {}

    ) {
        pagerState?.let { pager ->
            tabList?.let { tabItems ->
                tabItems.forEachIndexed { index, tab ->
                    if (viewTypeForTab == ViewTypeForTab.TAB_TYPE_DEFAULT_INDICATOR_WITH_ICON) {
                        DefaultTabWithIcon(title = stringResource(id = tab.first),
                            iconResId = tab.second ?: 0,
                            currentPage = pagerState.currentPage,
                            tabIndex = index,
                            onClickTab = { onClickTab(index) })
                    } else if (viewTypeForTab == ViewTypeForTab.TAB_TYPE_DEFAULT_INDICATOR_WITH_LEADING_ICON) {
                        DefaultTabWithLeadingIcon(title = stringResource(id = tab.first),
                            iconResId = tab.second ?: 0,
                            currentPage = pagerState.currentPage,
                            tabIndex = index,
                            onClickTab = { onClickTab(index) })
                    } else {
                        DefaultTabWithOutIcon(title = stringResource(id = tab.first),
                            currentPage = pagerState.currentPage,
                            tabIndex = index,
                            onClickTab = { onClickTab(index) })
                    }

                }
            }
        }

    }

}