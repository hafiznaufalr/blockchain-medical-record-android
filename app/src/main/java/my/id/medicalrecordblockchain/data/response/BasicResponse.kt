package my.id.medicalrecordblockchain.data.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class BasicResponse(
    @SerializedName("status")
    val status: Boolean?
) : Parcelable