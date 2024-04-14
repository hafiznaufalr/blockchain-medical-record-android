package my.id.medicalrecordblockchain.ui.patient.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import my.id.medicalrecordblockchain.databinding.FragmentHomePatientBinding
import my.id.medicalrecordblockchain.ui.global.home.HomeActivity
import my.id.medicalrecordblockchain.ui.patient.list_doctor.ListDoctorActivity
import my.id.medicalrecordblockchain.utils.LoadingDialog
import my.id.medicalrecordblockchain.utils.ResultData

@AndroidEntryPoint
class HomePatientFragment : Fragment() {
    private var _binding: FragmentHomePatientBinding? = null
    private val binding get() = _binding!!
    private val viewModel: HomePatientViewModel by viewModels()
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
                    (requireActivity() as HomeActivity).showProgressLinear(true)
                }

                is ResultData.Success -> {
                    (requireActivity() as HomeActivity).showProgressLinear(false)
                    servicesAdapter.setData(state.data.data)
                }

                is ResultData.Failure -> {
                    (requireActivity() as HomeActivity).showProgressLinear(false)
                }
            }
        }
    }
}