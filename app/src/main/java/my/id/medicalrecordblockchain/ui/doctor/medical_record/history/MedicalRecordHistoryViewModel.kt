package my.id.medicalrecordblockchain.ui.doctor.medical_record.history

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import my.id.medicalrecordblockchain.data.repository.doctor.DoctorRepository
import my.id.medicalrecordblockchain.data.repository.user.UserRepository
import my.id.medicalrecordblockchain.data.response.AppointmentResponse
import my.id.medicalrecordblockchain.data.response.BasicResponse
import my.id.medicalrecordblockchain.utils.ResultData
import javax.inject.Inject

@HiltViewModel
class MedicalRecordHistoryViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {
    private val _appointmentList = MutableLiveData<ResultData<AppointmentResponse>>()
    val appointmentList: LiveData<ResultData<AppointmentResponse>> = _appointmentList

    fun getAppointmentList(date: String? = null, service: String? = null) {
        _appointmentList.value = ResultData.Loading
        viewModelScope.launch {
            _appointmentList.postValue(
                userRepository.getAppointmentPatient(
                    status = "DONE",
                    isDoctor = true,
                    scheduleDate = date,
                    healthServiceId = service
                )
            )
        }
    }
}