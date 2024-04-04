package my.id.medicalrecordblockchain.ui.global.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import my.id.medicalrecordblockchain.R
import my.id.medicalrecordblockchain.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}