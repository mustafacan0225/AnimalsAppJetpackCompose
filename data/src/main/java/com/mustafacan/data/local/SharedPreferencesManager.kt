package com.mustafacan.data.local

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class SharedPreferencesManager(context: Context) {

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

    inline fun <reified T> saveModel(key: String, item: T) {
        try {
            setValue(key, Gson().toJson(item))
        } catch (e: Exception) {}

    }

    inline fun <reified T> getModel(key: String): T? {
        try {
            val type = object : TypeToken<T>() {}.type
            return Gson().fromJson(getValueString(key), type)
        } catch (e: Exception) {
            return null
        }
    }

    inline fun <reified T> addItemToList(key: String, item: T) {
        try {
            val savedList = getList<T>(key).toMutableList()
            savedList.add(item)
            setValue(key, Gson().toJson(savedList))
        } catch (e: Exception) {}

    }

    inline fun <reified T> removeItemFromList(key: String, item: T) {
        try {
            val savedList = getList<T>(key).toMutableList()
            savedList.remove(item)
            setValue(key, Gson().toJson(savedList))
        } catch (e: Exception) {}

    }

    fun <T> putList(key: String, list: List<T>) {
        try {
            saveModel(key, Gson().toJson(list))
        } catch (e: Exception) {}
    }

    inline fun <reified T> getList(key: String): List<T> {
        val listJson = getValueString(key)
        if (!listJson.isNullOrBlank()) {
            val type = object : TypeToken<List<T>>() {}.type
            return Gson().fromJson(listJson, type)
        }
        return listOf()
    }

}
