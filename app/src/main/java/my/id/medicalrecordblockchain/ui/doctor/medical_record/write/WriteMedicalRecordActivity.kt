package my.id.medicalrecordblockchain.ui.doctor.medical_record.write

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import id.zelory.compressor.Compressor
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import my.id.medicalrecordblockchain.data.Preferences
import my.id.medicalrecordblockchain.data.requests.WriteMedicalRecordRequest
import my.id.medicalrecordblockchain.data.response.AppointmentData
import my.id.medicalrecordblockchain.databinding.ActivityWriteMedicalRecordBinding
import my.id.medicalrecordblockchain.ui.global.detail_appointment.DetailAppointmentActivity
import my.id.medicalrecordblockchain.ui.global.home.HomeActivity
import my.id.medicalrecordblockchain.utils.LoadingDialog
import my.id.medicalrecordblockchain.utils.ResultData
import my.id.medicalrecordblockchain.utils.SnackBarType
import my.id.medicalrecordblockchain.utils.StorageFileUtil
import my.id.medicalrecordblockchain.utils.gone
import my.id.medicalrecordblockchain.utils.showSnackBar
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import java.io.IOException

@AndroidEntryPoint
class WriteMedicalRecordActivity : AppCompatActivity() {
    private lateinit var binding: ActivityWriteMedicalRecordBinding
    private val viewModel: WriteMedicalRecordViewModel by viewModels()
    private val loadingDialog by lazy { LoadingDialog(this) }
    private var appointmentData: AppointmentData? = null
    private var patientId = 0
    private var doctorId = 0
    private var actualImage: File? = null
    private var compressedImage: File? = null
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
            if (actualImage != null) {
                compressImage()
            } else {
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

        binding.ivUpload.setOnClickListener {
            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.type = "image/*"
            startActivityForResult(intent, 100)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 100 && resultCode == RESULT_OK) {
            if (data == null) {
                return
            }
            try {
                actualImage = StorageFileUtil.from(this, data.data)?.also {
                    binding.tvUploadImage.gone()
                    Glide.with(binding.ivUpload.context)
                        .load(data.data)
                        .into(binding.ivUpload)
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    private fun compressImage() {
        actualImage?.let { imageFile ->
            CoroutineScope(Dispatchers.Unconfined).launch {
                compressedImage = Compressor.compress(this@WriteMedicalRecordActivity, imageFile)
                withContext(Dispatchers.Main) {
                    postFileProof()
                }
            }
        }
    }

    private fun postFileProof() {
        val uri = Uri.fromFile(compressedImage)
        val requestBody = compressedImage!!.asRequestBody("multipart/form-data".toMediaTypeOrNull())
        val file = MultipartBody.Part.createFormData("file", uri.toString(), requestBody)
        viewModel.uploadFile(
            file = file
        )
    }


    private fun observer() {
        viewModel.postFile.observe(this) { state ->
            when (state) {
                is ResultData.Loading -> {
                    loadingDialog.show()
                }

                is ResultData.Success -> {
                    loadingDialog.dismiss()
                    state.data.data?.path?.let { path ->
                        viewModel.writeMedicalRecord(
                            appointmentId = appointmentData?.id.toString(),
                            request = WriteMedicalRecordRequest(
                                additionalDocumentPath = path,
                                diagnose = binding.etDiagnose.text.toString().trim(),
                                doctorId = doctorId,
                                patientId = patientId,
                                prescription = binding.etPrescription.text.toString().trim(),
                                notes = binding.etNote.text.toString().trim()
                            )
                        )
                    }
                }

                is ResultData.Failure -> {
                    loadingDialog.dismiss()
                }
            }
        }

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
                    showSnackBar(
                        message = "Berhasil membuat rekam medis",
                        snackBarType = SnackBarType.SUCCESS
                    )

                    Handler(Looper.getMainLooper()).postDelayed({
                        HomeActivity.launch(context = this)
                        DetailAppointmentActivity.launch(
                            context = this,
                            appointmentId = appointmentData?.id.toString()
                        )
                    }, 500)
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