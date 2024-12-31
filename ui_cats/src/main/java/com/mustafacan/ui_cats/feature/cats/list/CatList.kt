package com.mustafacan.ui_cats.feature.cats.list

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.constraintlayout.compose.atLeast
import androidx.constraintlayout.compose.atMost
import com.mustafacan.domain.model.cats.Cat
import com.mustafacan.ui_cats.R
import com.mustafacan.ui_common.components.image.CircleImage
import com.mustafacan.ui_common.components.lottie.LikeAnimation
import com.mustafacan.ui_common.model.enums.ViewTypeForList
import com.mustafacan.ui_cats.feature.cats.CatsScreenUiStateManager

@Composable
fun CatList(
    uiState: State<CatsScreenUiStateManager.CatsScreenState>,
    clickedItem: (cat: Cat) -> Unit,
    addFavorite: (cat: Cat) -> Unit,
    deleteFavorite: (cat: Cat) -> Unit,
    showBigImage: (cat: Cat) -> Unit
) {
    if (uiState.value.viewTypeForList == ViewTypeForList.LAZY_VERTICAL_GRID) {
        CatListForLazyVerticalGrid(
            catList = uiState.value.cats!!,
            clickedItem = { cat -> clickedItem(cat) },
            addFavorite = { cat -> addFavorite(cat.copy(isFavorite = true)) },
            deleteFavorite = { cat -> deleteFavorite(cat) },
            showBigImage = { cat -> showBigImage(cat) })
    } else if (uiState.value.viewTypeForList == ViewTypeForList.LAZY_COLUMN) {
        CatListForLazyColumn(
            catList = uiState.value.cats!!,
            clickedItem = { cat -> clickedItem(cat) },
            addFavorite = { cat -> addFavorite(cat.copy(isFavorite = true)) },
            deleteFavorite = { cat -> deleteFavorite(cat) },
            showBigImage = { cat -> showBigImage(cat) })
    }
}

