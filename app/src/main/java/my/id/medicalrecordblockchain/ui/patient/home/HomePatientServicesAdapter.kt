package my.id.medicalrecordblockchain.ui.patient.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import my.id.medicalrecordblockchain.data.response.ServicesData
import my.id.medicalrecordblockchain.databinding.ItemServiceBinding

class HomePatientServicesAdapter(
    private val onItemClickListener: (ServicesData) -> Unit
) :
    RecyclerView.Adapter<HomePatientServicesAdapter.HomePatientViewHolder>() {

    private val listServices = mutableListOf<ServicesData>()

    fun setData(services: List<ServicesData>) {
        listServices.clear()
        listServices.addAll(services)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomePatientViewHolder {
        val binding = ItemServiceBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HomePatientViewHolder(binding).apply {
            binding.root.setOnClickListener {
                onItemClickListener.invoke(listServices[adapterPosition])
            }
        }
    }

    override fun getItemCount(): Int = listServices.size

    override fun onBindViewHolder(holder: HomePatientViewHolder, position: Int) {
        holder.bindItem(listServices[position])
    }

    inner class HomePatientViewHolder(private val binding: ItemServiceBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindItem(item: ServicesData) {
            binding.tvService.text = item.name
        }
    }
}