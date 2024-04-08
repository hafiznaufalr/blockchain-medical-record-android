package my.id.medicalrecordblockchain.ui.patient.detail_doctor

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import my.id.medicalrecordblockchain.R
import my.id.medicalrecordblockchain.data.model.ScheduleTime
import my.id.medicalrecordblockchain.databinding.ItemPickTimeBinding

class TimeAdapter(
    private val onItemClickListener: (String) -> Unit
) : RecyclerView.Adapter<TimeAdapter.ViewHolder>() {

    private val listTime = mutableListOf<ScheduleTime>()

    fun setData(data: List<String>) {
        listTime.clear()
        data.forEachIndexed { index, item ->
            val isSelected = index == 0
            listTime.add(
                ScheduleTime(
                    isSelected = isSelected,
                    time = item
                )
            )
        }
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemPickTimeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding).apply {
            binding.root.setOnClickListener {
                onItemClickListener.invoke(listTime[adapterPosition].time)
                listTime.find { it.isSelected }?.isSelected = false
                listTime[adapterPosition].isSelected = true
                notifyDataSetChanged()
            }
        }
    }

    override fun getItemCount(): Int = listTime.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(listTime[position])
    }

    inner class ViewHolder(private val binding: ItemPickTimeBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindItem(item: ScheduleTime) {
            binding.tvTime.text = item.time

            val textColor = if (item.isSelected) {
                ContextCompat.getColor(itemView.context, R.color.black)
            } else {
                ContextCompat.getColor(itemView.context, R.color.white)
            }
            binding.tvTime.setTextColor(textColor)

            val background = if (item.isSelected) {
                ContextCompat.getDrawable(itemView.context, R.drawable.bg_pick_time_active)
            } else {
                ContextCompat.getDrawable(itemView.context, R.drawable.bg_pick_time)
            }
            binding.root.background = background
        }
    }
}