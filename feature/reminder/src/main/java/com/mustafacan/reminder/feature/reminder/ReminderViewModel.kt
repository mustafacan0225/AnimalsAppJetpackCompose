package com.mustafacan.reminder.feature.reminder

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.viewModelScope
import com.mustafacan.core.util.hasNotificationPermission
import com.mustafacan.core.util.openPermissionsPage
import com.mustafacan.core.viewmodel.BaseViewModel
import com.mustafacan.domain.usecase.reminder.GetReminderBirdsUseCase
import com.mustafacan.domain.usecase.reminder.GetReminderCatsUseCase
import com.mustafacan.domain.usecase.reminder.GetReminderDogsUseCase
import com.mustafacan.domain.usecase.reminder.SaveReminderBirdsUseCase
import com.mustafacan.domain.usecase.reminder.SaveReminderCatsUseCase
import com.mustafacan.domain.usecase.reminder.SaveReminderDogsUseCase
import com.mustafacan.reminder.R
import com.mustafacan.reminder.feature.worker.ReminderWorker
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReminderViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val getReminderDogsUseCase: GetReminderDogsUseCase,
    private val getReminderCatsUseCase: GetReminderCatsUseCase,
    private val getReminderBirdsUseCase: GetReminderBirdsUseCase,
    private val saveReminderDogsUseCase: SaveReminderDogsUseCase,
    private val saveReminderCatsUseCase: SaveReminderCatsUseCase,
    private val saveReminderBirdsUseCase: SaveReminderBirdsUseCase
) :
    BaseViewModel<ReminderScreenUiStateManager.ReminderScreenState,
            ReminderScreenUiStateManager.ReminderScreenEvent,
            ReminderScreenUiStateManager.ReminderScreenEffect>(
        initialState = ReminderScreenUiStateManager.ReminderScreenState.initial(),
        uiStateManager = ReminderScreenUiStateManager()
    ) {

    init {
        viewModelScope.launch {
            updateDogsReminder(getReminderDogsUseCase.runUseCase())
            updateCatsReminder(getReminderCatsUseCase.runUseCase())
            updateBirdsReminder(getReminderBirdsUseCase.runUseCase())
        }

    }

    fun dogsReminderUpdate(isReminder: Boolean) {
        viewModelScope.launch {
            if (isReminder) {
                if (context.hasNotificationPermission()) {
                    updateDogsReminder(isReminder)
                    ReminderWorker.initWorker(
                        context,
                        ReminderWorker.ReminderType.REMINDER_DOGS,
                        R.string.reminder_title_dogs,
                        R.drawable.temperament
                    )
                } else {
                    Toast.makeText(context, "Permission Required", Toast.LENGTH_LONG).show()
                    context.openPermissionsPage()
                }

            } else {
                updateDogsReminder(isReminder)
                ReminderWorker.cancelWorker(context, ReminderWorker.ReminderType.REMINDER_DOGS.name)
            }
        }

    }

    suspend fun updateDogsReminder(isReminder: Boolean) {
        saveReminderDogsUseCase.runUseCase(isReminder)
        sendEvent(ReminderScreenUiStateManager.ReminderScreenEvent.DogsReminderUpdate(isReminder))
    }

    fun catsReminderUpdate(isReminder: Boolean) {
        viewModelScope.launch {
            if (isReminder) {
                if (context.hasNotificationPermission()) {
                    updateCatsReminder(isReminder)
                    ReminderWorker.initWorker(
                        context,
                        ReminderWorker.ReminderType.REMINDER_CATS,
                        R.string.reminder_title_cats,
                        R.drawable.kitten
                    )
                } else {
                    Toast.makeText(context, "Permission Required", Toast.LENGTH_LONG).show()
                    context.openPermissionsPage()
                }

            } else {
                updateCatsReminder(isReminder)
                ReminderWorker.cancelWorker(context, ReminderWorker.ReminderType.REMINDER_CATS.name)
            }
        }
    }

    suspend fun updateCatsReminder(isReminder: Boolean) {
        saveReminderCatsUseCase.runUseCase(isReminder)
        sendEvent(ReminderScreenUiStateManager.ReminderScreenEvent.CatsReminderUpdate(isReminder))
    }

    fun birdsReminderUpdate(isReminder: Boolean) {
        viewModelScope.launch {
            if (isReminder) {
                if (context.hasNotificationPermission()) {
                    updateBirdsReminder(isReminder)
                    ReminderWorker.initWorker(
                        context,
                        ReminderWorker.ReminderType.REMINDER_BIRDS,
                        R.string.reminder_title_birds,
                        R.drawable.bird
                    )
                } else {
                    Toast.makeText(context, "Permission Required", Toast.LENGTH_LONG).show()
                    context.openPermissionsPage()
                }

            } else {
                updateBirdsReminder(isReminder)
                ReminderWorker.cancelWorker(
                    context,
                    ReminderWorker.ReminderType.REMINDER_BIRDS.name
                )
            }
        }
    }

    suspend fun updateBirdsReminder(isReminder: Boolean) {
        saveReminderBirdsUseCase.runUseCase(isReminder)
        sendEvent(ReminderScreenUiStateManager.ReminderScreenEvent.BirdsReminderUpdate(isReminder))
    }
}