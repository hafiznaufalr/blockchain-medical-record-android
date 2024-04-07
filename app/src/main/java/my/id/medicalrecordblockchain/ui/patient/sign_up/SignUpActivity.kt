package my.id.medicalrecordblockchain.ui.patient.sign_up

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint
import my.id.medicalrecordblockchain.data.requests.SignUpRequest
import my.id.medicalrecordblockchain.databinding.ActivitySignUpBinding
import my.id.medicalrecordblockchain.ui.patient.personal_data.form.PersonalDataFormActivity
import my.id.medicalrecordblockchain.utils.SnackBarType
import my.id.medicalrecordblockchain.utils.showSnackBar

@AndroidEntryPoint
class SignUpActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignUpBinding
    private val signUpViewModel: SignUpViewModel by viewModels()
    private var signUpRequest = SignUpRequest()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initListener()
        observer()
    }

    private fun initListener() {
        binding.btnNext.setOnClickListener {
            signUpRequest = SignUpRequest(
                name = binding.etName.text.toString().trim(),
                phone = binding.etPhone.text.toString().trim(),
                email = binding.etEmail.text.toString().trim(),
                password = binding.etPassword.text.toString().trim(),
                address = binding.etAddress.text.toString().trim(),
                identityNumber = binding.etNik.text.toString().trim()
            )
            signUpViewModel.validateSignUpData(signUpRequest)
        }
    }

    private fun observer() {
        signUpViewModel.signUpValidator.observe(this) {
            if (it == "next") {
                PersonalDataFormActivity.launch(
                    context = this,
                    signUpRequest = signUpRequest
                )
                return@observe
            }

            showSnackBar(
                message = it,
                snackBarType = SnackBarType.ERROR
            )
        }
    }

    companion object {
        fun launch(context: Context) {
            context.startActivity(
                Intent(
                    context,
                    SignUpActivity::class.java
                )
            )
        }
    }
}