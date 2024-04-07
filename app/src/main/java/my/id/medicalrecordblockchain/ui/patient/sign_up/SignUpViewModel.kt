package my.id.medicalrecordblockchain.ui.patient.sign_up

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import my.id.medicalrecordblockchain.data.requests.SignUpRequest

class SignUpViewModel: ViewModel() {
    private val _signUpValidator = MutableLiveData<String>()
    val signUpValidator: LiveData<String> get() = _signUpValidator

    fun postSignUp(signUpRequest: SignUpRequest) {
        if (signUpRequest.email.isEmpty()) {
            _signUpValidator.value = "Email tidak boleh kosong"
            return
        }

        if (signUpRequest.password.isEmpty()) {
            _signUpValidator.value = "Password tidak boleh kosong"
            return
        }

        if (signUpRequest.phone.isEmpty()) {
            _signUpValidator.value = "Telepon tidak boleh kosong"
            return
        }

        if (signUpRequest.name.isEmpty()) {
            _signUpValidator.value = "Nama tidak boleh kosong"
            return
        }

        if (signUpRequest.address.isEmpty()) {
            _signUpValidator.value = "Alamat tidak boleh kosong"
            return
        }

        if (signUpRequest.nik.isEmpty()) {
            _signUpValidator.value = "NIK tidak boleh kosong"
            return
        }

    }
}