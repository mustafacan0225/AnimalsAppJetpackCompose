package com.mustafacan.ui_reminder.feature.reminder

import com.mustafacan.data.local.datasource.sharedpref.reminder.LocalDataSourceReminder
import com.mustafacan.ui_common.viewmodel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ReminderViewModel @Inject constructor(private val localDataSourceReminder: LocalDataSourceReminder) :
    BaseViewModel<ReminderScreenReducer.ReminderScreenState,
            ReminderScreenReducer.ReminderScreenEvent,
            ReminderScreenReducer.ReminderScreenEffect>(
        initialState = ReminderScreenReducer.ReminderScreenState.initial(
            localDataSourceReminder
        ), reducer = ReminderScreenReducer()
    ) {

    fun dogsReminderUpdate(isReminder: Boolean) {
        localDataSourceReminder.saveReminderDogs(isReminder)
        sendEvent(ReminderScreenReducer.ReminderScreenEvent.DogsReminderUpdate(isReminder))
    }

    fun catsReminderUpdate(isReminder: Boolean) {
        localDataSourceReminder.saveReminderCats(isReminder)
        sendEvent(ReminderScreenReducer.ReminderScreenEvent.CatsReminderUpdate(isReminder))
    }

    fun birdsReminderUpdate(isReminder: Boolean) {
        localDataSourceReminder.saveReminderBirds(isReminder)
        sendEvent(ReminderScreenReducer.ReminderScreenEvent.BirdsReminderUpdate(isReminder))
    }
}