package my.id.medicalrecordblockchain.ui.global.medical_record

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import my.id.medicalrecordblockchain.BuildConfig
import my.id.medicalrecordblockchain.data.response.AccountData
import my.id.medicalrecordblockchain.data.response.AppointmentData
import my.id.medicalrecordblockchain.data.response.MedicalRecordData
import my.id.medicalrecordblockchain.databinding.ActivityDetailMedicalRecordBinding
import my.id.medicalrecordblockchain.utils.LoadingDialog
import my.id.medicalrecordblockchain.utils.ResultData
import my.id.medicalrecordblockchain.utils.calculateAge
import my.id.medicalrecordblockchain.utils.downloadMedicalRecord
import my.id.medicalrecordblockchain.utils.formatDate
import my.id.medicalrecordblockchain.utils.getGender

@AndroidEntryPoint
class DetailMedicalRecordActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailMedicalRecordBinding
    private val loadingDialog by lazy { LoadingDialog(this) }
    private val viewModel: DetailMedicalRecordViewModel by viewModels()
    private var appointmentData: AppointmentData? = null
    private var medicalRecordData: MedicalRecordData? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailMedicalRecordBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initData()
        initListener()
        observer()
    }

    private fun initData() {
        appointmentData = intent.getParcelableExtra(APPOINTMENT_DATA)
        viewModel.getPatientById(patientId = appointmentData?.patientId.toString())
        viewModel.getMedicalRecordById(appointmentId = appointmentData?.id.toString())
    }

    private fun initListener() {
        binding.ivBack.setOnClickListener {
            finish()
        }

        binding.btnAction.setOnClickListener {
            viewModel.exportMedicalRecord(
                appointmentId = appointmentData?.id.toString()
            )
        }
    }

    private fun observer() {
        viewModel.patientById.observe(this) { state ->
            when (state) {
                is ResultData.Loading -> {

                }

                is ResultData.Success -> {
                    state.data.data?.let {
                        setupPatientData(it)
                    }
                }

                is ResultData.Failure -> {

                }
            }
        }


        viewModel.medicalRecord.observe(this) { state ->
            when (state) {
                is ResultData.Loading -> {
                    loadingDialog.show()
                }

                is ResultData.Success -> {
                    loadingDialog.dismiss()
                    state.data.data?.let {
                        setupMedicalRecord(it)
                    }
                }

                is ResultData.Failure -> {
                    loadingDialog.dismiss()
                }
            }
        }

        viewModel.exportMedicalRecord.observe(this) { state ->
            when (state) {
                is ResultData.Loading -> {
                    loadingDialog.show()
                }

                is ResultData.Success -> {
                    loadingDialog.dismiss()
                    state.data.data?.let {
                        downloadMedicalRecord(
                            filename = medicalRecordData?.medicalRecordNumber.orEmpty(),
                            url = BuildConfig.BASE_URL + it
                        )
                    }
                }

                is ResultData.Failure -> {
                    loadingDialog.dismiss()
                }
            }
        }
    }

    private fun setupPatientData(data: AccountData) {
        binding.tvAge.text = calculateAge(data.dateOfBirth.orEmpty())
        binding.tvWeight.text = "${data.weight} Kg"
        binding.tvHeight.text = "${data.height} Cm"
        binding.tvGender.text = data.gender?.getGender()
        binding.tvAllergic.text = data.allergies
    }

    private fun setupMedicalRecord(data: MedicalRecordData) {
        medicalRecordData = data
        binding.tvMedicalRecordId.text = data.medicalRecordNumber
        binding.tvWrittenDate.text = data.createdAt?.formatDate()
        binding.tvDoctor.text = appointmentData?.doctorName
        binding.tvSymptoms.text = appointmentData?.symptoms
        binding.tvDisease.text = data.diagnose
        binding.tvNotes.text = data.notes
        binding.tvPrescriptions.text = data.prescription

        if (!data.additionalDocumentPath.orEmpty().contains("jpg")) return
        binding.ivUpload.isVisible = true
        Glide
            .with(binding.ivUpload.context)
            .load(BuildConfig.BASE_URL + data.additionalDocumentPath)
            .into(binding.ivUpload)
    }

    companion object {
        private const val APPOINTMENT_DATA = "APPOINTMENT_DATA"
        fun launch(context: Context, appointmentData: AppointmentData) {
            context.startActivity(
                Intent(
                    context,
                    DetailMedicalRecordActivity::class.java
                ).apply {
                    putExtra(APPOINTMENT_DATA, appointmentData)
                }
            )
        }
    }
}