package my.id.medicalrecordblockchain.ui.global.sign_in

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import my.id.medicalrecordblockchain.databinding.ActivitySignInBinding

class SignInActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignInBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}