package com.mustafacan.domain.repository.sharedpreference

interface ReminderSettingsRepository {
    suspend fun saveReminderDogs(isReminder: Boolean)
    suspend fun getReminderDogs(): Boolean
    suspend fun saveReminderCats(isReminder: Boolean)
    suspend fun getReminderCats(): Boolean
    suspend fun saveReminderBirds(isReminder: Boolean)
    suspend fun getReminderBirds(): Boolean
}
