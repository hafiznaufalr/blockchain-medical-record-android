package my.id.medicalrecordblockchain.ui.global.detail_appointment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import my.id.medicalrecordblockchain.data.repository.user.UserRepository
import my.id.medicalrecordblockchain.data.requests.UpdateAppointmentRequest
import my.id.medicalrecordblockchain.data.response.BasicResponse
import my.id.medicalrecordblockchain.data.response.DetailAppointmentResponse
import my.id.medicalrecordblockchain.utils.ResultData
import javax.inject.Inject

@HiltViewModel
class DetailAppointmentViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {
    private val _appointment = MutableLiveData<ResultData<DetailAppointmentResponse>>()
    val appointment: LiveData<ResultData<DetailAppointmentResponse>> = _appointment

    fun getAppointmentById(appointmentId: String) {
        _appointment.value = ResultData.Loading
        viewModelScope.launch {
            _appointment.postValue(
                userRepository.getAppointmentById(
                    appointmentId = appointmentId
                )
            )
        }
    }

    private val _updateAppointmentStatus = MutableLiveData<ResultData<BasicResponse>>()
    val updateAppointmentStatus: LiveData<ResultData<BasicResponse>> = _updateAppointmentStatus

    fun updateAppointmentStatus(appointmentId: String, status: String) {
        _updateAppointmentStatus.value = ResultData.Loading
        viewModelScope.launch {
            _updateAppointmentStatus.postValue(
                userRepository.updateAppointmentStatus(
                    appointmentId = appointmentId,
                    request = UpdateAppointmentRequest(
                        status = status
                    )
                )
            )
        }
    }
}