package com.mustafacan.ui_reminder.feature.reminder

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun ReminderScren() {
    Column(modifier = Modifier.fillMaxSize()) {
        Text(text = "REMINDER PAGE", color = Color.Red)
    }
}