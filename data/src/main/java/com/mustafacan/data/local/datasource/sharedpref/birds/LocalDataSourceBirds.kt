package com.mustafacan.data.local.datasource.sharedpref.birds
import com.mustafacan.data.local.datasource.sharedpref.SharedPreferencesManager
import javax.inject.Inject

class LocalDataSourceBirds @Inject constructor(private val sharedPreferencesManager: SharedPreferencesManager) {

    companion object {
        const val KEY_VIEW_TYPE_BIRD_LIST = "KEY_VIEW_TYPE_BIRD_LIST"
        const val KEY_VIEW_TYPE_SETTINGS_BIRD = "KEY_VIEW_TYPE_SETTINGS_BIRD"
        const val KEY_SEARCH_TYPE_BIRD = "KEY_SEARCH_TYPE_BIRD"
        const val KEY_VIEW_TYPE_TAB_BIRD = "KEY_VIEW_TYPE_TAB_BIRD"
    }

    fun saveListTypeForBirdList(type: String) {
        sharedPreferencesManager.setValue(KEY_VIEW_TYPE_BIRD_LIST, type)
    }

    fun getListTypeForBirdList(): String? {
        return sharedPreferencesManager.getValueString(KEY_VIEW_TYPE_BIRD_LIST)
    }

    fun saveSettingsTypeForBirdList(type: String) {
        sharedPreferencesManager.setValue(KEY_VIEW_TYPE_SETTINGS_BIRD, type)
    }

    fun getSettingsTypeForBirdList(): String? {
        return sharedPreferencesManager.getValueString(KEY_VIEW_TYPE_SETTINGS_BIRD)
    }

    fun saveSearchTypeForBirdList(type: String) {
        sharedPreferencesManager.setValue(KEY_SEARCH_TYPE_BIRD, type)
    }

    fun getSearchTypeForBirdList(): String? {
        return sharedPreferencesManager.getValueString(KEY_SEARCH_TYPE_BIRD)
    }

    fun saveTabTypeForBirdDetail(type: String) {
        sharedPreferencesManager.setValue(KEY_VIEW_TYPE_TAB_BIRD, type)
    }

    fun getTabTypeForBirdDetail(): String? {
        return sharedPreferencesManager.getValueString(KEY_VIEW_TYPE_TAB_BIRD)
    }



}