package my.id.medicalrecordblockchain.ui.patient.sign_up

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import my.id.medicalrecordblockchain.databinding.ActivitySignUpBinding

class SignUpActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignUpBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    companion object {
        fun launch(context: Context) {
            context.startActivity(
                Intent(
                    context,
                    SignUpActivity::class.java
                )
            )
        }
    }
}