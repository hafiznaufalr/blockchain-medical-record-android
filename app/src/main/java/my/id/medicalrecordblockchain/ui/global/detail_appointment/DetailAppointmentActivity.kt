package my.id.medicalrecordblockchain.ui.global.detail_appointment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint
import my.id.medicalrecordblockchain.data.response.AppointmentData
import my.id.medicalrecordblockchain.databinding.ActivityDetailAppointmentBinding
import my.id.medicalrecordblockchain.utils.ResultData
import my.id.medicalrecordblockchain.utils.decideActionByFlavor
import my.id.medicalrecordblockchain.utils.gone
import my.id.medicalrecordblockchain.utils.visible

@AndroidEntryPoint
class DetailAppointmentActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailAppointmentBinding
    private val viewModel: DetailAppointmentViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailAppointmentBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
        initData()
        observer()
    }

    private fun init() {

    }

    private fun initData() {
        viewModel.getAppointmentById(intent.getStringExtra(APPOINTMENT_ID) ?: "")
    }

    private fun observer() {
        viewModel.appointment.observe(this) { state ->
            when (state) {
                is ResultData.Loading -> {

                }

                is ResultData.Success -> {
                    state.data.data?.let {
                        setupDetailAppointment(it)
                        setupButtonByStatus(it.status.orEmpty())
                    }
                }

                is ResultData.Failure -> {

                }
            }
        }
    }

    private fun setupButtonByStatus(status: String) {
        decideActionByFlavor(
            patientAction = {
                when (status) {
                    "WAITING" -> {
                        binding.vBtnSeparator.gone()
                        binding.btnSecondary.gone()

                        binding.flButton.visible()
                        binding.btnPrimary.visible()
                        binding.btnPrimary.text = "Batalkan Janji"
                    }

                    "UPCOMING", "CANCELLED", "REJECTED" -> {
                        binding.flButton.gone()
                    }

                    "DONE" -> {
                        binding.vBtnSeparator.gone()
                        binding.btnSecondary.gone()

                        binding.flButton.visible()
                        binding.btnPrimary.visible()
                        binding.btnPrimary.text = "Lihat Rekam Medis"
                    }
                }
            },
            doctorAction = {
                when (status) {
                    "WAITING" -> {
                        binding.flButton.visible()
                        binding.btnPrimary.visible()
                        binding.btnPrimary.text = "Terima"
                        binding.vBtnSeparator.visible()
                        binding.btnSecondary.visible()
                        binding.btnSecondary.text = "Tolak"
                    }
                    "UPCOMING" -> {
                        binding.vBtnSeparator.gone()
                        binding.btnSecondary.gone()

                        binding.flButton.visible()
                        binding.btnPrimary.visible()
                        binding.btnPrimary.text = "Lihat Riwayat Kesehatan"
                    }

                    "CANCELLED", "REJECTED" -> {
                        binding.flButton.gone()
                    }

                    "DONE" -> {
                        binding.vBtnSeparator.gone()
                        binding.btnSecondary.gone()

                        binding.flButton.visible()
                        binding.btnPrimary.visible()
                        binding.btnPrimary.text = "Lihat Rekam Medis"
                    }
                }
            }
        )
    }

    private fun setupDetailAppointment(data: AppointmentData) {
    }

    companion object {
        private const val APPOINTMENT_ID = "APPOINTMENT_ID"
        fun launch(context: Context, appointmentId: String) {
            context.startActivity(
                Intent(
                    context,
                    DetailAppointmentActivity::class.java
                ).apply {
                    putExtra(APPOINTMENT_ID, appointmentId)
                }
            )
        }
    }
}