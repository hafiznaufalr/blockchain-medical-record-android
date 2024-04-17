package my.id.medicalrecordblockchain.ui.doctor.medical_record.write

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import my.id.medicalrecordblockchain.data.Preferences
import my.id.medicalrecordblockchain.data.repository.doctor.DoctorRepository
import my.id.medicalrecordblockchain.data.requests.WriteMedicalRecordRequest
import my.id.medicalrecordblockchain.data.response.BasicResponse
import my.id.medicalrecordblockchain.data.response.SignInResponse
import my.id.medicalrecordblockchain.utils.ResultData
import javax.inject.Inject

@HiltViewModel
class WriteMedicalRecordViewModel @Inject constructor(
    private val doctorRepository: DoctorRepository
) : ViewModel() {
    private val _writeMedicalRecordValidator = MutableLiveData<String>()
    val writeMedicalRecordValidator: LiveData<String> get() = _writeMedicalRecordValidator

    private val _postWriteMedicalRecord = MutableLiveData<ResultData<BasicResponse>>()
    val postWriteMedicalRecord: LiveData<ResultData<BasicResponse>> get() = _postWriteMedicalRecord

    fun writeMedicalRecord(appointmentId: String, request: WriteMedicalRecordRequest) {
        if (request.diagnose.isEmpty()) {
            _writeMedicalRecordValidator.value = "diagnosa tidak boleh kosong"
            return
        }
        if (request.notes.isEmpty()) {
            _writeMedicalRecordValidator.value = "catatan tidak boleh kosong"
            return
        }
        if (request.prescription.isEmpty()) {
            _writeMedicalRecordValidator.value = "resep tidak boleh kosong"
            return
        }

        _postWriteMedicalRecord.value = ResultData.Loading
        viewModelScope.launch {
            _postWriteMedicalRecord.postValue(
                doctorRepository.writeMedicalRecord(
                    appointmentId = appointmentId,
                    body = request
                )
            )
        }
    }

}