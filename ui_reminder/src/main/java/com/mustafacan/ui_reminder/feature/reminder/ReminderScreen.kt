package com.mustafacan.ui_reminder.feature.reminder

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.mustafacan.ui_common.components.toolbar.Toolbar
import com.mustafacan.ui_reminder.R
import androidx.compose.runtime.State

@Composable
fun ReminderScren() {
    val viewModel = hiltViewModel<ReminderViewModel>()
    val state = viewModel.state.collectAsStateWithLifecycle()
    Column(modifier = Modifier.fillMaxSize()) {
        Toolbar(title = stringResource(id = R.string.reminder))
        Spacer(modifier = Modifier.height(10.dp))
        ReminderContent(uiState = state,
            dogSwitchStateUpdated = {
                viewModel.dogsReminderUpdate(it)
            }, catSwitchStateUpdated = {
                viewModel.catsReminderUpdate(it)
            }, birdSwitchStateUpdated = {
                viewModel.birdsReminderUpdate(it)
            })

    }
}

@Composable
fun ReminderContent(uiState : State<ReminderScreenReducer.ReminderScreenState>,
                    dogSwitchStateUpdated: (isReminder: Boolean) -> Unit,
                    catSwitchStateUpdated: (isReminder: Boolean) -> Unit,
                    birdSwitchStateUpdated: (isReminder: Boolean) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 10.dp, end = 10.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = "Reminder notifications for dogs")
        Switch(
            checked = uiState.value.dogsReminderState,
            onCheckedChange = { dogSwitchStateUpdated(it) },
            colors = SwitchDefaults.colors(
                checkedThumbColor = Color.White,
                checkedTrackColor = colorResource(id = R.color.track_checked_color),
                uncheckedThumbColor = Color.LightGray,
                uncheckedTrackColor = colorResource(id = R.color.track_unchecked_color),
                uncheckedBorderColor = Color.LightGray,
            )
        )
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 10.dp, end = 10.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = "Reminder notifications for cats")
        Switch(
            checked = uiState.value.catsReminderState,
            onCheckedChange = { catSwitchStateUpdated(it) },
            colors = SwitchDefaults.colors(
                checkedThumbColor = Color.White,
                checkedTrackColor = colorResource(id = R.color.track_checked_color),
                uncheckedThumbColor = Color.LightGray,
                uncheckedTrackColor = colorResource(id = R.color.track_unchecked_color),
                uncheckedBorderColor = Color.LightGray,
            )
        )
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 10.dp, end = 10.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = "Reminder notifications for birds")
        Switch(
            checked = uiState.value.birdsReminderState,
            onCheckedChange = { birdSwitchStateUpdated(it) },
            colors = SwitchDefaults.colors(
                checkedThumbColor = Color.White,
                checkedTrackColor = colorResource(id = R.color.track_checked_color),
                uncheckedThumbColor = Color.LightGray,
                uncheckedTrackColor = colorResource(id = R.color.track_unchecked_color),
                uncheckedBorderColor = Color.LightGray,
            )
        )
    }

    Text(modifier = Modifier
        .fillMaxWidth()
        .padding(10.dp), fontSize = 12.sp, text = "You can activate periodic reminder notifications. Even if the application is closed, a request will be sent to the rest API in the background and the data received from the remote server will be displayed as a notification.", textAlign = TextAlign.Start)


    Spacer(modifier = Modifier.fillMaxWidth().height(1.dp).background(Color.LightGray))

    Text(modifier = Modifier
        .fillMaxWidth()
        .padding(10.dp), fontSize = 14.sp, fontWeight = FontWeight.Black, text = "WorkManager is used for this feature.", textAlign = TextAlign.Center)

}