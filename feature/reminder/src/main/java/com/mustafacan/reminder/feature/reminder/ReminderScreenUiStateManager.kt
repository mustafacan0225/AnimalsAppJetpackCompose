package com.mustafacan.reminder.feature.reminder

import androidx.compose.runtime.Immutable
import com.mustafacan.core.viewmodel.UiStateManager

class ReminderScreenUiStateManager() :
    UiStateManager<ReminderScreenUiStateManager.ReminderScreenState, ReminderScreenUiStateManager.ReminderScreenEvent, ReminderScreenUiStateManager.ReminderScreenEffect> {

    @Immutable
    sealed class ReminderScreenEvent : UiStateManager.ViewEvent {
        data class DogsReminderUpdate(val isReminder: Boolean) : ReminderScreenEvent()
        data class CatsReminderUpdate(val isReminder: Boolean) : ReminderScreenEvent()
        data class BirdsReminderUpdate(val isReminder: Boolean) : ReminderScreenEvent()
    }

    @Immutable
    sealed class ReminderScreenEffect : UiStateManager.ViewEffect { }

    @Immutable
    data class ReminderScreenState(
        val dogsReminderState: Boolean = false,
        val catsReminderState: Boolean = false,
        val birdsReminderState: Boolean = false,
    ) : UiStateManager.ViewState {
        companion object {
            fun initial(): ReminderScreenState {
                return ReminderScreenState()
            }
        }
    }

    override fun handleEvent(
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