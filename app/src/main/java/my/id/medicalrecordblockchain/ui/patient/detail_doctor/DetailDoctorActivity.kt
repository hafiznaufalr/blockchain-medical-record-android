package my.id.medicalrecordblockchain.ui.patient.detail_doctor

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import my.id.medicalrecordblockchain.App
import my.id.medicalrecordblockchain.R
import my.id.medicalrecordblockchain.data.requests.BookAppointmentRequest
import my.id.medicalrecordblockchain.data.response.DoctorData
import my.id.medicalrecordblockchain.databinding.ActivityDetailDoctorBinding
import my.id.medicalrecordblockchain.ui.patient.appointment.book_appointment.BookAppointmentActivity
import my.id.medicalrecordblockchain.utils.ItemHorizontalMarginDecoration
import java.text.SimpleDateFormat
import java.util.Locale

class DetailDoctorActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailDoctorBinding
    private var request = BookAppointmentRequest()
    private var doctorData: DoctorData? = null
    private var pickedDay: String = ""
    private var times = mapOf<String, List<String>>()
    private val dayAdapter by lazy {
        TimeAdapter {
            pickedDay = it
            timeAdapter.setData(
                data = times[it].orEmpty()
            )
        }
    }

    private var pickedTime: String = ""
    private val timeAdapter by lazy {
        TimeAdapter {
            pickedTime = it
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailDoctorBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
        initListener()
        initData()
    }

    private fun init() {
        val decoration = ItemHorizontalMarginDecoration(
            context = App.getAppContext(),
            horizontalMargin = R.dimen.size_4,
            firstLeftItemMargin = R.dimen.size_16,
            lastRightItemMargin = R.dimen.size_16
        )

        binding.rvPickDay.apply {
            adapter = dayAdapter
            addItemDecoration(
                decoration
            )
        }

        binding.rvPickTime.apply {
            adapter = timeAdapter
            addItemDecoration(
                decoration
            )
        }
    }

    private fun initListener() {
        binding.btnNext.setOnClickListener {
            request = BookAppointmentRequest(
                doctorId = doctorData?.doctorId,
                healthServiceId = doctorData?.healthServiceId,
                scheduleDate = getDateNow(),
                scheduleTime = pickedTime
            )
            BookAppointmentActivity.launch(
                context = this,
                request = request
            )
        }

        binding.ivBack.setOnClickListener {
            finish()
        }
    }

    private fun getDateNow(): String {
        val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        return formatter.format(System.currentTimeMillis())
    }

    private fun initData() {
        intent.getParcelableExtra<DoctorData>(KEY_DOCTOR_DATA)?.let {
            doctorData = it
            binding.tvDoctorName.text = it.name
            binding.tvDoctorType.text = it.healthServiceName
            binding.tvDoctorDescription.text = it.description

            times = mapOf(
                "Senin" to it.availableSchedule?.monday.orEmpty(),
                "Selasa" to it.availableSchedule?.tuesday.orEmpty(),
                "Rabu" to it.availableSchedule?.wednesday.orEmpty(),
                "Kamis" to it.availableSchedule?.thursday.orEmpty(),
                "Jum'at" to it.availableSchedule?.friday.orEmpty()
            )


            dayAdapter.setData(
                data = times.keys.toList()
            )
            pickedDay = times.keys.toList().first()

            timeAdapter.setData(
                data = times["Senin"].orEmpty()
            )
            pickedTime = times["Senin"]?.first().orEmpty()
        }
    }

    companion object {
        private const val KEY_DOCTOR_DATA = "KEY_DOCTOR_DATA"

        fun launch(context: Context, data: DoctorData) {
            context.startActivity(
                Intent(
                    context,
                    DetailDoctorActivity::class.java
                ).apply {
                    putExtra(KEY_DOCTOR_DATA, data)
                }
            )
        }
    }
}