package com.mustafacan.ui_reminder.feature.reminder

import androidx.compose.runtime.Immutable
import com.mustafacan.data.local.datasource.sharedpref.reminder.LocalDataSourceReminder
import com.mustafacan.ui_common.viewmodel.Reducer

class ReminderScreenReducer() :
    Reducer<ReminderScreenReducer.ReminderScreenState, ReminderScreenReducer.ReminderScreenEvent, ReminderScreenReducer.ReminderScreenEffect> {

    @Immutable
    sealed class ReminderScreenEvent : Reducer.ViewEvent {
        data class DogsReminderUpdate(val isReminder: Boolean) : ReminderScreenEvent()
        data class CatsReminderUpdate(val isReminder: Boolean) : ReminderScreenEvent()
        data class BirdsReminderUpdate(val isReminder: Boolean) : ReminderScreenEvent()
    }

    @Immutable
    sealed class ReminderScreenEffect : Reducer.ViewEffect { }

    @Immutable
    data class ReminderScreenState(
        val dogsReminderState: Boolean,
        val catsReminderState: Boolean,
        val birdsReminderState: Boolean,
    ) : Reducer.ViewState {
        companion object {
            fun initial(
                localDataSourceReminder: LocalDataSourceReminder
            ): ReminderScreenState {
                return ReminderScreenState(
                    dogsReminderState = localDataSourceReminder.getReminderDogs(),
                    catsReminderState = localDataSourceReminder.getReminderCats(),
                    birdsReminderState = localDataSourceReminder.getReminderBirds()
                )
            }
        }
    }

    override fun reduce(
        previousState: ReminderScreenState,
        event: ReminderScreenEvent
    ): Pair<ReminderScreenState, ReminderScreenEffect?> {

        return when (event) {
            is ReminderScreenEvent.DogsReminderUpdate -> {
                previousState.copy(dogsReminderState = event.isReminder) to null
            }

            is ReminderScreenEvent.CatsReminderUpdate -> {
                previousState.copy(catsReminderState = event.isReminder) to null
            }

            is ReminderScreenEvent.BirdsReminderUpdate -> {
                previousState.copy(birdsReminderState = event.isReminder) to null
            }

        }
    }

}