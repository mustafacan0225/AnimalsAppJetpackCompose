package com.mustafacan.core.model.enums

import com.mustafacan.core.R


enum class ViewTypeForList(val resId: Int) {
    LAZY_COLUMN(R.string.view_mode_lazy_column),
    LAZY_VERTICAL_GRID(R.string.view_mode_lazy_vertical_grid)
}