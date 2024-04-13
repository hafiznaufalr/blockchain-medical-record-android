package my.id.medicalrecordblockchain.ui.patient.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import my.id.medicalrecordblockchain.databinding.FragmentHomePatientBinding
import my.id.medicalrecordblockchain.ui.patient.list_doctor.ListDoctorActivity
import my.id.medicalrecordblockchain.utils.LoadingDialog
import my.id.medicalrecordblockchain.utils.ResultData

@AndroidEntryPoint
class HomePatientFragment : Fragment() {
    private var _binding: FragmentHomePatientBinding? = null
    private val binding get() = _binding!!
    private val viewModel: HomePatientViewModel by viewModels()
    private val loadingDialog by lazy { LoadingDialog(requireContext()) }
    private val servicesAdapter by lazy {
        HomePatientServicesAdapter { data ->
            ListDoctorActivity.launch(
                context = requireContext(),
                data = data
            )
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomePatientBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        initData()
        initListener()
        observer()
    }

    private fun init() {
        binding.rvService.adapter = servicesAdapter
    }

    private fun initData() {
        viewModel.getServices()
        viewModel.getAccount()
    }

    private fun initListener() {

    }

    private fun observer() {
        viewModel.services.observe(viewLifecycleOwner) { state ->
            when (state) {
                is ResultData.Loading -> {
                    loadingDialog.show()
                }

                is ResultData.Success -> {
                    loadingDialog.dismiss()
                    servicesAdapter.setData(state.data.data)
                }

                is ResultData.Failure -> {
                    loadingDialog.dismiss()
                }
            }
        }
    }
}