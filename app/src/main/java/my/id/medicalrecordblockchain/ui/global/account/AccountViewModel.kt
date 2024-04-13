package my.id.medicalrecordblockchain.ui.global.account

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import my.id.medicalrecordblockchain.data.Preferences
import my.id.medicalrecordblockchain.data.repository.user.UserRepository
import my.id.medicalrecordblockchain.data.response.AccountData
import my.id.medicalrecordblockchain.data.response.AccountResponse
import my.id.medicalrecordblockchain.utils.ResultData
import javax.inject.Inject

@HiltViewModel
class AccountViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {
    private val _account = MutableLiveData<ResultData<AccountResponse>>()
    val account: LiveData<ResultData<AccountResponse>> = _account

    private val _localAccount = MutableLiveData<AccountData>()
    val localAccount: LiveData<AccountData> = _localAccount

    fun getAccount() {
        // get local first
        Preferences.getAccount()?.let {
            _localAccount.value = it
        }

        // get from API & save result to local
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