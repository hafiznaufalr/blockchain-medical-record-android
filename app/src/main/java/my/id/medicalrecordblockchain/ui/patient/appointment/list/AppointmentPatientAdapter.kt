package my.id.medicalrecordblockchain.ui.patient.appointment.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import my.id.medicalrecordblockchain.data.response.AppointmentData
import my.id.medicalrecordblockchain.databinding.ItemAppointmentPatientBinding

class AppointmentPatientAdapter(
    private val onItemClickListener: (AppointmentData) -> Unit
) : RecyclerView.Adapter<AppointmentPatientAdapter.ViewHolder>() {

    private val listAppointments = mutableListOf<AppointmentData>()

    fun setData(data: List<AppointmentData>) {
        listAppointments.clear()
        listAppointments.addAll(data)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemAppointmentPatientBinding.inflate(LayoutInflater.from(parent.context), parent, false)
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

    inner class ViewHolder(private val binding: ItemAppointmentPatientBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindItem(item: AppointmentData) {
            binding.tvDoctorName.text = item.doctorName
            binding.tvDoctorType.text = item.healthServiceName
            binding.tvAppointmentDate.text = item.scheduleDate
            binding.tvAppointmentId.text = item.recordNumber
            binding.tvAppointmentStatus.text = item.status
        }
    }
}