package my.id.medicalrecordblockchain.data.repository.doctor

import my.id.medicalrecordblockchain.data.requests.WriteMedicalRecordRequest
import my.id.medicalrecordblockchain.data.response.BasicResponse
import my.id.medicalrecordblockchain.utils.ResultData

interface DoctorRepository {
    suspend fun getMedicalRecordHistory(appointmentId: String): ResultData<BasicResponse>
    suspend fun writeMedicalRecord(appointmentId: String, body: WriteMedicalRecordRequest): ResultData<BasicResponse>
}