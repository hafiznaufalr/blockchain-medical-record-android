package my.id.medicalrecordblockchain.ui.patient.personal_data.info

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import my.id.medicalrecordblockchain.data.Preferences
import my.id.medicalrecordblockchain.data.response.AccountData
import javax.inject.Inject

@HiltViewModel
class PersonalDataInfoViewModel @Inject constructor(): ViewModel() {
    private val _account = MutableLiveData<AccountData>()
    val account: LiveData<AccountData> = _account
    fun getAccount() {
        _account.value = Preferences.getAccount()
    }
}