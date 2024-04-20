package my.id.medicalrecordblockchain.ui.doctor.medical_record.history

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import dagger.hilt.android.AndroidEntryPoint
import my.id.medicalrecordblockchain.data.response.AppointmentData
import my.id.medicalrecordblockchain.databinding.ActivityMedicalRecordHistoryBinding
import my.id.medicalrecordblockchain.ui.doctor.medical_record.write.WriteMedicalRecordActivity
import my.id.medicalrecordblockchain.ui.global.filter.FilterMedicalBottomSheet
import my.id.medicalrecordblockchain.ui.global.filter.FilterMedicalCallBack
import my.id.medicalrecordblockchain.ui.global.medical_record.DetailMedicalRecordActivity
import my.id.medicalrecordblockchain.utils.ResultData

@AndroidEntryPoint
class MedicalRecordHistoryActivity : AppCompatActivity(), FilterMedicalCallBack {
    private lateinit var binding: ActivityMedicalRecordHistoryBinding
    private val viewModel: MedicalRecordHistoryViewModel by viewModels()
    private val adapter by lazy {
        MedicalRecordHistoryAdapter {
            DetailMedicalRecordActivity.launch(
                context = this,
                appointmentData = it
            )
        }
    }
    private var appointmentData: AppointmentData? = null
    private var filterMedicalBottomSheet: FilterMedicalBottomSheet? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMedicalRecordHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
        initListener()
        observer()
    }

    private fun init() {
        binding.rvMedicalRecord.adapter = adapter
        appointmentData = intent.getParcelableExtra(APPOINTMENT_DATA)
        setupFilterBottomSheet()
    }

    private fun setupFilterBottomSheet() {
        filterMedicalBottomSheet = null
        filterMedicalBottomSheet = FilterMedicalBottomSheet(
            withService = true
        )
        filterMedicalBottomSheet?.attachCallBack(this)
    }

    override fun onResume() {
        super.onResume()
        viewModel.getAppointmentList()
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
            }?.show(supportFragmentManager, "filter")
        }

        binding.ivBack.setOnClickListener {
            finish()
        }

        binding.btnPrimary.setOnClickListener {
            appointmentData?.let {
                val patientId = appointmentData?.patientId ?: 0
                if (patientId == 0) return@setOnClickListener

                WriteMedicalRecordActivity.launch(
                    context = this,
                    appointmentData = it
                )
            }
        }
    }

    private fun observer() {
        viewModel.appointmentList.observe(this) { state ->
            when (state) {
                is ResultData.Loading -> {

                }

                is ResultData.Success -> {
                    adapter.setData(state.data.data)
                }

                is ResultData.Failure -> {
                    adapter.setData(emptyList())
                }
            }
        }
    }

    companion object {
        private const val APPOINTMENT_DATA = "APPOINTMENT_DATA"
        fun launch(context: Context, appointmentData: AppointmentData) {
            context.startActivity(
                Intent(
                    context,
                    MedicalRecordHistoryActivity::class.java
                ).apply {
                    putExtra(APPOINTMENT_DATA, appointmentData)
                }
            )
        }
    }

    override fun onSelectedFilter(date: String?, status: String?, service: Int?) {
        binding.tvReset.isVisible = !date.isNullOrEmpty() || service != 0
        viewModel.getAppointmentList(
            date = date,
            service = service.toString()
        )
    }
}