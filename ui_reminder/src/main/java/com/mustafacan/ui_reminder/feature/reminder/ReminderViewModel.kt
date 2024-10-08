package com.mustafacan.ui_reminder.feature.reminder

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.viewModelScope
import com.mustafacan.data.local.datasource.sharedpref.reminder.LocalDataSourceReminder
import com.mustafacan.ui_common.util.hasNotificationPermission
import com.mustafacan.ui_common.util.openPermissionsPage
import com.mustafacan.ui_common.viewmodel.BaseViewModel
import com.mustafacan.ui_reminder.R
import com.mustafacan.ui_reminder.feature.worker.ReminderWorker
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReminderViewModel @Inject constructor(@ApplicationContext private val context: Context,
                                            private val localDataSourceReminder: LocalDataSourceReminder) :
    BaseViewModel<ReminderScreenReducer.ReminderScreenState,
            ReminderScreenReducer.ReminderScreenEvent,
            ReminderScreenReducer.ReminderScreenEffect>(
        initialState = ReminderScreenReducer.ReminderScreenState.initial(
            localDataSourceReminder
        ), reducer = ReminderScreenReducer()
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
        localDataSourceReminder.saveReminderDogs(isReminder)
        sendEvent(ReminderScreenReducer.ReminderScreenEvent.DogsReminderUpdate(isReminder))
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
        localDataSourceReminder.saveReminderCats(isReminder)
        sendEvent(ReminderScreenReducer.ReminderScreenEvent.CatsReminderUpdate(isReminder))
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
        localDataSourceReminder.saveReminderBirds(isReminder)
        sendEvent(ReminderScreenReducer.ReminderScreenEvent.BirdsReminderUpdate(isReminder))
    }
}