package my.id.medicalrecordblockchain.ui.patient.appointment.book_appointment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint
import my.id.medicalrecordblockchain.data.Preferences
import my.id.medicalrecordblockchain.data.requests.BookAppointmentRequest
import my.id.medicalrecordblockchain.databinding.ActivityBookAppointmentBinding
import my.id.medicalrecordblockchain.ui.global.detail_appointment.DetailAppointmentActivity
import my.id.medicalrecordblockchain.ui.global.home.HomeActivity
import my.id.medicalrecordblockchain.utils.LoadingDialog
import my.id.medicalrecordblockchain.utils.ResultData
import my.id.medicalrecordblockchain.utils.SnackBarType
import my.id.medicalrecordblockchain.utils.showSnackBar

@AndroidEntryPoint
class BookAppointmentActivity : AppCompatActivity() {
    private lateinit var binding: ActivityBookAppointmentBinding
    private val viewModel: BookAppointmentViewModel by viewModels()
    private val loadingDialog by lazy { LoadingDialog(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBookAppointmentBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initListener()
        observer()
    }

    private fun initListener() {
        binding.ivBack.setOnClickListener {
            finish()
        }

        binding.btnBookAppointment.setOnClickListener {
            intent.getParcelableExtra<BookAppointmentRequest>(
                KEY_BOOKING_APPOINTMENT
            )?.copy(
                symptoms = binding.etSymptoms.text.toString().trim()
            )?.let { request ->
                viewModel.bookingAppointment(request)
            }
        }
    }

    private fun observer() {
        Preferences.getAccount()?.let {
            binding.etAllergic.setText(it.allergies)
        }

        viewModel.bookingValidator.observe(this) {
            showSnackBar(
                message = it,
                snackBarType = SnackBarType.ERROR
            )
        }

        viewModel.bookingAppointment.observe(this) { state ->
            when (state) {
                is ResultData.Loading -> {
                    loadingDialog.show()
                }

                is ResultData.Success -> {
                    loadingDialog.dismiss()
                    showSnackBar(
                        "Pemesanan Sukses",
                        snackBarType = SnackBarType.SUCCESS
                    )

                    Handler(Looper.getMainLooper()).postDelayed({
                        state.data.data?.id?.let { appointmentId ->
                            HomeActivity.launch(context = this, indexMenu = 1)
                            DetailAppointmentActivity.launch(
                                context = this,
                                appointmentId = appointmentId.toString()
                            )
                        }
                    }, 500)
                }

                is ResultData.Failure -> {
                    loadingDialog.dismiss()
                    showSnackBar(
                        state.error,
                        snackBarType = SnackBarType.ERROR
                    )
                }
            }
        }
    }

    companion object {
        private const val KEY_BOOKING_APPOINTMENT = "KEY_BOOKING_APPOINTMENT"
        fun launch(context: Context, request: BookAppointmentRequest) {
            context.startActivity(
                Intent(
                    context,
                    BookAppointmentActivity::class.java
                ).apply {
                    putExtra(KEY_BOOKING_APPOINTMENT, request)
                }
            )
        }
    }
}