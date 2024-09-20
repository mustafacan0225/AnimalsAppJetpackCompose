package com.mustafacan.data.local.datasource.sharedpref.dogs
import com.mustafacan.data.local.datasource.sharedpref.SharedPreferencesManager
import javax.inject.Inject

class LocalDataSourceDogs @Inject constructor(private val sharedPreferencesManager: SharedPreferencesManager) {

    companion object {
        const val KEY_VIEW_TYPE_DOG_LIST = "KEY_VIEW_TYPE_DOG_LIST"
        const val KEY_VIEW_TYPE_SETTINGS = "KEY_VIEW_TYPE_SETTINGS"
        const val KEY_SEARCH_TYPE = "KEY_SEARCH_TYPE"
        const val KEY_VIEW_TYPE_TAB = "KEY_VIEW_TYPE_TAB"
    }

    fun saveListTypeForDogList(type: String) {
        sharedPreferencesManager.setValue(KEY_VIEW_TYPE_DOG_LIST, type)
    }

    fun getListTypeForDogList(): String? {
        return sharedPreferencesManager.getValueString(KEY_VIEW_TYPE_DOG_LIST)
    }

    fun saveSettingsTypeForDogList(type: String) {
        sharedPreferencesManager.setValue(KEY_VIEW_TYPE_SETTINGS, type)
    }

    fun getSettingsTypeForDogList(): String? {
        return sharedPreferencesManager.getValueString(KEY_VIEW_TYPE_SETTINGS)
    }

    fun saveSearchTypeForDogList(type: String) {
        sharedPreferencesManager.setValue(KEY_SEARCH_TYPE, type)
    }

    fun getSearchTypeForDogList(): String? {
        return sharedPreferencesManager.getValueString(KEY_SEARCH_TYPE)
    }

    fun saveTabTypeForDogDetail(type: String) {
        sharedPreferencesManager.setValue(KEY_VIEW_TYPE_TAB, type)
    }

    fun getTabTypeForDogDetail(): String? {
        return sharedPreferencesManager.getValueString(KEY_VIEW_TYPE_TAB)
    }



}