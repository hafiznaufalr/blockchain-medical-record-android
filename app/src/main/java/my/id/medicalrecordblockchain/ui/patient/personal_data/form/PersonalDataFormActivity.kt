package my.id.medicalrecordblockchain.ui.patient.personal_data.form

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import my.id.medicalrecordblockchain.databinding.ActivityPersonalDataFormBinding

class PersonalDataFormActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPersonalDataFormBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPersonalDataFormBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}