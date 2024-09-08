package com.mustafacan.domain.model.dogs

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import com.google.gson.annotations.SerializedName
import com.mustafacan.domain.model.UrlEncodedStringSerializer
import kotlinx.android.parcel.Parcelize
import kotlinx.serialization.Serializable

@Entity(tableName = "favorite_dogs")
@Serializable
@Parcelize
data class Dog(
    @SerializedName("id") var id: Int? = null,
    @SerializedName("name") var name: String? = null,
    @SerializedName("breed_group") var breedGroup: String? = null,
    @SerializedName("size") var size: String? = null,
    @SerializedName("lifespan") var lifespan: String? = null,
    @SerializedName("origin") var origin: String? = null,
    @SerializedName("temperament") var temperament: String? = null,
    @SerializedName("colors") var colors: ArrayList<String> = arrayListOf(),
    @SerializedName("description") var description: String? = null,

    @Serializable(with = UrlEncodedStringSerializer::class)
    @SerializedName("image") var image: String? = null
) : Parcelable
