package my.id.medicalrecordblockchain.ui.patient.book_appointment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import my.id.medicalrecordblockchain.R
import my.id.medicalrecordblockchain.databinding.ActivityBookAppointmentBinding

class BookAppointmentActivity : AppCompatActivity() {
    private lateinit var binding: ActivityBookAppointmentBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBookAppointmentBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}