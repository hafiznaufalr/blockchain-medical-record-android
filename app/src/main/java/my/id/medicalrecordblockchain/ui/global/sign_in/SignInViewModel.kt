package my.id.medicalrecordblockchain.ui.global.sign_in

import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import my.id.medicalrecordblockchain.data.Preferences
import my.id.medicalrecordblockchain.data.repository.UserRepository
import my.id.medicalrecordblockchain.data.requests.SignInRequest
import my.id.medicalrecordblockchain.data.response.SignInResponse
import my.id.medicalrecordblockchain.utils.ResultData
import java.util.regex.Pattern
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    private val _isLoggedIn = MutableLiveData<Boolean>()
    val isLoggedIn: LiveData<Boolean> get() = _isLoggedIn

    fun isLoggedIn() {
        _isLoggedIn.value = Preferences.getToken().isNotEmpty()
    }

    private val _signInValidator = MutableLiveData<String>()
    val signInValidator: LiveData<String> get() = _signInValidator

    private val _postSignIn = MutableLiveData<ResultData<SignInResponse>>()
    val postSignIn: LiveData<ResultData<SignInResponse>> get() = _postSignIn

    fun postSignIn(signInRequest: SignInRequest) {
        if (signInRequest.email.isEmpty()) {
            _signInValidator.value = "email tidak boleh kosong"
            return
        }
        if (Patterns.EMAIL_ADDRESS.matcher(signInRequest.email).matches().not()) {
            _signInValidator.value = "email tidak valid"
            return
        }
        if (signInRequest.password.isEmpty()) {
            _signInValidator.value = "password tidak boleh kosong"
            return
        }

        _postSignIn.value = ResultData.Loading
        viewModelScope.launch {
            val data = userRepository.postSignIn(signInRequest).also {
                if (it is ResultData.Success) {
                    it.data.data?.token?.let { token ->
                        Preferences.storeToken(token)
                    }
                }
            }
            _postSignIn.postValue(data)
        }
    }
}