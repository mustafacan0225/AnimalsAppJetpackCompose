package com.mustafacan.data.local.datasource.sharedpref.cats
import com.mustafacan.data.local.datasource.sharedpref.SharedPreferencesManager
import javax.inject.Inject

class LocalDataSourceCats @Inject constructor(private val sharedPreferencesManager: SharedPreferencesManager) {

    companion object {
        const val KEY_VIEW_TYPE_CAT_LIST = "KEY_VIEW_TYPE_CAT_LIST"
        const val KEY_VIEW_TYPE_SETTINGS_CAT = "KEY_VIEW_TYPE_SETTINGS_CAT"
        const val KEY_SEARCH_TYPE_CAT = "KEY_SEARCH_TYPE_CAT"
        const val KEY_VIEW_TYPE_TAB_CAT = "KEY_VIEW_TYPE_TAB_CAT"
    }

    fun saveListTypeForCatList(type: String) {
        sharedPreferencesManager.setValue(KEY_VIEW_TYPE_CAT_LIST, type)
    }

    fun getListTypeForCatList(): String? {
        return sharedPreferencesManager.getValueString(KEY_VIEW_TYPE_CAT_LIST)
    }

    fun saveSettingsTypeForCatList(type: String) {
        sharedPreferencesManager.setValue(KEY_VIEW_TYPE_SETTINGS_CAT, type)
    }

    fun getSettingsTypeForCatList(): String? {
        return sharedPreferencesManager.getValueString(KEY_VIEW_TYPE_SETTINGS_CAT)
    }

    fun saveSearchTypeForCatList(type: String) {
        sharedPreferencesManager.setValue(KEY_SEARCH_TYPE_CAT, type)
    }

    fun getSearchTypeForCatList(): String? {
        return sharedPreferencesManager.getValueString(KEY_SEARCH_TYPE_CAT)
    }

    fun saveTabTypeForCatDetail(type: String) {
        sharedPreferencesManager.setValue(KEY_VIEW_TYPE_TAB_CAT, type)
    }

    fun getTabTypeForCatDetail(): String? {
        return sharedPreferencesManager.getValueString(KEY_VIEW_TYPE_TAB_CAT)
    }



}