package com.mustafacan.domain.model.cats

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Cat(
    @SerializedName("id") var id: Int? = null,
    @SerializedName("name") var name: String? = null,
    @SerializedName("origin") var origin: String? = null,
    @SerializedName("temperament") var temperament: String? = null,
    @SerializedName("colors") var colors: ArrayList<String> = arrayListOf(),
    @SerializedName("description") var description: String? = null,
    @SerializedName("image") var image: String? = null
) : Parcelable
