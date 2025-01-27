package com.mustafacan.data.sharedpreferences.reminder

import com.mustafacan.data.sharedpreferences.SharedPreferencesManager
import javax.inject.Inject

class ReminderSettings @Inject constructor(private val sharedPreferencesManager: SharedPreferencesManager) {

    companion object {
        const val KEY_REMINDER_DOGS = "KEY_REMINDER_DOGS"
        const val KEY_REMINDER_CATS = "KEY_REMINDER_CATS"
        const val KEY_REMINDER_BIRDS = "KEY_REMINDER_BIRDS"
    }

    fun saveReminderDogs(isReminder: Boolean) {
        sharedPreferencesManager.setValue(KEY_REMINDER_DOGS, isReminder)
    }

    fun getReminderDogs(): Boolean {
        return sharedPreferencesManager.getValueBoolean(KEY_REMINDER_DOGS)
    }

    fun saveReminderCats(isReminder: Boolean) {
        sharedPreferencesManager.setValue(KEY_REMINDER_CATS, isReminder)
    }

    fun getReminderCats(): Boolean {
        return sharedPreferencesManager.getValueBoolean(KEY_REMINDER_CATS)
    }

    fun saveReminderBirds(isReminder: Boolean) {
        sharedPreferencesManager.setValue(KEY_REMINDER_BIRDS, isReminder)
    }

    fun getReminderBirds(): Boolean  {
        return sharedPreferencesManager.getValueBoolean(KEY_REMINDER_BIRDS)
    }



}