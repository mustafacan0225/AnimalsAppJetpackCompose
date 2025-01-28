package com.mustafacan.domain.usecase.reminder

import com.mustafacan.domain.repository.sharedpreference.ReminderSettingsRepository
import javax.inject.Inject

class GetReminderBirdsUseCase  @Inject constructor(private val repository: ReminderSettingsRepository) {
    suspend fun runUseCase(): Boolean {
        return repository.getReminderBirds()
    }
}