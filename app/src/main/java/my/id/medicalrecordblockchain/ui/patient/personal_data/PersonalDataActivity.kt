package my.id.medicalrecordblockchain.ui.patient.personal_data

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import my.id.medicalrecordblockchain.R
import my.id.medicalrecordblockchain.databinding.ActivityPersonalDataBinding

class PersonalDataActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPersonalDataBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPersonalDataBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}