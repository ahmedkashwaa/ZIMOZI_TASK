package com.example.zimozitask.presentation.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.zimozitask.R
import com.example.zimozitask.data.databaase.DatabaseTable

class RvPagedListAdapter :
    PagingDataAdapter<DatabaseTable, RvPagedListAdapter.ViewHolder>(DIFFUTIL_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.rv_list, parent, false)
        )
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position)!!)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val time = itemView.findViewById<TextView>(R.id.time)
        val date = itemView.findViewById<TextView>(R.id.date)
        val battery_perc = itemView.findViewById<TextView>(R.id.battery)

        fun bind(item: DatabaseTable) = with(itemView) {
            time.text = "Time - ${item.time}"
            date.text = "Date - ${item.date}"
            battery_perc.text = "Battery % - ${item.battery.toInt()}"
        }
    }
companion object{
    private val DIFFUTIL_CALLBACK = object: DiffUtil.ItemCallback<DatabaseTable>(){
        override fun areItemsTheSame(oldItem: DatabaseTable, newItem: DatabaseTable): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: DatabaseTable, newItem: DatabaseTable): Boolean {
            return oldItem == newItem
        }
    }
}

}


