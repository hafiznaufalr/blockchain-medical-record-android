package my.id.medicalrecordblockchain.ui.doctor.medical_record.history

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint
import my.id.medicalrecordblockchain.data.response.AppointmentData
import my.id.medicalrecordblockchain.databinding.ActivityMedicalRecordHistoryBinding
import my.id.medicalrecordblockchain.ui.doctor.medical_record.write.WriteMedicalRecordActivity
import my.id.medicalrecordblockchain.utils.ResultData

@AndroidEntryPoint
class MedicalRecordHistoryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMedicalRecordHistoryBinding
    private val viewModel: MedicalRecordHistoryViewModel by viewModels()
    private val adapter by lazy {
        MedicalRecordHistoryAdapter {

        }
    }
    private var appointmentData: AppointmentData? = null
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
    }

    override fun onResume() {
        super.onResume()
        viewModel.getAppointmentList()
    }

    private fun initListener() {
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
}