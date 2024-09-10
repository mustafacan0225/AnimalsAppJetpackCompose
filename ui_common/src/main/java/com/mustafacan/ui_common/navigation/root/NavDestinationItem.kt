package com.mustafacan.ui_common.navigation.root

import android.os.Bundle
import android.os.Parcelable
import androidx.navigation.NavType
import com.mustafacan.ui_common.R
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import com.mustafacan.domain.model.dogs.Dog

@Serializable
sealed class NavDestinationItem(var titleResource: Int, var icon: Int? = null) {

    @Serializable
    object Dogs : NavDestinationItem(R.string.bottom_menu_title_dogs, R.drawable.dog)

    @Serializable
    data class DogDetailScreen(
        val dog: Dog
    )

    @Serializable
    object Cats : NavDestinationItem(R.string.bottom_menu_title_cats, R.drawable.kitten)

    @Serializable
    object Birds : NavDestinationItem(R.string.bottom_menu_title_birds, R.drawable.bird)


}

inline fun <reified T : Parcelable> NavType.Companion.fromCustom(): NavType<T> {
    return object : NavType<T>(
        isNullableAllowed = false
    ) {
        override fun put(bundle: Bundle, key: String, value: T) {
            bundle.putParcelable(key, value)
        }

        override fun get(bundle: Bundle, key: String): T? {
            return if (android.os.Build.VERSION.SDK_INT < 34) {
                @Suppress("DEPRECATION")
                bundle.getParcelable(key)
            } else {
                bundle.getParcelable(key, T::class.java)
            }
        }

        override fun parseValue(value: String): T {
            return Json.decodeFromString<T>(value)
        }

        override fun serializeAsValue(value: T): String {
            return Json.encodeToString(value)
        }

        override val name = T::class.java.name
    }
}