package com.mustafacan.data.sharedpreferences.reminder

import com.mustafacan.domain.repository.sharedpreference.ReminderSettingsRepository
import javax.inject.Inject

class ReminderSettingsRepositoryImpl @Inject constructor(private val reminderSettings: ReminderSettings): ReminderSettingsRepository {
    override suspend fun saveReminderDogs(isReminder: Boolean) {
        reminderSettings.saveReminderDogs(isReminder)
    }

    override suspend fun getReminderDogs(): Boolean {
        return reminderSettings.getReminderDogs()
    }

    override suspend fun saveReminderCats(isReminder: Boolean) {
        reminderSettings.saveReminderCats(isReminder)
    }

    override suspend fun getReminderCats(): Boolean {
        return reminderSettings.getReminderCats()
    }

    override suspend fun saveReminderBirds(isReminder: Boolean) {
        reminderSettings.saveReminderBirds(isReminder)
    }

    override suspend fun getReminderBirds(): Boolean {
        return reminderSettings.getReminderBirds()
    }
}