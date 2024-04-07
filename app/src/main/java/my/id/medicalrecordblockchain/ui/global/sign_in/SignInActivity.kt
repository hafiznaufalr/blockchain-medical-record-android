package my.id.medicalrecordblockchain.ui.global.sign_in

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import dagger.hilt.android.AndroidEntryPoint
import my.id.medicalrecordblockchain.data.requests.SignInRequest
import my.id.medicalrecordblockchain.databinding.ActivitySignInBinding
import my.id.medicalrecordblockchain.ui.patient.sign_up.SignUpActivity
import my.id.medicalrecordblockchain.utils.LoadingDialog
import my.id.medicalrecordblockchain.utils.ResultData
import my.id.medicalrecordblockchain.utils.SnackBarType
import my.id.medicalrecordblockchain.utils.decideActionByFlavor
import my.id.medicalrecordblockchain.utils.decideFlavor
import my.id.medicalrecordblockchain.utils.showSnackBar

@AndroidEntryPoint
class SignInActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignInBinding
    private val signInViewModel: SignInViewModel by viewModels()
    private val loadingDialog by lazy { LoadingDialog(this) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
        initListener()
        observer()
    }

    private fun init() {
        decideActionByFlavor(
            patientAction = {

            },
            doctorAction = {
                binding.tvSignUp.isVisible = false
            }
        )
    }

    private fun initListener() {
        binding.tvSignUp.setOnClickListener {
            SignUpActivity.launch(this)
        }

        binding.btnSignIn.setOnClickListener {
            signInViewModel.postSignIn(
                signInRequest = SignInRequest(
                    email = binding.etEmail.text.toString().trim(),
                    password = binding.etPassword.text.toString().trim(),
                    type = decideFlavor()
                )
            )
        }
    }

    private fun observer() {
        signInViewModel.signInValidator.observe(this) { msg ->
            showSnackBar(
                message = msg,
                snackBarType = SnackBarType.ERROR
            )
        }

        signInViewModel.postSignIn.observe(this) { state ->
            when (state) {
                is ResultData.Loading -> {
                    loadingDialog.show()
                }

                is ResultData.Success -> {
                    loadingDialog.dismiss()
                    showSnackBar(
                        state.data.data?.token.orEmpty(),
                        snackBarType = SnackBarType.SUCCESS
                    )
                }

                is ResultData.Failure -> {
                    loadingDialog.dismiss()
                    showSnackBar(
                        state.error,
                        snackBarType = SnackBarType.ERROR
                    )
                }
            }
        }
    }
}