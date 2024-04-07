package my.id.medicalrecordblockchain.ui.global.sign_in

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import my.id.medicalrecordblockchain.utils.ResultData

class SignInViewModel: ViewModel() {
    private val _signInValidator = MutableLiveData<String>()
    val signInValidator: LiveData<String> get() = _signInValidator

    private val _postSignIn = MutableLiveData<ResultData<Int>>()
    val postSignIn: LiveData<ResultData<Int>> get() = _postSignIn

    fun postSignIn(email: String, password: String) {
        if (email.isEmpty()) {
            _signInValidator.value = "email tidak boleh kosong"
            return
        }
        if (password.isEmpty()) {
            _signInValidator.value = "password tidak boleh kosong"
            return
        }

        _postSignIn.value = ResultData.Loading
    }
}