@Composable
fun CatListForLazyColumn(
    catList: List<Cat>,
    clickedItem: (cat: Cat) -> Unit,
    addFavorite: (cat: Cat) -> Unit,
    deleteFavorite: (cat: Cat) -> Unit,
    showBigImage: (cat: Cat) -> Unit
) {
    LazyColumn(
        Modifier
            .fillMaxSize()
            .padding(10.dp)
    ) {
        items(catList) { cat ->

            Card(
                Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
                    .clickable { clickedItem(cat) },
                colors = CardDefaults.cardColors(containerColor = Color.White),
                border = BorderStroke(
                    1.dp,
                    if (cat.isFavorite
                            ?: false
                    ) colorResource(id = R.color.indicator_color) else Color.White
                ),
                shape = RoundedCornerShape(12.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
            ) {

                ConstraintLayout(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .padding(10.dp)
                ) {
                    val (image, catInfoLayaout, favoriteIcon) = createRefs()

                    Card(
                        modifier = Modifier
                            .width(80.dp)
                            .height(80.dp)
                            .constrainAs(image) {
                                centerVerticallyTo(parent)
                                start.linkTo(parent.start)
                                top.linkTo(parent.top)
                                bottom.linkTo(parent.bottom)
                            },
                        colors = CardDefaults.cardColors(
                            containerColor = Color.White,
                        )
                    ) {
                        CircleImage(
                            url = cat.image
                                ?: "https://cdn.pixabay.com/photo/2023/09/06/17/03/maine-coon-8237571_1280.jpg",
                            modifier = Modifier
                                .size(80.dp)
                                .clip(CircleShape)
                                .clickable { showBigImage(cat) }
                        )
                    }

                    Column(modifier = Modifier.constrainAs(catInfoLayaout) {
                        centerVerticallyTo(parent)
                        start.linkTo(image.end, margin = 10.dp)
                        end.linkTo(favoriteIcon.start, margin = 10.dp)
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                        width = Dimension.fillToConstraints
                    }) {
                        Text(
                            text = cat.name ?: "",
                            fontWeight = FontWeight.Black
                        )

                        Text(
                            modifier = Modifier.padding(top = 5.dp, bottom = 5.dp),
                            text = cat.origin ?: ""
                        )

                        Text(text = cat.temperament ?: "", maxLines = 1)
                    }

                    IconButton(onClick = {
                        if (cat.isFavorite ?: false)
                            deleteFavorite(cat)
                        else
                            addFavorite(cat)
                    }, modifier = Modifier.constrainAs(favoriteIcon) {
                        centerVerticallyTo(parent)
                        end.linkTo(parent.end)
                    }) {
                        if (cat.isFavorite ?: false) {
                            LikeAnimation(modifier = Modifier.size(50.dp))
                        } else {
                            Icon(
                                imageVector = Icons.Default.FavoriteBorder,
                                contentDescription = "favorite",
                                tint = Color.Black

                            )
                        }
                        /*Icon(
                            imageVector = if (cat.isFavorite
                                    ?: false
                            ) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                            contentDescription = "favorite",
                            tint = if (cat.isFavorite ?: false) Color.Red else Color.Black

                        )*/
                    }

                }

            }
        }
    }

}

@Composable
fun CatListForLazyVerticalGrid(
    catList: List<Cat>,
    clickedItem: (cat: Cat) -> Unit,
    addFavorite: (cat: Cat) -> Unit,
    deleteFavorite: (cat: Cat) -> Unit,
    showBigImage: (cat: Cat) -> Unit
) {
    LazyVerticalGrid(
        modifier =
        Modifier
            .fillMaxSize()
            .padding(10.dp),
        columns = GridCells.Fixed(2)
    ) {
        items(catList) { cat ->
            ConstraintLayout(modifier = Modifier.padding(start = 10.dp, end = 10.dp, top = 10.dp)) {
                val (card, favIcon) = createRefs()

                Card(
                    Modifier
                        .constrainAs(card) {
                            width = Dimension.fillToConstraints
                            height = Dimension.wrapContent
                        }
                        .clickable { clickedItem(cat) },
                    colors = CardDefaults.cardColors(
                        containerColor = Color.White,
                    ),
                    border = BorderStroke(
                        1.dp,
                        if (cat.isFavorite
                                ?: false
                        ) colorResource(id = R.color.indicator_color) else Color.White
                    ),
                    shape = RoundedCornerShape(12.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = cat.name ?: "",
                            fontWeight = FontWeight.Black,
                            textAlign = TextAlign.Center,
                            maxLines = 1,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 10.dp, start = 10.dp, end = 10.dp)
                        )
                        Spacer(modifier = Modifier.height(5.dp))
                        Card(
                            modifier = Modifier
                                .width(80.dp)
                                .height(80.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = Color.White,
                            ),
                            shape = CircleShape
                        ) {
                            CircleImage(
                                url = cat.image
                                    ?: "https://cdn.pixabay.com/photo/2023/09/06/17/03/maine-coon-8237571_1280.jpg",
                                modifier = Modifier
                                    .size(80.dp)
                                    .clip(CircleShape)
                                    .clickable { showBigImage(cat) }
                            )
                        }
                        Text(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 5.dp),
                            text = cat.origin ?: "",
                            textAlign = TextAlign.Center,
                            maxLines = 1,
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(
                            modifier = Modifier
                                .background(
                                    colorResource(id = R.color.indicator_color),
                                    RoundedCornerShape(50.dp)
                                )
                                .padding(start = 10.dp, end = 10.dp, top = 5.dp, bottom = 5.dp)
                                .clickable { clickedItem(cat) },
                            text = stringResource(id = R.string.detail),
                            color = Color.White
                        )

                        Spacer(modifier = Modifier.height(30.dp))
                    }
                }

                IconButton(onClick = {
                    if (cat.isFavorite ?: false)
                        deleteFavorite(cat)
                    else
                        addFavorite(cat)
                }, modifier = Modifier.constrainAs(favIcon) {
                    width = Dimension.fillToConstraints.atMost(80.dp).atLeast(80.dp)
                    height = Dimension.fillToConstraints.atMost(80.dp).atLeast(80.dp)
                    centerHorizontallyTo(parent)
                    top.linkTo(card.bottom)
                    bottom.linkTo(card.bottom)

                }) {
                    if (cat.isFavorite ?: false) {
                        LikeAnimation(modifier = Modifier.size(80.dp))
                    } else {
                        Icon(
                            imageVector = Icons.Default.FavoriteBorder,
                            contentDescription = "favorite",
                            tint = Color.Black,
                            modifier = Modifier.size(40.dp)
                        )
                    }
                }
            }

        }
    }

}