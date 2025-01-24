package com.mustafacan.core.model.enums

import com.mustafacan.core.R


enum class SearchType(val resId: Int) {
    LOCAL_SEARCH(R.string.search_type_locale),
    REMOTE_SEARCH(R.string.search_type_remote)
}