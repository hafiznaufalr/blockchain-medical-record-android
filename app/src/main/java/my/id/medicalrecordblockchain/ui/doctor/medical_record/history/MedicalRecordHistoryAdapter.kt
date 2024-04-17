package my.id.medicalrecordblockchain.ui.doctor.medical_record.history

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import my.id.medicalrecordblockchain.data.response.AppointmentData
import my.id.medicalrecordblockchain.databinding.ItemMedicalRecordHistoryBinding

class MedicalRecordHistoryAdapter(
    private val onItemClickListener: (AppointmentData) -> Unit
) : RecyclerView.Adapter<MedicalRecordHistoryAdapter.ViewHolder>() {

    private val listAppointments = mutableListOf<AppointmentData>()

    fun setData(data: List<AppointmentData>) {
        listAppointments.clear()
        listAppointments.addAll(data)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemMedicalRecordHistoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding).apply {
            binding.root.setOnClickListener {
                onItemClickListener.invoke(listAppointments[adapterPosition])
            }
        }
    }

    override fun getItemCount(): Int = listAppointments.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(listAppointments[position])
    }

    inner class ViewHolder(private val binding: ItemMedicalRecordHistoryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindItem(item: AppointmentData) {
            binding.tvHealthService.text = item.healthServiceName
            binding.tvDoctorName.text = item.doctorName
            binding.tvAppointmentDate.text = item.scheduleDate
        }
    }
}