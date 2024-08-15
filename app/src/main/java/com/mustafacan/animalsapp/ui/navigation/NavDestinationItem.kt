package com.mustafacan.animalsapp.ui.navigation

import com.mustafacan.animalsapp.R

sealed class NavDestinationItem(var titleResource: Int, var icon: Int? = null, var route: String) {
    object Dogs : NavDestinationItem(R.string.bottom_menu_title_dogs, R.drawable.dog, "dogs/")
    object DogsDetail : NavDestinationItem(titleResource = R.string.bottom_menu_title_dogs, route = "dogsDetail/")
    object Cats : NavDestinationItem(R.string.bottom_menu_title_cats, R.drawable.kitten, "cats/")
    object Birds : NavDestinationItem(R.string.bottom_menu_title_birds, R.drawable.bird, "birds/")

}
