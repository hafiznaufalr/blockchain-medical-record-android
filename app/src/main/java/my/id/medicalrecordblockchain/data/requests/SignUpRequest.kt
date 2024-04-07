package my.id.medicalrecordblockchain.data.requests

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class SignUpRequest(
    @SerializedName("address")
    val address: String = "",
    @SerializedName("allergies")
    val allergies: String = "",
    @SerializedName("blood_group")
    val bloodGroup: String = "",
    @SerializedName("date_of_birth")
    var dateOfBirth: String = "",
    @SerializedName("email")
    val email: String = "",
    @SerializedName("gender")
    val gender: String = "",
    @SerializedName("height")
    val height: Int? = null,
    @SerializedName("identity_number")
    val identityNumber: String = "",
    @SerializedName("name")
    val name: String = "",
    @SerializedName("password")
    val password: String = "",
    @SerializedName("phone")
    val phone: String = "",
    @SerializedName("type")
    val type: String = "PATIENT",
    @SerializedName("place_of_birth")
    val placeOfBirth: String = "MCHC",
    @SerializedName("weight")
    val weight: Int? = null
) : Parcelable