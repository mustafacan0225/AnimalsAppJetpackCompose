package com.mustafacan.ui_reminder.feature.reminder

import android.content.Context
import com.mustafacan.data.local.datasource.sharedpref.reminder.LocalDataSourceReminder
import com.mustafacan.ui_common.viewmodel.BaseViewModel
import com.mustafacan.ui_reminder.R
import com.mustafacan.ui_reminder.feature.worker.ReminderWorker
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
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
        localDataSourceReminder.saveReminderDogs(isReminder)
        sendEvent(ReminderScreenReducer.ReminderScreenEvent.DogsReminderUpdate(isReminder))

        if (isReminder) {
            ReminderWorker.initWorker(context,
                ReminderWorker.ReminderType.REMINDER_DOGS,
                R.string.reminder_title_dogs,
                R.drawable.temperament)
        } else {
            ReminderWorker.cancelWorker(context, ReminderWorker.ReminderType.REMINDER_DOGS.name)
        }
    }

    fun catsReminderUpdate(isReminder: Boolean) {
        localDataSourceReminder.saveReminderCats(isReminder)
        sendEvent(ReminderScreenReducer.ReminderScreenEvent.CatsReminderUpdate(isReminder))

        if (isReminder) {
            ReminderWorker.initWorker(context,
                ReminderWorker.ReminderType.REMINDER_CATS,
                R.string.reminder_title_cats,
                R.drawable.kitten)
        } else {
            ReminderWorker.cancelWorker(context, ReminderWorker.ReminderType.REMINDER_CATS.name)
        }
    }

    fun birdsReminderUpdate(isReminder: Boolean) {
        localDataSourceReminder.saveReminderBirds(isReminder)
        sendEvent(ReminderScreenReducer.ReminderScreenEvent.BirdsReminderUpdate(isReminder))

        if (isReminder) {
            ReminderWorker.initWorker(context,
                ReminderWorker.ReminderType.REMINDER_BIRDS,
                R.string.reminder_title_birds,
                R.drawable.bird)
        } else {
            ReminderWorker.cancelWorker(context, ReminderWorker.ReminderType.REMINDER_BIRDS.name)
        }
    }
}