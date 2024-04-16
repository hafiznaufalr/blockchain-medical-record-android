package my.id.medicalrecordblockchain.data

import my.id.medicalrecordblockchain.data.requests.BookAppointmentRequest
import my.id.medicalrecordblockchain.data.requests.SignInRequest
import my.id.medicalrecordblockchain.data.requests.SignUpRequest
import my.id.medicalrecordblockchain.data.response.AccountResponse
import my.id.medicalrecordblockchain.data.response.AppointmentResponse
import my.id.medicalrecordblockchain.data.response.BasicResponse
import my.id.medicalrecordblockchain.data.response.DetailAppointmentResponse
import my.id.medicalrecordblockchain.data.response.ListDoctorResponse
import my.id.medicalrecordblockchain.data.response.ServicesResponse
import my.id.medicalrecordblockchain.data.response.SignInResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

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

    @GET("/v1/services/{serviceId}/doctors")
    suspend fun getListDoctorByServices(
        @Header("Authorization") token: String = "Bearer ${Preferences.getToken()}",
        @Path("serviceId") serviceId: Int
    ): Response<ListDoctorResponse>

    @POST("/v1/appointments")
    suspend fun bookingAppointment(
        @Header("Authorization") token: String = "Bearer ${Preferences.getToken()}",
        @Body bookAppointmentRequest: BookAppointmentRequest
    ): Response<BasicResponse>

    @GET("/v1/users/details")
    suspend fun getAccount(
        @Header("Authorization") token: String = "Bearer ${Preferences.getToken()}"
    ): Response<AccountResponse>

    @GET("/v1/appointments")
    suspend fun getListAppointments(
        @Header("Authorization") token: String = "Bearer ${Preferences.getToken()}",
        @Query("schedule_date") scheduleDate: String? = null,
        @Query("health_service_id") healthServiceId: String? = null,
        @Query("status") status: String? = null,
        @Query("is_doctor") isDoctor: Boolean? = null,
        @Query("patient_id") patientId: String? = null
    ): Response<AppointmentResponse>

    @GET("/v1/appointments/{id}")
    suspend fun getAppointmentById(
        @Header("Authorization") token: String = "Bearer ${Preferences.getToken()}",
        @Path("id") id: String
    ): Response<DetailAppointmentResponse>
}