package my.id.medicalrecordblockchain.ui.patient.detail_doctor

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import my.id.medicalrecordblockchain.R
import my.id.medicalrecordblockchain.databinding.ActivityDetailDoctorBinding

class DetailDoctorActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailDoctorBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailDoctorBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}