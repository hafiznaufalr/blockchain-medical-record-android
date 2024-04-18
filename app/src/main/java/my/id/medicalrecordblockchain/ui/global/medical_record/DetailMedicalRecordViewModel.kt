package my.id.medicalrecordblockchain.ui.global.medical_record

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import my.id.medicalrecordblockchain.data.repository.doctor.DoctorRepository
import my.id.medicalrecordblockchain.data.response.AccountResponse
import my.id.medicalrecordblockchain.data.response.MedicalRecordResponse
import my.id.medicalrecordblockchain.utils.ResultData
import javax.inject.Inject

@HiltViewModel
class DetailMedicalRecordViewModel @Inject constructor(
    private val doctorRepository: DoctorRepository
) : ViewModel() {
    private val _medicalRecord = MutableLiveData<ResultData<MedicalRecordResponse>>()
    val medicalRecord: LiveData<ResultData<MedicalRecordResponse>> = _medicalRecord

    fun getMedicalRecordById(appointmentId: String) {
        _medicalRecord.value = ResultData.Loading
        viewModelScope.launch {
            _medicalRecord.postValue(
                doctorRepository.getMedicalRecordById(
                    appointmentId = appointmentId
                )
            )
        }
    }

    private val _patientById = MutableLiveData<ResultData<AccountResponse>>()
    val patientById: LiveData<ResultData<AccountResponse>> = _patientById

    fun getPatientById(patientId: String) {
        _patientById.value = ResultData.Loading
        viewModelScope.launch {
            _patientById.postValue(
                doctorRepository.getPatientById(
                    patientId = patientId
                )
            )
        }
    }
}