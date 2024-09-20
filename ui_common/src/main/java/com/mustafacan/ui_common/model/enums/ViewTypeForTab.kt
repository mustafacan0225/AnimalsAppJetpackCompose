package com.mustafacan.ui_common.model.enums
import com.mustafacan.ui_common.R


enum class ViewTypeForTab(val resId: Int) {
    TAB_TYPE_CUSTOM_INDICATOR_WITH_ICON(R.string.view_mode_tab_custom_indicator_with_icon),
    TAB_TYPE_CUSTOM_INDICATOR_WITH_LEADING_ICON(R.string.view_mode_tab_custom_indicator_with_leading_icon),
    TAB_TYPE_CUSTOM_INDICATOR_WITHOUT_ICON(R.string.view_mode_tab_custom_indicator_without_icon),
    TAB_TYPE_DEFAULT_INDICATOR_WITH_ICON(R.string.view_mode_tab_default_with_icon),
    TAB_TYPE_DEFAULT_INDICATOR_WITH_LEADING_ICON(R.string.view_mode_tab_default_with_leading_icon),
    TAB_TYPE_DEFAULT_INDICATOR_WITHOUT_ICON(R.string.view_mode_tab_default_without_icon)
}