package my.id.medicalrecordblockchain.data.response


import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import android.os.Parcelable

@Parcelize
data class AccountResponse(
    @SerializedName("data")
    val `data`: AccountData?,
    @SerializedName("status")
    val status: Boolean?
) : Parcelable


@Parcelize
data class AccountData(
    @SerializedName("address")
    val address: String?,
    @SerializedName("allergies")
    val allergies: String?,
    @SerializedName("blood_group")
    val bloodGroup: String?,
    @SerializedName("date_of_birth")
    val dateOfBirth: String?,
    @SerializedName("email")
    val email: String?,
    @SerializedName("gender")
    val gender: String?,
    @SerializedName("height")
    val height: Int?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("identity_number")
    val identityNumber: String?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("patient_id")
    val patientId: Int?,
    @SerializedName("phone")
    val phone: String?,
    @SerializedName("place_of_birth")
    val placeOfBirth: String?,
    @SerializedName("type")
    val type: String?,
    @SerializedName("weight")
    val weight: Int?
) : Parcelable