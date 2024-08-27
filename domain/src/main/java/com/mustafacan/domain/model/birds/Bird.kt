package com.mustafacan.domain.model.birds

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Bird(@SerializedName("id") var id: Int? = null,
    @SerializedName("name") var name: String? = null,
    @SerializedName("species") var species: String? = null,
    @SerializedName("family") var family: String? = null,
    @SerializedName("habitat") var habitat: String? = null,
    @SerializedName("place_of_found") var placeOfFound: String? = null,
    @SerializedName("diet") var diet: String? = null,
    @SerializedName("description") var description: String? = null,
    @SerializedName("wingspan_cm") var wingspanCm: Int? = null,
    @SerializedName("weight_kg") var weightKg: Double? = null,
    @SerializedName("image") var image: String? = null): Parcelable
