package my.id.medicalrecordblockchain.ui.global.account

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import my.id.medicalrecordblockchain.data.Preferences
import my.id.medicalrecordblockchain.data.response.AccountData
import my.id.medicalrecordblockchain.databinding.FragmentAccountBinding
import my.id.medicalrecordblockchain.ui.global.home.HomeActivity
import my.id.medicalrecordblockchain.ui.global.sign_in.SignInActivity
import my.id.medicalrecordblockchain.ui.patient.personal_data.info.PersonalDataInfoActivity
import my.id.medicalrecordblockchain.utils.ResultData
import my.id.medicalrecordblockchain.utils.SnackBarType
import my.id.medicalrecordblockchain.utils.showSnackBar

@AndroidEntryPoint
class AccountFragment : Fragment() {
    private var _binding: FragmentAccountBinding? = null
    private val binding get() = _binding!!
    private val viewModel: AccountViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAccountBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        initListener()
        initData()
        observer()
    }

    private fun init() {
    }

    private fun initListener() {
        binding.tvAction.setOnClickListener {
            PersonalDataInfoActivity.launch(requireContext())
        }

        binding.ivArrowAction.setOnClickListener {
            PersonalDataInfoActivity.launch(requireContext())
        }

        binding.btnSignOut.setOnClickListener {
            Preferences.clear()

            showSnackBar(
                message = "Keluar berhasil",
                snackBarType = SnackBarType.SUCCESS
            )

            Handler(Looper.getMainLooper()).postDelayed({
                SignInActivity.launch(
                    context = requireContext()
                )
                requireActivity().finishAffinity()
            }, 1000)
        }
    }

    private fun initData() {
        viewModel.getAccount()
    }

    private fun observer() {
        viewModel.localAccount.observe(viewLifecycleOwner) {
            setupAccountData(it)
        }

        viewModel.account.observe(viewLifecycleOwner) { state ->
            when (state) {
                is ResultData.Loading -> {
                    (requireActivity() as HomeActivity).showProgressLinear(true)
                }

                is ResultData.Success -> {
                    (requireActivity() as HomeActivity).showProgressLinear(false)
                    state.data.data?.let {
                        setupAccountData(it)
                    }
                }

                is ResultData.Failure -> {
                    (requireActivity() as HomeActivity).showProgressLinear(false)
                }
            }
        }
    }

    private fun setupAccountData(data: AccountData) {
        binding.tvName.text = data.name
        binding.tvEmail.text = data.email
        binding.tvAddress.text = data.address
        binding.tvNik.text = data.identityNumber
    }
}