package my.id.medicalrecordblockchain.ui.doctor.medical_record.write

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint
import my.id.medicalrecordblockchain.data.Preferences
import my.id.medicalrecordblockchain.data.requests.WriteMedicalRecordRequest
import my.id.medicalrecordblockchain.data.response.AppointmentData
import my.id.medicalrecordblockchain.databinding.ActivityWriteMedicalRecordBinding
import my.id.medicalrecordblockchain.utils.LoadingDialog
import my.id.medicalrecordblockchain.utils.ResultData
import my.id.medicalrecordblockchain.utils.SnackBarType
import my.id.medicalrecordblockchain.utils.showSnackBar

@AndroidEntryPoint
class WriteMedicalRecordActivity : AppCompatActivity() {
    private lateinit var binding: ActivityWriteMedicalRecordBinding
    private val viewModel: WriteMedicalRecordViewModel by viewModels()
    private val loadingDialog by lazy { LoadingDialog(this) }
    private var appointmentData: AppointmentData? = null
    private var patientId = 0
    private var doctorId = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWriteMedicalRecordBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
        initListener()
        observer()
    }

    private fun init() {
        appointmentData = intent.getParcelableExtra(PATIENT_DATA)
        patientId = appointmentData?.patientId ?: 0
        doctorId = Preferences.getAccount()?.doctorId ?: 0
    }

    private fun initListener() {
        binding.ivBack.setOnClickListener {
            finish()
        }

        binding.btnAction.setOnClickListener {
            viewModel.writeMedicalRecord(
                appointmentId = appointmentData?.id.toString(),
                request = WriteMedicalRecordRequest(
                    additionalDocumentPath = "dummy",
                    diagnose = binding.etDiagnose.text.toString().trim(),
                    doctorId = doctorId,
                    patientId = patientId,
                    prescription = binding.etPrescription.text.toString().trim(),
                    notes = binding.etNote.text.toString().trim()
                )
            )
        }
    }

    private fun observer() {
        viewModel.writeMedicalRecordValidator.observe(this) { err ->
            showSnackBar(
                message = err,
                snackBarType = SnackBarType.ERROR
            )
        }

        viewModel.postWriteMedicalRecord.observe(this) { state ->
            when (state) {
                is ResultData.Loading -> {
                    loadingDialog.show()
                }

                is ResultData.Success -> {
                    loadingDialog.dismiss()
                }

                is ResultData.Failure -> {
                    loadingDialog.dismiss()
                }
            }
        }
    }

    companion object {
        private const val PATIENT_DATA = "PATIENT_DATA"
        fun launch(context: Context, appointmentData: AppointmentData) {
            context.startActivity(
                Intent(
                    context,
                    WriteMedicalRecordActivity::class.java
                ).apply {
                    putExtra(PATIENT_DATA, appointmentData)
                }
            )
        }
    }
}