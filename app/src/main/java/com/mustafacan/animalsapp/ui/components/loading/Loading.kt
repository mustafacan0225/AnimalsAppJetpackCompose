package com.mustafacan.animalsapp.ui.components.loading

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.mustafacan.animalsapp.ui.components.shimmer.ShimmerEffect

@Composable
fun LoadingScreen() {
    LazyColumn {
        repeat(10) {
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .padding(start = 20.dp, top = 10.dp),
                    horizontalArrangement = Arrangement.spacedBy(
                        space = 16.dp,
                        alignment = Alignment.CenterHorizontally
                    ),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    //Spacer(modifier = Modifier.width(16.dp))
                    ShimmerEffect(
                        Modifier
                            .size(80.dp)
                            .background(Color.LightGray, shape = RoundedCornerShape(50))
                    )

                    Column(
                        modifier = Modifier
                            .weight(0.7f)
                            .height(60.dp)
                            .padding(end = 20.dp)
                    ) {
                        ShimmerEffect(
                            Modifier
                                .width(320.dp)
                                .height(20.dp)
                                .clip(RoundedCornerShape(20.dp))
                                .background(Color.LightGray)
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        ShimmerEffect(
                            Modifier
                                .width(100.dp)
                                .height(20.dp)
                                .clip(RoundedCornerShape(20.dp))
                                .background(Color.LightGray)
                        )

                    }
                }
            }

        }
    }

}