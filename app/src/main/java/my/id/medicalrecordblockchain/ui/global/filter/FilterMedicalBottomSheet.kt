package my.id.medicalrecordblockchain.ui.global.filter

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.DialogFragment
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog
import my.id.medicalrecordblockchain.R
import my.id.medicalrecordblockchain.data.Preferences
import my.id.medicalrecordblockchain.databinding.BottomSheetFilterMedicalRecordBinding
import java.util.Calendar


class FilterMedicalBottomSheet(
    private val withStatus: Boolean = false,
    private val withService: Boolean = false
) : BottomSheetDialogFragment(), DatePickerDialog.OnDateSetListener {
    private var _binding: BottomSheetFilterMedicalRecordBinding? = null
    private val binding get() = _binding!!
    private var callBack: FilterMedicalCallBack? = null
    private var selectedStatus: String = "ALL"
    private var selectedService: Int = 0
    private var datePickerDialog: DatePickerDialog? = null
    private val statuses = listOf(
        "ALL",
        "WAITING",
        "UPCOMING",
        "DONE",
        "REJECTED",
        "CANCELLED"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NORMAL, R.style.AppBottomSheetDialogTheme)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = BottomSheetFilterMedicalRecordBinding.inflate(inflater, container, false)
        return binding.root
    }

    fun attachCallBack(callBack: FilterMedicalCallBack) {
        this.callBack = callBack
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        initListener()
    }

    private fun init() {
        binding.tvLabelService.isVisible = withService
        binding.etService.isVisible = withService

        binding.tvLabelStatus.isVisible = withStatus
        binding.etStatus.isVisible = withStatus

        val services = Preferences.getServices()
        val servicesAdapter: ArrayAdapter<String> = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_item,
            listOf("ALL") + services.map { it.name.orEmpty() }
        ).apply {
            setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        }
        binding.etService.apply {
            adapter = servicesAdapter
            onItemSelectedListener = object : OnItemSelectedListener{
                override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                    if (p2 == 0) return
                    selectedService = services[p2 - 1].id ?: 0
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {
                }

            }
        }

        val statusAdapter: ArrayAdapter<String> = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_item,
            statuses
        ).apply {
            setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        }
        binding.etStatus.apply {
            adapter = statusAdapter
            onItemSelectedListener = object : OnItemSelectedListener{
                override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                    selectedStatus = statuses[p2]
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {
                }

            }
        }
    }

    private fun initListener() {
        binding.etDate.setOnClickListener {
            showDatePicker()
        }

        binding.btnApply.setOnClickListener {
            callBack?.onSelectedFilter(
                date = binding.etDate.text.toString(),
                status = if (selectedStatus == "ALL") "" else selectedStatus,
                service = selectedService
            )
            dismiss()
        }
    }

    private fun showDatePicker() {
        val now = Calendar.getInstance()
        datePickerDialog = DatePickerDialog.newInstance(
            this, now[Calendar.YEAR], now[Calendar.MONTH], now[Calendar.DAY_OF_MONTH]
        )

        datePickerDialog?.maxDate = Calendar.getInstance()
        datePickerDialog?.version = DatePickerDialog.Version.VERSION_2
        datePickerDialog?.accentColor =
            ContextCompat.getColor(requireContext(), R.color.primary)
        datePickerDialog?.setOnCancelListener {
            Log.d("DatePickerDialog", "Dialog was cancelled")
        }
        datePickerDialog?.show(childFragmentManager, "date picker onboarding")
    }

    override fun onDateSet(view: DatePickerDialog?, year: Int, monthOfYear: Int, dayOfMonth: Int) {
        val month = monthOfYear + 1

        val updatedDay = dayOfMonth.toString().padStart(2, '0')
        val updatedMonth = month.toString().padStart(2, '0')

        binding.etDate.setText("${year}-${updatedMonth}-${updatedDay}")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}