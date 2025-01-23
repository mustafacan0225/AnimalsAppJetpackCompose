package com.mustafacan.cats.feature.catdetail.pager

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.mustafacan.domain.model.cats.Cat
import com.mustafacan.cats.feature.catdetail.tabs.ColorsTabContent
import com.mustafacan.cats.feature.catdetail.tabs.GeneralTabContent
import com.mustafacan.cats.feature.catdetail.tabs.InfoTabContent
import com.mustafacan.cats.feature.catdetail.tabs.TemperamentTabContent

@Composable
fun PagerCatDetail(
    pagerState: PagerState,
    cat: Cat
) {
    HorizontalPager(state = pagerState, modifier = Modifier.fillMaxSize()) { index ->
        when (index) {
            0 -> {
                GeneralTabContent(cat.description?: "")
            }

            1 -> {
                InfoTabContent(cat)
            }

            2 -> {
                TemperamentTabContent(cat)
            }

            3 -> {
                ColorsTabContent(cat)
            }

        }

    }
}