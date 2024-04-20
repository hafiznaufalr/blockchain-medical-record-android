package my.id.medicalrecordblockchain.ui.doctor.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import my.id.medicalrecordblockchain.data.Preferences
import my.id.medicalrecordblockchain.data.repository.patient.PatientRepository
import my.id.medicalrecordblockchain.data.repository.user.UserRepository
import my.id.medicalrecordblockchain.data.response.AccountData
import my.id.medicalrecordblockchain.data.response.AccountResponse
import my.id.medicalrecordblockchain.data.response.AppointmentResponse
import my.id.medicalrecordblockchain.data.response.ServicesResponse
import my.id.medicalrecordblockchain.utils.ResultData
import javax.inject.Inject

@HiltViewModel
class HomeDoctorViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val patientRepository: PatientRepository
) : ViewModel() {
    private val _appointmentList = MutableLiveData<ResultData<AppointmentResponse>>()
    val appointmentList: LiveData<ResultData<AppointmentResponse>> = _appointmentList

    fun getAppointmentList(
        scheduleDate: String? = null,
        status: String? = null
    ) {
        _appointmentList.value = ResultData.Loading
        viewModelScope.launch {
            _appointmentList.postValue(
                userRepository.getAppointmentPatient(
                    scheduleDate = scheduleDate,
                    status = status,
                    isDoctor = true
                )
            )
        }
    }

    private val _account = MutableLiveData<ResultData<AccountResponse>>()
    val account: LiveData<ResultData<AccountResponse>> = _account

    private val _localAccount = MutableLiveData<AccountData>()
    val localAccount: LiveData<AccountData> = _localAccount

    fun getAccount() {
        Preferences.getAccount()?.let {
            _localAccount.value = it
        }

        _account.value = ResultData.Loading
        viewModelScope.launch {
            val data = userRepository.getAccount().also {
                if (it is ResultData.Success) {
                    Preferences.storeAccount(it.data.data)
                }
            }
            _account.postValue(data)
        }
    }

    private val _services = MutableLiveData<ResultData<ServicesResponse>>()
    val services: LiveData<ResultData<ServicesResponse>> get() = _services

    fun getServices() {
        _services.value = ResultData.Loading
        viewModelScope.launch {
            patientRepository.getServices().also {
                if (it is ResultData.Success) {
                    Preferences.storeServices(it.data.data)
                }
            }
        }
    }
}