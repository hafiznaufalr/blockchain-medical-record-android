package my.id.medicalrecordblockchain.ui.patient.list_doctor

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import my.id.medicalrecordblockchain.databinding.ActivityListDoctorBinding

class ListDoctorActivity : AppCompatActivity() {
    private lateinit var binding: ActivityListDoctorBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListDoctorBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}