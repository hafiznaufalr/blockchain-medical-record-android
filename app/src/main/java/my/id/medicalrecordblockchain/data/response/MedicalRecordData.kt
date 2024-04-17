package my.id.medicalrecordblockchain.data.response


import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import android.os.Parcelable

@Parcelize
data class MedicalRecordData(
    @SerializedName("additional_document_path")
    val additionalDocumentPath: String?,
    @SerializedName("appointment_id")
    val appointmentId: Int?,
    @SerializedName("created_at")
    val createdAt: String?,
    @SerializedName("diagnose")
    val diagnose: String?,
    @SerializedName("doctor_id")
    val doctorId: Int?,
    @SerializedName("medical_record_number")
    val medicalRecordNumber: String?,
    @SerializedName("notes")
    val notes: String?,
    @SerializedName("patient_id")
    val patientId: Int?,
    @SerializedName("prescription")
    val prescription: String?
) : Parcelable