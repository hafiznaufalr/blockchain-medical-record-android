package my.id.medicalrecordblockchain.ui.doctor.home

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import my.id.medicalrecordblockchain.databinding.ActivityHomeDoctorBinding

class HomeDoctorActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeDoctorBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeDoctorBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}