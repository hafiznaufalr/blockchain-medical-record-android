package my.id.medicalrecordblockchain.data.response


import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import android.os.Parcelable

@Parcelize
data class ServicesResponse(
    @SerializedName("data")
    val `data`: List<ServicesData> = emptyList(),
    @SerializedName("status")
    val status: Boolean?
) : Parcelable

@Parcelize
data class ServicesData(
    @SerializedName("created_at")
    val createdAt: String?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("image_url")
    val imageUrl: String?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("updated_at")
    val updatedAt: String?
) : Parcelable