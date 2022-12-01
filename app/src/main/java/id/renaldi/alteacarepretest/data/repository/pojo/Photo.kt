package id.renaldi.alteacarepretest.data.repository.pojo

import com.google.gson.annotations.SerializedName

data class Photo(
    @SerializedName("size_formatted")
    val sizeFormatted: String,
    val url: String,
    val formats: Formats
)

data class Formats(
    val thumbnail: String,
    val large: String,
    val medium: String,
    val small: String
)