package my.id.medicalrecordblockchain.ui.doctor.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import my.id.medicalrecordblockchain.databinding.FragmentHomeDoctorBinding
import my.id.medicalrecordblockchain.ui.doctor.appointment.AppointmentDoctorAdapter
import my.id.medicalrecordblockchain.ui.global.detail_appointment.DetailAppointmentActivity
import my.id.medicalrecordblockchain.ui.global.home.HomeActivity
import my.id.medicalrecordblockchain.utils.ResultData

@AndroidEntryPoint
class HomeDoctorFragment : Fragment() {
    private var _binding: FragmentHomeDoctorBinding? = null
    private val binding get() = _binding!!
    private val viewModel: HomeDoctorViewModel by viewModels()
    private val newRequestAdapter by lazy {
        AppointmentDoctorAdapter {
            DetailAppointmentActivity.launch(requireContext(), it.id.toString())
        }
    }

    private val todayScheduleAdapter by lazy {
        AppointmentDoctorAdapter {
            DetailAppointmentActivity.launch(requireContext(), it.id.toString())
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeDoctorBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        initData()
        observer()
    }

    private fun init() {
        binding.rvNewRequest.adapter = newRequestAdapter
        binding.rvSchedule.adapter = todayScheduleAdapter
    }

    private fun initData() {
        viewModel.getAccount()
    }

    private fun observer() {
        viewModel.appointmentList.observe(viewLifecycleOwner) { state ->
            when (state) {
                is ResultData.Loading -> {
                    (requireActivity() as HomeActivity).showProgressLinear(true)
                }

                is ResultData.Success -> {
                    (requireActivity() as HomeActivity).showProgressLinear(false)

                    val newRequestData = state.data.data.filter { it.status == "WAITING" }
                    binding.tvEmptyNewRequest.isVisible = newRequestData.isEmpty()
                    newRequestAdapter.setData(newRequestData)

                    val todaySchedule = state.data.data.filter { it.status == "UPCOMING" }
                    binding.tvEmptySchedule.isVisible = todaySchedule.isEmpty()
                    todayScheduleAdapter.setData(todaySchedule)
                }

                is ResultData.Failure -> {
                    (requireActivity() as HomeActivity).showProgressLinear(false)
                }
            }
        }

        viewModel.account.observe(viewLifecycleOwner) { state ->
            when (state) {
                is ResultData.Loading -> {
                }

                is ResultData.Success -> {
                    state.data.data?.let {
                        binding.tvDoctorName.text = "Hi, ${it.name}"
                        binding.tvDoctorType.text = it.healthServiceName
                    }
                }

                is ResultData.Failure -> {
                }
            }
        }

        viewModel.localAccount.observe(viewLifecycleOwner) {
            binding.tvDoctorName.text = "Hi, ${it.name}"
            binding.tvDoctorType.text = it.healthServiceName
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.getAppointmentList()
    }
}