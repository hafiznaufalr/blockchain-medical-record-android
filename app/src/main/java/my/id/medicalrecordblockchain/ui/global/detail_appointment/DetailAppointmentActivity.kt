package my.id.medicalrecordblockchain.ui.global.detail_appointment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import my.id.medicalrecordblockchain.R
import my.id.medicalrecordblockchain.databinding.ActivityDetailAppointmentBinding

class DetailAppointmentActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailAppointmentBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailAppointmentBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}