package com.mustafacan.animalsapp.ui

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.app.ActivityCompat.shouldShowRequestPermissionRationale
import androidx.core.content.ContextCompat
import com.mustafacan.animalsapp.ui.screen.main.MainScreen
import com.mustafacan.animalsapp.ui.theme.AnimalsAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestNotificationPermission()
        setContent {
            AnimalsAppTheme {
                Surface(modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background) {
                    MainScreen()
                }
            }
        }
    }

    private var requestPermissionLauncher: ActivityResultLauncher<String> =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (!isGranted) {
                Log.d("POST_NOTIFICATION_PERMISSION", "USER DENIED PERMISSION")
            } else {
                Log.d("POST_NOTIFICATION_PERMISSION", "USER GRANTED PERMISSION")
            }
        }

    /**
     * Requests notification permission if it's not granted.
     * Shows a toast message indicating the permission status.
     */
    private fun requestNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            val permission = Manifest.permission.POST_NOTIFICATIONS
            when {
                ContextCompat.checkSelfPermission(
                    this, permission
                ) == PackageManager.PERMISSION_GRANTED -> {
                    // Action to take when permission is already granted
                    Toast.makeText(this, "Permission granted", Toast.LENGTH_LONG).show()
                }

                shouldShowRequestPermissionRationale(permission) -> {
                    // Action to take when permission was denied permanently
                    Toast.makeText(this, "Permission denied permanently", Toast.LENGTH_LONG).show()
                }

                else -> {
                    // Request permission
                    requestPermissionLauncher.launch(permission)
                }
            }
        } else {
            // Device does not support required permission
            Toast.makeText(this, "No required permission", Toast.LENGTH_LONG).show()
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

