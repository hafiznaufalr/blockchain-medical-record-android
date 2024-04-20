package my.id.medicalrecordblockchain.data.response


import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import android.os.Parcelable

@Parcelize
data class AppointmentResponse(
    @SerializedName("data")
    val `data`: List<AppointmentData> = emptyList(),
    @SerializedName("status")
    val status: Boolean?
) : Parcelable

@Parcelize
data class DetailAppointmentResponse(
    @SerializedName("data")
    val `data`: AppointmentData?,
    @SerializedName("status")
    val status: Boolean?
) : Parcelable

@Parcelize
data class AppointmentData(
    @SerializedName("allergies")
    val allergies: String?,
    @SerializedName("booking_at")
    val bookingAt: String?,
    @SerializedName("doctor_id")
    val doctorId: Int?,
    @SerializedName("diagnose")
    val diagnose: String?,
    @SerializedName("doctor_name")
    val doctorName: String?,
    @SerializedName("health_service_id")
    val healthServiceId: Int?,
    @SerializedName("health_service_name")
    val healthServiceName: String?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("patient_id")
    val patientId: Int?,
    @SerializedName("patient_name")
    val patientName: String?,
    @SerializedName("record_number")
    val recordNumber: String?,
    @SerializedName("schedule_date")
    val scheduleDate: String?,
    @SerializedName("schedule_time")
    val scheduleTime: String?,
    @SerializedName("status")
    val status: String?,
    @SerializedName("symptoms")
    val symptoms: String?
) : Parcelable