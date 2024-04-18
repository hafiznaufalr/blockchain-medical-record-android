package my.id.medicalrecordblockchain.data.repository.doctor

import my.id.medicalrecordblockchain.data.APIService
import my.id.medicalrecordblockchain.data.requests.WriteMedicalRecordRequest
import my.id.medicalrecordblockchain.data.response.AccountResponse
import my.id.medicalrecordblockchain.data.response.BasicResponse
import my.id.medicalrecordblockchain.data.response.MedicalRecordResponse
import my.id.medicalrecordblockchain.utils.NetworkHandler
import my.id.medicalrecordblockchain.utils.ResultData
import java.lang.IllegalStateException
import javax.inject.Inject

class DoctorRepositoryImpl @Inject constructor(
    private val apiService: APIService
) : DoctorRepository {

    override suspend fun getMedicalRecordById(appointmentId: String): ResultData<MedicalRecordResponse> {
        return NetworkHandler.safeApiCall {
            apiService.getMedicalRecordById(
                appointmentId = appointmentId
            )
        }
    }

    override suspend fun writeMedicalRecord(
        appointmentId: String,
        body: WriteMedicalRecordRequest
    ): ResultData<BasicResponse> {
        return NetworkHandler.safeApiCall {
            apiService.writeMedicalRecord(
                appointmentId = appointmentId,
                body = body
            )
        }
    }

    override suspend fun getPatientById(patientId: String): ResultData<AccountResponse> {
        return NetworkHandler.safeApiCall {
            apiService.getPatientById(
                patientId = patientId
            )
        }
    }

}