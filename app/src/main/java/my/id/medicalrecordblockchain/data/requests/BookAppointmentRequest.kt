package my.id.medicalrecordblockchain.data.requests


import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import android.os.Parcelable

@Parcelize
data class BookAppointmentRequest(
    @SerializedName("doctor_id")
    val doctorId: Int? = null,
    @SerializedName("health_service_id")
    val healthServiceId: Int? = null,
    @SerializedName("schedule_date")
    val scheduleDate: String? = null,
    @SerializedName("schedule_time")
    val scheduleTime: String? = null,
    @SerializedName("symptoms")
    val symptoms: String? = null
) : Parcelable