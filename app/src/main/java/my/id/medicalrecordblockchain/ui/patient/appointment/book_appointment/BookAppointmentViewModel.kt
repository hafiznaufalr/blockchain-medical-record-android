package my.id.medicalrecordblockchain.ui.patient.appointment.book_appointment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import my.id.medicalrecordblockchain.data.repository.patient.PatientRepository
import my.id.medicalrecordblockchain.data.requests.BookAppointmentRequest
import my.id.medicalrecordblockchain.data.response.BasicResponse
import my.id.medicalrecordblockchain.utils.ResultData
import javax.inject.Inject

@HiltViewModel
class BookAppointmentViewModel @Inject constructor(
    private val patientRepository: PatientRepository
): ViewModel() {

    private val _bookingValidator = MutableLiveData<String>()
    val bookingValidator: LiveData<String> get() = _bookingValidator

    private val _bookingAppointment = MutableLiveData<ResultData<BasicResponse>>()
    val bookingAppointment: LiveData<ResultData<BasicResponse>> get() = _bookingAppointment

    fun bookingAppointment(request: BookAppointmentRequest) {
        if (request.symptoms.isNullOrEmpty()) {
            _bookingValidator.value = "Keluhan tidak boleh kosong"
            return
        }

        _bookingAppointment.value = ResultData.Loading
        viewModelScope.launch {
            _bookingAppointment.postValue(patientRepository.bookingAppointment(request))
        }
    }
}