package com.mustafacan.data.local

import android.content.Context
import android.content.SharedPreferences
import com.squareup.moshi.Moshi

class SharedPreferencesManager(context: Context, val moshi: Moshi) {

    private var sharedPreferences: SharedPreferences

    companion object {
        const val PREF_KEY_TEST =  "KEY_TEST"
    }

    init {
        sharedPreferences = context.getSharedPreferences("shared_preferences_animal_apps", Context.MODE_PRIVATE)
    }

    fun getSharedPreferences() : SharedPreferences {
        return sharedPreferences
    }

    fun deletePreference(key: String) {
        try {
            sharedPreferences.edit().remove(key).commit();
        } catch (ex: Exception) {}
    }

    fun setValue(key: String, value: Any) {
        when (value) {
            is String -> sharedPreferences.edit().putString(key, value).apply()
            is Boolean -> sharedPreferences.edit().putBoolean(key, value).apply()
            is Int -> sharedPreferences.edit().putInt(key, value).apply()
            is Long -> sharedPreferences.edit().putLong(key, value).apply()
        }
    }

    fun getValueString(key: String, defValue: String? = null): String? = sharedPreferences.getString(key, defValue)
    fun getValueBoolean(key: String): Boolean = sharedPreferences.getBoolean(key, false)
    fun getValueLong(key: String): Long = sharedPreferences.getLong(key, 0)
    fun getValueInt(key: String): Int = sharedPreferences.getInt(key, 0)

    inline fun <reified T : Any> getValueModel(key: String): T? {
        getValueString(key)?.let {
            return moshi.adapter(T::class.java).fromJson(it)
        }
        return null
    }

    inline fun < reified T : Any> saveModel(key: String, model: T) {
        val jsonData: String = moshi.adapter(T::class.java).toJson(model)
        setValue(key,jsonData)
    }

}
