package com.mustafacan.reminder.feature.reminder

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import com.mustafacan.core.components.toolbar.Toolbar
import com.mustafacan.reminder.R
import androidx.compose.runtime.State
import androidx.compose.ui.res.painterResource

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
fun ReminderContent(uiState : State<ReminderScreenUiStateManager.ReminderScreenState>,
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
        Text(text = stringResource(id = R.string.reminder_switch_text_dogs))
        Switch(
            checked = uiState.value.dogsReminderState,
            onCheckedChange = { dogSwitchStateUpdated(it) },
            colors = SwitchDefaults.colors(
                checkedThumbColor = Color.White,
                checkedTrackColor = colorResource(id = R.color.track_checked_color),
                uncheckedThumbColor = Color.LightGray,
                uncheckedTrackColor = colorResource(id = R.color.track_unchecked_color),
                uncheckedBorderColor = Color.LightGray,
            ),
            thumbContent = {
                if (uiState.value.dogsReminderState) {
                    Icon(
                        painter = painterResource(id = R.drawable.temperament),
                        contentDescription = null,
                        modifier = Modifier.size(SwitchDefaults.IconSize),
                    )
                }
            }
        )
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 10.dp, end = 10.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = stringResource(id = R.string.reminder_switch_text_cats))
        Switch(
            checked = uiState.value.catsReminderState,
            onCheckedChange = { catSwitchStateUpdated(it) },
            colors = SwitchDefaults.colors(
                checkedThumbColor = Color.White,
                checkedTrackColor = colorResource(id = R.color.track_checked_color),
                uncheckedThumbColor = Color.LightGray,
                uncheckedTrackColor = colorResource(id = R.color.track_unchecked_color),
                uncheckedBorderColor = Color.LightGray,
            ),
            thumbContent = {
                if (uiState.value.catsReminderState) {
                    Icon(
                        painter = painterResource(id = R.drawable.kitten),
                        contentDescription = null,
                        modifier = Modifier.size(SwitchDefaults.IconSize),
                    )
                }
            }
        )
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 10.dp, end = 10.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = stringResource(id = R.string.reminder_switch_text_birds))

        Switch(
            checked = uiState.value.birdsReminderState,
            onCheckedChange = { birdSwitchStateUpdated(it) },
            colors = SwitchDefaults.colors(
                checkedThumbColor = Color.White,
                checkedTrackColor = colorResource(id = R.color.track_checked_color),
                uncheckedThumbColor = Color.LightGray,
                uncheckedTrackColor = colorResource(id = R.color.track_unchecked_color),
                uncheckedBorderColor = Color.LightGray,
            ),
            thumbContent = {
                if (uiState.value.birdsReminderState) {
                    Icon(
                        painter = painterResource(id = R.drawable.bird),
                        contentDescription = null,
                        modifier = Modifier.size(SwitchDefaults.IconSize),
                    )
                }
            }
        )
    }

    Text(modifier = Modifier
        .fillMaxWidth()
        .padding(10.dp), fontSize = 12.sp, text = stringResource(id = R.string.reminder_description), textAlign = TextAlign.Start)


    Spacer(modifier = Modifier
        .fillMaxWidth()
        .height(1.dp)
        .background(Color.LightGray))

    Text(modifier = Modifier
        .fillMaxWidth()
        .padding(10.dp), fontSize = 14.sp, fontWeight = FontWeight.Black, text = stringResource(id = R.string.reminder_workmanager), textAlign = TextAlign.Center)

}