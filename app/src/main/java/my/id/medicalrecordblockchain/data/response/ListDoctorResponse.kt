package my.id.medicalrecordblockchain.data.response


import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import android.os.Parcelable

@Parcelize
data class ListDoctorResponse(
    @SerializedName("data")
    val `data`: List<DoctorData> = emptyList(),
    @SerializedName("status")
    val status: Boolean?
) : Parcelable

@Parcelize
data class DoctorData(
    @SerializedName("address")
    val address: String?,
    @SerializedName("available_schedule")
    val availableSchedule: DoctorAvailableSchedule?,
    @SerializedName("date_of_birth")
    val dateOfBirth: String?,
    @SerializedName("description")
    val description: String?,
    @SerializedName("doctor_id")
    val doctorId: Int?,
    @SerializedName("email")
    val email: String?,
    @SerializedName("gender")
    val gender: String?,
    @SerializedName("health_service_id")
    val healthServiceId: Int?,
    @SerializedName("health_service_name")
    val healthServiceName: String?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("identity_number")
    val identityNumber: String?,
    @SerializedName("image_url")
    val imageUrl: String?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("phone")
    val phone: String?,
    @SerializedName("place_of_birth")
    val placeOfBirth: String?,
    @SerializedName("type")
    val type: String?
) : Parcelable

@Parcelize
data class DoctorAvailableSchedule(
    @SerializedName("friday")
    val friday: List<String> = emptyList(),
    @SerializedName("monday")
    val monday: List<String> = emptyList(),
    @SerializedName("thursday")
    val thursday: List<String> = emptyList(),
    @SerializedName("tuesday")
    val tuesday: List<String> = emptyList(),
    @SerializedName("wednesday")
    val wednesday: List<String> = emptyList()
) : Parcelable