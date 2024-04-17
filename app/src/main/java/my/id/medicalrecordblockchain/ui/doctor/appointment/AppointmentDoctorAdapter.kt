package my.id.medicalrecordblockchain.ui.doctor.appointment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import my.id.medicalrecordblockchain.data.response.AppointmentData
import my.id.medicalrecordblockchain.databinding.ItemAppointmentDoctorBinding

class AppointmentDoctorAdapter(
    private val onItemClickListener: (AppointmentData) -> Unit
) : RecyclerView.Adapter<AppointmentDoctorAdapter.ViewHolder>() {

    private val listAppointments = mutableListOf<AppointmentData>()

    fun setData(data: List<AppointmentData>) {
        listAppointments.clear()
        listAppointments.addAll(data)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemAppointmentDoctorBinding.inflate(LayoutInflater.from(parent.context), parent, false)
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

    inner class ViewHolder(private val binding: ItemAppointmentDoctorBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindItem(item: AppointmentData) {
            binding.tvPatientName.text = item.patientName
            binding.tvAppointmentDate.text = item.scheduleDate
            binding.tvAppointmentId.text = item.recordNumber
            binding.tvAppointmentStatus.text = item.status
        }
    }
}