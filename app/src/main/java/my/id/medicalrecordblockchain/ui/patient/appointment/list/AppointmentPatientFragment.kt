package my.id.medicalrecordblockchain.ui.patient.appointment.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import my.id.medicalrecordblockchain.databinding.FragmentAppointmentPatientBinding
import my.id.medicalrecordblockchain.ui.global.detail_appointment.DetailAppointmentActivity
import my.id.medicalrecordblockchain.ui.global.home.HomeActivity
import my.id.medicalrecordblockchain.utils.ResultData

@AndroidEntryPoint
class AppointmentPatientFragment : Fragment() {
    private var _binding: FragmentAppointmentPatientBinding? = null
    private val binding get() = _binding!!
    private val viewModel: AppointmentPatientViewModel by viewModels()
    private val adapter by lazy {
        AppointmentPatientAdapter {
            DetailAppointmentActivity.launch(requireContext(), it.id.toString())
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAppointmentPatientBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        initData()
        observer()
    }

    private fun init() {
        binding.rvAppointment.adapter = adapter
    }

    private fun initData() {
        viewModel.getAppointmentList()
    }

    private fun observer() {
        viewModel.appointmentList.observe(viewLifecycleOwner) { state ->
            when (state) {
                is ResultData.Loading -> {
                    (requireActivity() as HomeActivity).showProgressLinear(true)
                }

                is ResultData.Success -> {
                    (requireActivity() as HomeActivity).showProgressLinear(false)
                    adapter.setData(state.data.data)
                }

                is ResultData.Failure -> {
                    (requireActivity() as HomeActivity).showProgressLinear(false)
                }
            }
        }
    }
}