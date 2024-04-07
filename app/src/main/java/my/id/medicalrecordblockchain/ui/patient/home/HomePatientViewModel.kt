package my.id.medicalrecordblockchain.ui.patient.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import my.id.medicalrecordblockchain.data.repository.patient.PatientRepository
import my.id.medicalrecordblockchain.data.response.ServicesResponse
import my.id.medicalrecordblockchain.utils.ResultData
import javax.inject.Inject

@HiltViewModel
class HomePatientViewModel @Inject constructor(
    private val patientRepository: PatientRepository
) : ViewModel() {
    private val _services = MutableLiveData<ResultData<ServicesResponse>>()
    val services: LiveData<ResultData<ServicesResponse>> get() = _services

    fun getServices() {
        _services.value = ResultData.Loading
        viewModelScope.launch {
            _services.postValue(patientRepository.getServices())
        }
    }
}