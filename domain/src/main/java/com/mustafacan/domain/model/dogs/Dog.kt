package com.mustafacan.domain.model.dogs

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import com.google.gson.reflect.TypeToken
import com.mustafacan.domain.model.serializer.UrlEncodedStringSerializer
import kotlinx.android.parcel.Parcelize
import kotlinx.serialization.Serializable
import java.lang.reflect.Type


@Entity(tableName = "favorite_dogs")
@Serializable
@Parcelize
data class Dog(
    @PrimaryKey(autoGenerate = false)
    var id: Int? = null,
    var name: String? = null,
    @SerializedName("breed_group")
    var breedGroup: String? = null,
    var size: String? = null,
    var lifespan: String? = null,
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