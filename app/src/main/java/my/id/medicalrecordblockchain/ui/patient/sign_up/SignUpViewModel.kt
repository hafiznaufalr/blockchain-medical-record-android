package my.id.medicalrecordblockchain.ui.patient.sign_up

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import my.id.medicalrecordblockchain.data.repository.user.UserRepository
import my.id.medicalrecordblockchain.data.requests.SignUpRequest
import my.id.medicalrecordblockchain.data.response.SignInResponse
import my.id.medicalrecordblockchain.utils.ResultData
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {
    private val _signUpValidator = MutableLiveData<String>()
    val signUpValidator: LiveData<String> get() = _signUpValidator

    fun validateSignUpData(signUpRequest: SignUpRequest) {
        if (signUpRequest.name.isEmpty()) {
            _signUpValidator.value = "Nama tidak boleh kosong"
            return
        }

        if (signUpRequest.phone.isEmpty()) {
            _signUpValidator.value = "Telepon tidak boleh kosong"
            return
        }

        if (signUpRequest.email.isEmpty()) {
            _signUpValidator.value = "Email tidak boleh kosong"
            return
        }

        if (signUpRequest.password.isEmpty()) {
            _signUpValidator.value = "Password tidak boleh kosong"
            return
        }

        if (signUpRequest.address.isEmpty()) {
            _signUpValidator.value = "Alamat tidak boleh kosong"
            return
        }

        if (signUpRequest.identityNumber.isEmpty()) {
            _signUpValidator.value = "NIK tidak boleh kosong"
            return
        }

        _signUpValidator.value = "next"
    }

    fun validatePersonalData(signUpRequest: SignUpRequest) {

        if (signUpRequest.dateOfBirth.isEmpty()) {
            _signUpValidator.value = "Email tidak boleh kosong"
            return
        }

        if (signUpRequest.weight == null) {
            _signUpValidator.value = "Berat badan tidak boleh kosong"
            return
        }

        if (signUpRequest.height == null) {
            _signUpValidator.value = "Tinggi badan tidak boleh kosong"
            return
        }

        if (signUpRequest.allergies.isEmpty()) {
            _signUpValidator.value = "Alergi tidak boleh kosong"
            return
        }

        if (signUpRequest.gender.isEmpty()) {
            _signUpValidator.value = "Gender tidak boleh kosong"
            return
        }

        if (signUpRequest.bloodGroup.isEmpty()) {
            _signUpValidator.value = "Golongan darah tidak boleh kosong"
            return
        }

        signUpRequest.dateOfBirth = signUpRequest.dateOfBirth + "T00:00:00Z"
        postSignUp(signUpRequest)
    }

    private val _postSignUp = MutableLiveData<ResultData<SignInResponse>>()
    val postSignUp: LiveData<ResultData<SignInResponse>> get() = _postSignUp

    private fun postSignUp(signUpRequest: SignUpRequest) {
        _postSignUp.value = ResultData.Loading
        viewModelScope.launch {
            _postSignUp.postValue(userRepository.postSignUp(signUpRequest))
        }
    }


}