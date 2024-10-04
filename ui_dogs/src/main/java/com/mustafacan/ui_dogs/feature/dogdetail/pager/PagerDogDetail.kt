package com.mustafacan.ui_dogs.feature.dogdetail.pager

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.mustafacan.domain.model.dogs.Dog
import com.mustafacan.ui_dogs.feature.dogdetail.tabs.ColorsTabContent
import com.mustafacan.ui_dogs.feature.dogdetail.tabs.GeneralTabContent
import com.mustafacan.ui_dogs.feature.dogdetail.tabs.InfoTabContent
import com.mustafacan.ui_dogs.feature.dogdetail.tabs.TemperamentTabContent

@Composable
fun PagerDogDetail(
    pagerState: PagerState,
    dog: Dog
) {
    HorizontalPager(state = pagerState, modifier = Modifier.fillMaxSize()) { index ->
        when (index) {
            0 -> {
                GeneralTabContent(dog.description?: "")
            }

            1 -> {
                InfoTabContent(dog)
            }

            2 -> {
                TemperamentTabContent(dog)
            }

            3 -> {
                ColorsTabContent(dog)
            }

        }

    }
}