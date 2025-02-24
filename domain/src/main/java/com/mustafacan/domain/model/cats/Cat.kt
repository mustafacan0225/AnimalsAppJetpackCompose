package com.mustafacan.domain.model.cats

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.mustafacan.domain.model.serializer.UrlEncodedStringSerializer
import kotlinx.android.parcel.Parcelize
import kotlinx.serialization.Serializable

@Entity(tableName = "favorite_cats")
@Serializable
@Parcelize
data class Cat(
    @PrimaryKey(autoGenerate = false)
    var id: Int? = null,
    var name: String? = null,
    var origin: String? = null,
    var temperament: String? = null,
    var colors: ArrayList<String> = arrayListOf(),
    var description: String? = null,
    @Serializable(with = UrlEncodedStringSerializer::class)
    var image: String? = null,
    var isFavorite: Boolean? = false,
) : Parcelable {
    fun getTemperamentList(): List<String> {

        if ((this.temperament?: "").contains(",")) {
            var list = this.temperament?.replace(" ", "")?.split(",")
            list?.let {
                return it
            }
        }

        return listOf(this.temperament?: "")
    }
}
