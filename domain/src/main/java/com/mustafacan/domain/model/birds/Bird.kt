package com.mustafacan.domain.model.birds

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import com.mustafacan.domain.model.UrlEncodedStringSerializer
import kotlinx.android.parcel.Parcelize
import kotlinx.serialization.Serializable

@Entity(tableName = "favorite_birds")
@Serializable
@Parcelize
data class Bird(
    @PrimaryKey(autoGenerate = false)
    var id: Int? = null,
    var name: String? = null,
    var species: String? = null,
    var family: String? = null,
    var habitat: String? = null,

    @SerializedName("place_of_found")
    var placeOfFound: String? = null,

    var diet: String? = null,
    var description: String? = null,

    @SerializedName("wingspan_cm")
    var wingspanCm: Int? = null,

    @SerializedName("weight_kg")
    var weightKg: Double? = null,

    @Serializable(with = UrlEncodedStringSerializer::class)
    var image: String? = null,
    var isFavorite: Boolean? = false): Parcelable
