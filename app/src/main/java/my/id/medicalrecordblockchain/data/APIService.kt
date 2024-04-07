package my.id.medicalrecordblockchain.data

import my.id.medicalrecordblockchain.data.requests.SignInRequest
import my.id.medicalrecordblockchain.data.requests.SignUpRequest
import my.id.medicalrecordblockchain.data.response.ServicesResponse
import my.id.medicalrecordblockchain.data.response.SignInResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface APIService {
    @POST("/v1/users/register")
    suspend fun postSignUp(
        @Body signUpRequest: SignUpRequest
    ): Response<SignInResponse>

    @POST("/v1/users/login")
    suspend fun postSignIn(
        @Body signInRequest: SignInRequest
    ): Response<SignInResponse>

    @GET("/v1/services")
    suspend fun getServices(
        @Header("Authorization") token: String = "Bearer ${Preferences.getToken()}"
    ): Response<ServicesResponse>
}