package com.mustafacan.domain.usecase.reminder

import com.mustafacan.domain.repository.sharedpreference.ReminderSettingsRepository
import javax.inject.Inject

class GetReminderDogsUseCase @Inject constructor(private val repository: ReminderSettingsRepository) {
    suspend fun runUseCase(): Boolean {
        return repository.getReminderDogs()
    }
}