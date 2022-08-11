package net.mastery.driverutility.screens

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import net.mastery.driverutility.R
import net.mastery.driverutility.models.Driver


class DriverListAdapter(
    private val drivers: List<Driver>,
    private val listener: OnItemClickListener
) : RecyclerView.Adapter<DriverListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_cell_two_line, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val driver = drivers[position]

        holder.textView.text = "${driver.lastName}, ${driver.firstName}"
        holder.subtextView.text = "${driver.phoneNumber}"

        listener.let { onItemClickListener ->
            holder.textView.setOnClickListener { onItemClickListener.onItemClick(driver) }
        }
    }

    interface OnItemClickListener {
        fun onItemClick(item: Driver?)
    }

    override fun getItemCount(): Int {
        return drivers.size
    }

    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val textView: TextView = itemView.findViewById(R.id.list_item_text)
        val subtextView: TextView = itemView.findViewById(R.id.list_item_secondary_text)
    }
}