package my.id.medicalrecordblockchain.data.requests


import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import android.os.Parcelable

@Parcelize
data class WriteMedicalRecordRequest(
    @SerializedName("additional_document_path")
    val additionalDocumentPath: String?,
    @SerializedName("diagnose")
    val diagnose: String,
    @SerializedName("doctor_id")
    val doctorId: Int,
    @SerializedName("notes")
    val notes: String,
    @SerializedName("patient_id")
    val patientId: Int,
    @SerializedName("prescription")
    val prescription: String
) : Parcelable