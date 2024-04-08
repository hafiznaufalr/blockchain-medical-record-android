package my.id.medicalrecordblockchain.data.repository.patient

import my.id.medicalrecordblockchain.data.APIService
import my.id.medicalrecordblockchain.data.response.ListDoctorResponse
import my.id.medicalrecordblockchain.data.response.ServicesResponse
import my.id.medicalrecordblockchain.utils.NetworkHandler
import my.id.medicalrecordblockchain.utils.ResultData
import javax.inject.Inject

class PatientRepositoryImpl @Inject constructor(
    private val apiService: APIService
) : PatientRepository {
    override suspend fun getServices(): ResultData<ServicesResponse> {
        return NetworkHandler.safeApiCall {
            apiService.getServices()
        }
    }

    override suspend fun getListDoctorByService(serviceId: Int): ResultData<ListDoctorResponse> {
        return NetworkHandler.safeApiCall {
            apiService.getListDoctorByServices(
                serviceId = serviceId
            )
        }
    }
}