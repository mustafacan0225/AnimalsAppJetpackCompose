package com.mustafacan.core.components.allfavoriteanimals

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.mustafacan.domain.model.AllFavoriteAnimals
import com.mustafacan.core.R
import com.mustafacan.core.components.image.CircleImage

@Composable
fun ShowAllFavoriteAnimals(
    allFavoriteAnimals: AllFavoriteAnimals,
    onDismiss: () -> Unit,
) {
    Dialog(onDismissRequest = { onDismiss() }) {
        Surface(
            shape = RoundedCornerShape(25.dp),
            border = BorderStroke(1.dp, colorResource(id = R.color.indicator_color))
        ) {

            Column(modifier = Modifier.fillMaxWidth().padding(start = 10.dp, top = 10.dp, bottom = 10.dp)) {
                Spacer(modifier = Modifier.height(10.dp))
                Text(text = "All Favorite Animals", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = colorResource(id = R.color.indicator_color))
                Spacer(modifier = Modifier.height(10.dp))

                if (allFavoriteAnimals.getCount() == 0) {
                    Text(text = stringResource(id = R.string.all_favorite_animals_info), modifier = Modifier.padding(end = 5.dp))
                }

                //Dogs
                if (allFavoriteAnimals.dogs.size > 0) {
                    Text(text = "Dogs (${allFavoriteAnimals.dogs.size})", fontWeight = FontWeight.Bold)
                    Row(modifier = Modifier.horizontalScroll(rememberScrollState()).fillMaxWidth().height(80.dp), horizontalArrangement = Arrangement.Absolute.SpaceBetween) {

                        repeat(allFavoriteAnimals.dogs.size) { i ->
                            CircleImage(url = allFavoriteAnimals.dogs.get(i).image?: "",
                                modifier = Modifier.size(80.dp).padding(5.dp).clip(CircleShape))

                            if (i == allFavoriteAnimals.dogs.size - 1)
                                Spacer(modifier = Modifier.width(5.dp))
                        }
                    }

                    if (allFavoriteAnimals.dogs.size == 0)
                        Text(text = "No favorite dog")

                }

                Spacer(modifier = Modifier.height(5.dp))

                //Cats
                if (allFavoriteAnimals.cats.size > 0) {
                    Text(text = "Cats (${allFavoriteAnimals.cats.size})", fontWeight = FontWeight.Bold)
                    Row(modifier = Modifier.horizontalScroll(rememberScrollState()).fillMaxWidth().height(80.dp), horizontalArrangement = Arrangement.Absolute.SpaceBetween) {

                        repeat(allFavoriteAnimals.cats.size) { i ->
                            CircleImage(url = allFavoriteAnimals.cats.get(i).image?: "",
                                modifier = Modifier.size(80.dp).padding(5.dp).clip(CircleShape))

                        }
                    }
                }

                Spacer(modifier = Modifier.height(5.dp))

                //Birds
                if (allFavoriteAnimals.birds.size > 0) {
                    Text(text = "Birds (${allFavoriteAnimals.birds.size})", fontWeight = FontWeight.Bold)
                    Row(modifier = Modifier.horizontalScroll(rememberScrollState()).fillMaxWidth().height(80.dp),
                        horizontalArrangement = Arrangement.Absolute.SpaceBetween) {

                        repeat(allFavoriteAnimals.birds.size) { i ->
                            CircleImage(url = allFavoriteAnimals.birds.get(i).image?: "",
                                modifier = Modifier.size(80.dp).padding(5.dp).clip(CircleShape))

                        }
                    }
                }
            }
            

        }
    }

}