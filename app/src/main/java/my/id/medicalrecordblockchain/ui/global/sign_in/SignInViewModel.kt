package my.id.medicalrecordblockchain.ui.global.sign_in

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import my.id.medicalrecordblockchain.data.repository.UserRepository
import my.id.medicalrecordblockchain.data.requests.SignInRequest
import my.id.medicalrecordblockchain.data.response.SignInResponse
import my.id.medicalrecordblockchain.utils.ResultData
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {
    private val _signInValidator = MutableLiveData<String>()
    val signInValidator: LiveData<String> get() = _signInValidator

    private val _postSignIn = MutableLiveData<ResultData<SignInResponse>>()
    val postSignIn: LiveData<ResultData<SignInResponse>> get() = _postSignIn

    fun postSignIn(signInRequest: SignInRequest) {
        if (signInRequest.email.isEmpty()) {
            _signInValidator.value = "email tidak boleh kosong"
            return
        }
        if (signInRequest.password.isEmpty()) {
            _signInValidator.value = "password tidak boleh kosong"
            return
        }

        _postSignIn.value = ResultData.Loading
        viewModelScope.launch {
            _postSignIn.postValue(userRepository.postSignIn(signInRequest))
        }
    }
}