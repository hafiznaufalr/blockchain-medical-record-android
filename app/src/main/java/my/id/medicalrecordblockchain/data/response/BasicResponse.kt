package my.id.medicalrecordblockchain.data.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class BasicResponse(
    @SerializedName("status")
    val status: Boolean?,
    @SerializedName("data")
    val data: BasicData?
) : Parcelable

@Parcelize
data class BasicData(
    @SerializedName("id")
    val id: Int?
) : Parcelable