package id.renaldi.alteacarepretest.data.repository.pojo

import com.google.gson.annotations.SerializedName

data class Doctor(
    @SerializedName("doctor_id")
    val doctorId: String,
    @SerializedName("is_popular")
    val isPopular: Boolean,
    @SerializedName("about_preview")
    val aboutPreview: String,
    val name: String,
    val slug: String,
    val about: String,
    val overview: String,
    val photo: Photo,
    val sip: String,
    val experience: String,
    val price: Price,
    val specialization: Specialization,
    val hospital: MutableList<Hospital>
)

data class Hospital(
    val id: String,
    val name: String,
    val image: Photo,
    val icon: Photo
)

data class Specialization(
    val id: String,
    val name: String
)

data class Price(
    val raw: Int,
    val formatted: String
)