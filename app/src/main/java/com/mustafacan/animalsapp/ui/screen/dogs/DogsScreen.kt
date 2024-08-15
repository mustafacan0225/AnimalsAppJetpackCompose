package com.mustafacan.animalsapp.ui.screen.dogs

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.mustafacan.animalsapp.ui.navigation.NavDestinationItem
import com.mustafacan.animalsapp.ui.theme.AnimalsAppTheme
import com.mustafacan.animalsapp.ui.toolbar.Toolbar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DogsScreen(navController: NavController) {
    val viewModel = hiltViewModel<DogsViewModel>()

    Column(
        Modifier
            .fillMaxWidth()
            .padding(0.dp)) {

        Toolbar(title = "Animals App - Dogs")
        /*TopAppBar(title = { Text(text = "Dogs Screen") }, modifier = Modifier
            .padding(0.dp)
            .background(MaterialTheme.colorScheme.onPrimary))*/

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp)
                /*.border(
                    border = BorderStroke(
                        1.dp, MaterialTheme.colorScheme.primary
                    ), MaterialTheme.shapes.large
                )*/,
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
           /* Text(
                "Dogs Screen",
                modifier = Modifier.padding(horizontal = 20.dp, vertical = 20.dp),
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold
            )*/

            Text(
                "Dog Screen Content",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(horizontal = 20.dp, vertical = 20.dp),
                textAlign = TextAlign.Center
            )

            Button(modifier = Modifier
                .padding(10.dp, 5.dp, 10.dp, 5.dp)
                .align(alignment = Alignment.CenterHorizontally),
                onClick = { navController.navigate(NavDestinationItem.DogsDetail.route) },
                shape = MaterialTheme.shapes.large,
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
            ) {
                Text(text = "Detail Page", color = Color.White)
            }
        }

    }

}