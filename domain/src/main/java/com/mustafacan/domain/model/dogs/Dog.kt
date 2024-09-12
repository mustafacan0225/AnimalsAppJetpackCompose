package com.mustafacan.domain.model.dogs

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import com.google.gson.reflect.TypeToken
import com.mustafacan.domain.model.UrlEncodedStringSerializer
import kotlinx.android.parcel.Parcelize
import kotlinx.serialization.Serializable
import java.lang.reflect.Type


@Entity(tableName = "favorite_dogs")
@Serializable
@Parcelize
data class Dog(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo("id")
    @SerializedName("id") var id: Int? = null,

    @ColumnInfo("name")
    @SerializedName("name") var name: String? = null,

    @ColumnInfo("breed_group")
    @SerializedName("breed_group") var breedGroup: String? = null,

    @ColumnInfo("size")
    @SerializedName("size") var size: String? = null,

    @ColumnInfo("lifespan")
    @SerializedName("lifespan") var lifespan: String? = null,

    @ColumnInfo("origin")
    @SerializedName("origin") var origin: String? = null,

    @ColumnInfo("temperament")
    @SerializedName("temperament") var temperament: String? = null,

    @ColumnInfo("colors")
    @SerializedName("colors") var colors: ArrayList<String> = arrayListOf(),

    @ColumnInfo("description")
    @SerializedName("description") var description: String? = null,

    @ColumnInfo("image")
    @Serializable(with = UrlEncodedStringSerializer::class)
    @SerializedName("image") var image: String? = null,

    @ColumnInfo("isFavorite") var isFavorite: Boolean? = false,
) : Parcelable

object AnimalColorsConverters {
    @TypeConverter
    fun fromString(value: String?): ArrayList<String> {
        val listType: Type = object : TypeToken<ArrayList<String?>?>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun fromArrayList(list: ArrayList<String?>?): String {
        val gson = Gson()
        val json = gson.toJson(list)
        return json
    }
}