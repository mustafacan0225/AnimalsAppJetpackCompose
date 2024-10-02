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
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo("id")
    @SerializedName("id") var id: Int? = null,

    @SerializedName("name") var name: String? = null,
    @SerializedName("species") var species: String? = null,
    @SerializedName("family") var family: String? = null,
    @SerializedName("habitat") var habitat: String? = null,
    @SerializedName("place_of_found") var placeOfFound: String? = null,
    @SerializedName("diet") var diet: String? = null,
    @SerializedName("description") var description: String? = null,
    @SerializedName("wingspan_cm") var wingspanCm: Int? = null,
    @SerializedName("weight_kg") var weightKg: Double? = null,

    @ColumnInfo("image")
    @Serializable(with = UrlEncodedStringSerializer::class)
    @SerializedName("image") var image: String? = null,
    @ColumnInfo("isFavorite") var isFavorite: Boolean? = false): Parcelable
