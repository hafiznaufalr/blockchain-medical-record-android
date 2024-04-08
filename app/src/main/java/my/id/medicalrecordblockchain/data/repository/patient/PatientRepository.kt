package my.id.medicalrecordblockchain.data.repository.patient

import my.id.medicalrecordblockchain.data.requests.BookAppointmentRequest
import my.id.medicalrecordblockchain.data.response.BasicResponse
import my.id.medicalrecordblockchain.data.response.ListDoctorResponse
import my.id.medicalrecordblockchain.data.response.ServicesResponse
import my.id.medicalrecordblockchain.utils.ResultData

interface PatientRepository {
    suspend fun getServices(): ResultData<ServicesResponse>
    suspend fun getListDoctorByService(serviceId: Int): ResultData<ListDoctorResponse>
    suspend fun bookingAppointment(request: BookAppointmentRequest): ResultData<BasicResponse>
}