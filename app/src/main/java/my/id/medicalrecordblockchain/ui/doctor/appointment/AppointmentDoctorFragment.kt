package my.id.medicalrecordblockchain.ui.doctor.appointment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import my.id.medicalrecordblockchain.databinding.FragmentAppointmentDoctorBinding
import my.id.medicalrecordblockchain.ui.global.detail_appointment.DetailAppointmentActivity
import my.id.medicalrecordblockchain.ui.global.filter.FilterMedicalBottomSheet
import my.id.medicalrecordblockchain.ui.global.filter.FilterMedicalCallBack
import my.id.medicalrecordblockchain.ui.global.home.HomeActivity
import my.id.medicalrecordblockchain.utils.ResultData

@AndroidEntryPoint
class AppointmentDoctorFragment : Fragment(), FilterMedicalCallBack {
    private var _binding: FragmentAppointmentDoctorBinding? = null
    private val binding get() = _binding!!
    private val viewModel: AppointmentDoctorViewModel by viewModels()
    private val adapter by lazy {
        AppointmentDoctorAdapter {
            DetailAppointmentActivity.launch(requireContext(), it.id.toString())
        }
    }
    private var filterMedicalBottomSheet: FilterMedicalBottomSheet? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAppointmentDoctorBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        initListener()
        observer()
    }

    private fun init() {
        binding.rvAppointment.adapter = adapter
        setupFilterBottomSheet()
    }

    private fun setupFilterBottomSheet() {
        filterMedicalBottomSheet = null
        filterMedicalBottomSheet = FilterMedicalBottomSheet(
            withStatus = true
        )
        filterMedicalBottomSheet?.attachCallBack(this)
    }

    private fun initListener() {
        binding.tvReset.setOnClickListener {
            binding.tvReset.isVisible = false
            setupFilterBottomSheet()
            viewModel.getAppointmentList()
        }

        binding.ivFilter.setOnClickListener {
            filterMedicalBottomSheet?.apply {
                isCancelable = true
            }?.show(childFragmentManager, "filter")
        }
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
                    adapter.setData(emptyList())
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.getAppointmentList()
    }

    override fun onSelectedFilter(date: String?, status: String?, service: Int?) {
        binding.tvReset.isVisible = !date.isNullOrEmpty() || !status.isNullOrEmpty()
        viewModel.getAppointmentList(
            scheduleDate = date,
            status = status
        )
    }
}