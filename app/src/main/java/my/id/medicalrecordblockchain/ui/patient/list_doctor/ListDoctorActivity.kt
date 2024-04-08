package my.id.medicalrecordblockchain.ui.patient.list_doctor

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint
import my.id.medicalrecordblockchain.data.response.ServicesData
import my.id.medicalrecordblockchain.databinding.ActivityListDoctorBinding
import my.id.medicalrecordblockchain.ui.patient.detail_doctor.DetailDoctorActivity
import my.id.medicalrecordblockchain.utils.LoadingDialog
import my.id.medicalrecordblockchain.utils.ResultData

@AndroidEntryPoint
class ListDoctorActivity : AppCompatActivity() {
    private lateinit var binding: ActivityListDoctorBinding
    private val viewModel: ListDoctorViewModel by viewModels()
    private val loadingDialog by lazy { LoadingDialog(this) }
    private val doctorAdapter by lazy {
        ListDoctorAdapter { data ->
            DetailDoctorActivity.launch(
                context = this,
                data = data
            )
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListDoctorBinding.inflate(layoutInflater)
        setContentView(binding.root)

        init()
        initListener()
        initData()
        observer()
    }

    private fun init() {
        binding.rvDoctor.adapter = doctorAdapter
    }

    private fun initListener() {
        binding.ivBack.setOnClickListener {
            finish()
        }
    }

    private fun initData() {
        intent.getParcelableExtra<ServicesData>(KEY_SERVICE_DATA)?.let {
            viewModel.getListDoctorByService(it.id ?: 0)
            binding.tvSubTitle.text = it.name
        }
    }

    private fun observer() {
        viewModel.listDoctorByService.observe(this) { state ->
            when (state) {
                is ResultData.Loading -> {
                    loadingDialog.show()
                }

                is ResultData.Success -> {
                    loadingDialog.dismiss()
                    doctorAdapter.setData(state.data.data)
                }

                is ResultData.Failure -> {
                    loadingDialog.dismiss()
                }
            }
        }
    }


    companion object {
        private const val KEY_SERVICE_DATA = "KEY_SERVICE_DATA"

        fun launch(context: Context, data: ServicesData) {
            context.startActivity(
                Intent(
                    context,
                    ListDoctorActivity::class.java
                ).apply {
                    putExtra(KEY_SERVICE_DATA, data)
                }
            )
        }
    }
}