package com.mustafacan.reminder.feature.reminder

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.viewModelScope
import com.mustafacan.core.util.hasNotificationPermission
import com.mustafacan.core.util.openPermissionsPage
import com.mustafacan.core.viewmodel.BaseViewModel
import com.mustafacan.data.sharedpreferences.reminder.ReminderSettings
import com.mustafacan.reminder.R
import com.mustafacan.reminder.feature.worker.ReminderWorker
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReminderViewModel @Inject constructor(@ApplicationContext private val context: Context,
                                            private val reminderSettings: ReminderSettings) :
    BaseViewModel<ReminderScreenUiStateManager.ReminderScreenState,
            ReminderScreenUiStateManager.ReminderScreenEvent,
            ReminderScreenUiStateManager.ReminderScreenEffect>(
        initialState = ReminderScreenUiStateManager.ReminderScreenState.initial(
            reminderSettings
        ), uiStateManager = ReminderScreenUiStateManager()
    ) {

    fun dogsReminderUpdate(isReminder: Boolean) {
        viewModelScope.launch {
            if (isReminder) {
                if (context.hasNotificationPermission()) {
                    saveDogsReminder(isReminder)
                    ReminderWorker.initWorker(context,
                        ReminderWorker.ReminderType.REMINDER_DOGS,
                        R.string.reminder_title_dogs,
                        R.drawable.temperament)
                } else {
                    Toast.makeText(context, "Permission Required", Toast.LENGTH_LONG).show()
                    context.openPermissionsPage()
                }

            } else {
                saveDogsReminder(isReminder)
                ReminderWorker.cancelWorker(context, ReminderWorker.ReminderType.REMINDER_DOGS.name)
            }
        }

    }

    fun saveDogsReminder(isReminder: Boolean) {
        reminderSettings.saveReminderDogs(isReminder)
        sendEvent(ReminderScreenUiStateManager.ReminderScreenEvent.DogsReminderUpdate(isReminder))
    }

    fun catsReminderUpdate(isReminder: Boolean) {
        viewModelScope.launch {
            if (isReminder) {
                if (context.hasNotificationPermission()) {
                    saveCatsReminder(isReminder)
                    ReminderWorker.initWorker(context,
                        ReminderWorker.ReminderType.REMINDER_CATS,
                        R.string.reminder_title_cats,
                        R.drawable.kitten)
                } else {
                    Toast.makeText(context, "Permission Required", Toast.LENGTH_LONG).show()
                    context.openPermissionsPage()
                }

            } else {
                saveCatsReminder(isReminder)
                ReminderWorker.cancelWorker(context, ReminderWorker.ReminderType.REMINDER_CATS.name)
            }
        }
    }

    fun saveCatsReminder(isReminder: Boolean) {
        reminderSettings.saveReminderCats(isReminder)
        sendEvent(ReminderScreenUiStateManager.ReminderScreenEvent.CatsReminderUpdate(isReminder))
    }

    fun birdsReminderUpdate(isReminder: Boolean) {
        viewModelScope.launch {
            if (isReminder) {
                if (context.hasNotificationPermission()) {
                    saveBirdsReminder(isReminder)
                    ReminderWorker.initWorker(context,
                        ReminderWorker.ReminderType.REMINDER_BIRDS,
                        R.string.reminder_title_birds,
                        R.drawable.bird)
                } else {
                    Toast.makeText(context, "Permission Required", Toast.LENGTH_LONG).show()
                    context.openPermissionsPage()
                }

            } else {
                saveBirdsReminder(isReminder)
                ReminderWorker.cancelWorker(context, ReminderWorker.ReminderType.REMINDER_BIRDS.name)
            }
        }
    }

    fun saveBirdsReminder(isReminder: Boolean) {
        reminderSettings.saveReminderBirds(isReminder)
        sendEvent(ReminderScreenUiStateManager.ReminderScreenEvent.BirdsReminderUpdate(isReminder))
    }
}