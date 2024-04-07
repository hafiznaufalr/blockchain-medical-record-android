package my.id.medicalrecordblockchain.data.response


import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import android.os.Parcelable

@Parcelize
data class SignInResponse(
    @SerializedName("data")
    val `data`: SignInData?,
    @SerializedName("status")
    val status: Boolean?
) : Parcelable

@Parcelize
data class SignInData(
    @SerializedName("id")
    val id: Int?,
    @SerializedName("token")
    val token: String?,
    @SerializedName("type")
    val type: String?
) : Parcelable