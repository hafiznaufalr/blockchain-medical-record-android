package my.id.medicalrecordblockchain.ui.patient.personal_data.info

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import dagger.hilt.android.AndroidEntryPoint
import my.id.medicalrecordblockchain.R
import my.id.medicalrecordblockchain.data.response.AccountData
import my.id.medicalrecordblockchain.databinding.ActivityPersonalDataInfoBinding

@AndroidEntryPoint
class PersonalDataInfoActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPersonalDataInfoBinding
    private val viewModel: PersonalDataInfoViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPersonalDataInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initData()
        observer()
    }

    private fun initData() {
        viewModel.getAccount()
    }

    private fun observer() {
        viewModel.account.observe(this) {
            setupPersonalDataInfo(it)
        }
    }

    private fun setupPersonalDataInfo(data: AccountData) {
        binding.tvGender.text = data.gender
        binding.tvAge.text = "-"
        binding.tvWeight.text = "${data.weight} Kg"
        binding.tvHeight.text = "${data.height} Cm"
        binding.tvAllergic.text = data.allergies
        binding.tvBloodType.text = data.bloodGroup
    }

    companion object {
        fun launch(context: Context) {
            context.startActivity(
                Intent(
                    context,
                    PersonalDataInfoActivity::class.java
                )
            )
        }
    }
}