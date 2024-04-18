package my.id.medicalrecordblockchain.data.repository.doctor

import my.id.medicalrecordblockchain.data.requests.WriteMedicalRecordRequest
import my.id.medicalrecordblockchain.data.response.AccountResponse
import my.id.medicalrecordblockchain.data.response.BasicResponse
import my.id.medicalrecordblockchain.data.response.MedicalRecordResponse
import my.id.medicalrecordblockchain.utils.ResultData

interface DoctorRepository {
    suspend fun getMedicalRecordById(appointmentId: String): ResultData<MedicalRecordResponse>
    suspend fun writeMedicalRecord(appointmentId: String, body: WriteMedicalRecordRequest): ResultData<BasicResponse>
    suspend fun getPatientById(patientId: String): ResultData<AccountResponse>
}