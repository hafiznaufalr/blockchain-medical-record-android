package my.id.medicalrecordblockchain.ui.doctor.medical_record

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import my.id.medicalrecordblockchain.R
import my.id.medicalrecordblockchain.databinding.ActivityWriteMedicalRecordBinding

class WriteMedicalRecordActivity : AppCompatActivity() {
    private lateinit var binding: ActivityWriteMedicalRecordBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWriteMedicalRecordBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}