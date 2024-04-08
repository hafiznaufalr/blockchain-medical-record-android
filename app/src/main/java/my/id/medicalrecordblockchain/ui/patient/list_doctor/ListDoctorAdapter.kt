package my.id.medicalrecordblockchain.ui.patient.list_doctor

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import my.id.medicalrecordblockchain.data.response.DoctorData
import my.id.medicalrecordblockchain.databinding.ItemDoctorBinding

class ListDoctorAdapter(
    private val onItemClickListener: (DoctorData) -> Unit
) : RecyclerView.Adapter<ListDoctorAdapter.ViewHolder>() {

    private val listDoctors = mutableListOf<DoctorData>()

    fun setData(data: List<DoctorData>) {
        listDoctors.clear()
        listDoctors.addAll(data)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemDoctorBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding).apply {
            binding.root.setOnClickListener {
                onItemClickListener.invoke(listDoctors[adapterPosition])
            }
        }
    }

    override fun getItemCount(): Int = listDoctors.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(listDoctors[position])
    }

    inner class ViewHolder(private val binding: ItemDoctorBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindItem(item: DoctorData) {
            binding.tvDoctor.text = item.name
        }
    }
}