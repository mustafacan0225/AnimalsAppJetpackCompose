package com.mustafacan.domain.usecase.reminder

import com.mustafacan.domain.repository.sharedpreference.ReminderSettingsRepository
import javax.inject.Inject

class SaveReminderBirdsUseCase @Inject constructor(private val repository: ReminderSettingsRepository) {
    suspend fun runUseCase(isReminder: Boolean) {
        repository.saveReminderBirds(isReminder)
    }
}