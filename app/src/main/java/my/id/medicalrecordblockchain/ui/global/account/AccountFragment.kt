package my.id.medicalrecordblockchain.ui.global.account

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import my.id.medicalrecordblockchain.data.response.AccountData
import my.id.medicalrecordblockchain.databinding.FragmentAccountBinding
import my.id.medicalrecordblockchain.ui.patient.personal_data.info.PersonalDataInfoActivity
import my.id.medicalrecordblockchain.utils.LoadingDialog
import my.id.medicalrecordblockchain.utils.ResultData

@AndroidEntryPoint
class AccountFragment: Fragment() {
    private var _binding: FragmentAccountBinding? = null
    private val binding get() = _binding!!
    private val viewModel: AccountViewModel by viewModels()
    private var loadingDialog: LoadingDialog? = null

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
        loadingDialog = LoadingDialog(requireContext())
    }

    private fun initListener() {
        binding.tvAction.setOnClickListener {
            PersonalDataInfoActivity.launch(requireContext())
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
                    loadingDialog?.show()
                }

                is ResultData.Success -> {
                    loadingDialog?.dismiss()
                    state.data.data?.let {
                        setupAccountData(it)
                    }
                }

                is ResultData.Failure -> {
                    loadingDialog?.dismiss()
                }
            }
        }
    }

    private fun setupAccountData(data: AccountData) {
        binding.tvName.text = data.name
        binding.tvEmail.text = data.email
        binding.tvAddress.text = data.address
        binding.tvNik.text  = data.identityNumber
    }
}