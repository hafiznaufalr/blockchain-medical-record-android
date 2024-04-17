package my.id.medicalrecordblockchain.data.repository.doctor

import my.id.medicalrecordblockchain.data.APIService
import my.id.medicalrecordblockchain.data.requests.WriteMedicalRecordRequest
import my.id.medicalrecordblockchain.data.response.BasicResponse
import my.id.medicalrecordblockchain.utils.NetworkHandler
import my.id.medicalrecordblockchain.utils.ResultData
import javax.inject.Inject

class DoctorRepositoryImpl @Inject constructor(
    private val apiService: APIService
) : DoctorRepository {
    override suspend fun getMedicalRecordHistory(appointmentId: String): ResultData<BasicResponse> {
        return NetworkHandler.safeApiCall {
            apiService.getHistoryMedicalRecord(
                appointmentId = appointmentId
            )
        }
    }

    override suspend fun writeMedicalRecord(appointmentId: String, body: WriteMedicalRecordRequest): ResultData<BasicResponse> {
        return NetworkHandler.safeApiCall {
            apiService.writeMedicalRecord(
                appointmentId = appointmentId,
                body = body
            )
        }
    }

}