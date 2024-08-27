package com.mustafacan.animalsapp.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.mustafacan.animalsapp.ui.components.menu.bottom.BottomMenu
import com.mustafacan.animalsapp.ui.navigation.NavigationMain
import com.mustafacan.animalsapp.ui.theme.AnimalsAppTheme
import dagger.hilt.android.AndroidEntryPoint

@OptIn(ExperimentalMaterial3Api::class)
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //enableEdgeToEdge()
        setContent {
            AnimalsAppTheme {
                Surface(modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background) {
                    val navController = rememberNavController()

                    Scaffold(bottomBar = {

                        //com.mustafacan.animalsapp.ui.components.bottom.menu - BottomMenu.kt
                        BottomMenu(
                            navController = navController
                        )

                    }) {
                        Box(modifier = Modifier.padding(it)) {
                            //com.mustafacan.animalsapp.ui.components.navigation - NavigationMain.kt
                            NavigationMain(navController = navController)
                        }
                    }


                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    AnimalsAppTheme {
        Greeting("Android")
    }
}