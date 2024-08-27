package com.mustafacan.data.local
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class LocalDataSource @Inject constructor(private val sharedPreferencesManager: SharedPreferencesManager) {

    fun setTest(value: Int) {
        try {
            sharedPreferencesManager.setValue(SharedPreferencesManager.PREF_KEY_TEST, value)
        } catch (e: Exception) {

        }
    }

    fun getTest(): Int {
        return sharedPreferencesManager.getValueInt(SharedPreferencesManager.PREF_KEY_TEST)
    }

    fun getTestFlow(): Flow<Int> {
        return sharedPreferencesManager.getSharedPreferences().observeKey(SharedPreferencesManager.PREF_KEY_TEST, 0)
    }


}