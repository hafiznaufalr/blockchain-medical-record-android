package my.id.medicalrecordblockchain.utils

import retrofit2.Response
import java.io.IOException
import java.net.UnknownHostException

object NetworkHandler {
    suspend fun <T> safeApiCall(apiCall: suspend () -> Response<T>
    ): ResultData<T> {
        return try {
            val response: Response<T> = apiCall()

            if (response.isSuccessful) {
                when(response.code()) {
                    200, 201 -> {
                        ResultData.Success(response.body()!!)
                    }
                    500 -> ResultData.Failure("Server Error")
                    else -> {
                        ResultData.Failure("Server Error: ${response.message()}")
                    }
                }
            } else {
                ResultData.Failure("Unsuccessful response: ${response.code()}")
            }
        } catch (e: IOException) {
            ResultData.Failure(e.localizedMessage)
        } catch (e: UnknownHostException) {
            ResultData.Failure(e.localizedMessage)
        }
    }
}