package my.id.medicalrecordblockchain.ui.patient.home

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
import my.id.medicalrecordblockchain.data.response.ServicesResponse
import my.id.medicalrecordblockchain.utils.ResultData
import javax.inject.Inject

@HiltViewModel
class HomePatientViewModel @Inject constructor(
    private val patientRepository: PatientRepository,
    private val userRepository: UserRepository
) : ViewModel() {
    private val _services = MutableLiveData<ResultData<ServicesResponse>>()
    val services: LiveData<ResultData<ServicesResponse>> get() = _services

    fun getServices() {
        _services.value = ResultData.Loading
        viewModelScope.launch {
            _services.postValue(patientRepository.getServices().also {
                if (it is ResultData.Success) {
                    Preferences.storeServices(it.data.data)
                }
            })
        }
    }

    private val _account = MutableLiveData<ResultData<AccountResponse>>()
    val account: LiveData<ResultData<AccountResponse>> = _account

    fun getAccount() {
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
}