package my.id.medicalrecordblockchain.ui.patient.appointment.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import my.id.medicalrecordblockchain.data.repository.user.UserRepository
import my.id.medicalrecordblockchain.data.response.AppointmentResponse
import my.id.medicalrecordblockchain.utils.ResultData
import javax.inject.Inject

@HiltViewModel
class AppointmentPatientViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    private val _appointmentList = MutableLiveData<ResultData<AppointmentResponse>>()
    val appointmentList: LiveData<ResultData<AppointmentResponse>> = _appointmentList

    fun getAppointmentList(
        scheduleDate: String? = null,
        healthServiceId: String? = null,
        status: String? = null
    ) {
        _appointmentList.value = ResultData.Loading
        viewModelScope.launch {
            _appointmentList.postValue(
                userRepository.getAppointmentPatient(
                    scheduleDate = scheduleDate,
                    healthServiceId = healthServiceId,
                    status = status
                )
            )
        }
    }
}