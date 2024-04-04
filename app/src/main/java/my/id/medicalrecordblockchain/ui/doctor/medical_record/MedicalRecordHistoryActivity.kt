package my.id.medicalrecordblockchain.ui.doctor.medical_record

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import my.id.medicalrecordblockchain.databinding.ActivityMedicalRecordHistoryBinding

class MedicalRecordHistoryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMedicalRecordHistoryBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMedicalRecordHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}