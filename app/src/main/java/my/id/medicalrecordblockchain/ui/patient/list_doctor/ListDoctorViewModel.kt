package my.id.medicalrecordblockchain.ui.patient.list_doctor

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import my.id.medicalrecordblockchain.data.repository.patient.PatientRepository
import my.id.medicalrecordblockchain.data.response.ListDoctorResponse
import my.id.medicalrecordblockchain.utils.ResultData
import javax.inject.Inject

@HiltViewModel
class ListDoctorViewModel @Inject constructor(
    private val patientRepository: PatientRepository
) : ViewModel() {
    private val _listDoctorByService = MutableLiveData<ResultData<ListDoctorResponse>>()
    val listDoctorByService: LiveData<ResultData<ListDoctorResponse>> get() = _listDoctorByService

    fun getListDoctorByService(serviceId: Int) {
        _listDoctorByService.value = ResultData.Loading
        viewModelScope.launch {
            _listDoctorByService.postValue(patientRepository.getListDoctorByService(serviceId))
        }
    }
}