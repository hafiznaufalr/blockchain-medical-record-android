package my.id.medicalrecordblockchain.data

import my.id.medicalrecordblockchain.data.requests.BookAppointmentRequest
import my.id.medicalrecordblockchain.data.requests.SignInRequest
import my.id.medicalrecordblockchain.data.requests.SignUpRequest
import my.id.medicalrecordblockchain.data.requests.UpdateAppointmentRequest
import my.id.medicalrecordblockchain.data.requests.WriteMedicalRecordRequest
import my.id.medicalrecordblockchain.data.response.AccountResponse
import my.id.medicalrecordblockchain.data.response.AppointmentResponse
import my.id.medicalrecordblockchain.data.response.BasicResponse
import my.id.medicalrecordblockchain.data.response.DetailAppointmentResponse
import my.id.medicalrecordblockchain.data.response.ExportResponse
import my.id.medicalrecordblockchain.data.response.ListDoctorResponse
import my.id.medicalrecordblockchain.data.response.MedicalRecordResponse
import my.id.medicalrecordblockchain.data.response.ServicesResponse
import my.id.medicalrecordblockchain.data.response.SignInResponse
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
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

    @GET("/v1/users/patient/{id}")
    suspend fun getPatientById(
        @Header("Authorization") token: String = "Bearer ${Preferences.getToken()}",
        @Path("id") patientId: String
    ): Response<AccountResponse>

    @GET("/v1/appointments")
    suspend fun getListAppointments(
        @Header("Authorization") token: String = "Bearer ${Preferences.getToken()}",
        @Query("schedule_date") scheduleDate: String? = null,
        @Query("health_service_id") healthServiceId: String? = null,
        @Query("status") status: String? = null,
        @Query("is_doctor") isDoctor: Boolean? = null,
        @Query("patient_id") patientId: String? = null,
        @Query("appointment_id") appointmentId: String? = null
    ): Response<AppointmentResponse>

    @GET("/v1/appointments/details/{id}")
    suspend fun getAppointmentById(
        @Header("Authorization") token: String = "Bearer ${Preferences.getToken()}",
        @Path("id") id: String
    ): Response<DetailAppointmentResponse>

    @PATCH("/v1/appointments/{id}")
    suspend fun updateAppointmentStatus(
        @Header("Authorization") token: String = "Bearer ${Preferences.getToken()}",
        @Path("id") id: String,
        @Body body: UpdateAppointmentRequest
    ): Response<DetailAppointmentResponse>

    @GET("/v1/appointments/{id}")
    suspend fun getMedicalRecordById(
        @Header("Authorization") token: String = "Bearer ${Preferences.getToken()}",
        @Path("id") appointmentId: String
    ): Response<MedicalRecordResponse>

    @PUT("/v1/appointments/{id}")
    suspend fun writeMedicalRecord(
        @Header("Authorization") token: String = "Bearer ${Preferences.getToken()}",
        @Path("id") appointmentId: String,
        @Body body: WriteMedicalRecordRequest
    ): Response<BasicResponse>

    @GET("/v1/appointments/export/{id}")
    suspend fun exportMedicalRecord(
        @Header("Authorization") token: String = "Bearer ${Preferences.getToken()}",
        @Path("id") appointmentId: String
    ): Response<ExportResponse>

    @Multipart
    @POST("/v1/appointments/upload")
    suspend fun postImage(
        @Header("Authorization") token: String = "Bearer ${Preferences.getToken()}",
        @Part file: MultipartBody.Part
    ): Response<BasicResponse>
}