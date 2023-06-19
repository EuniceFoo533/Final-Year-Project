package com.example.myapplication.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.fragments.ActiveFragment
import com.example.myapplication.item.ItemParking
import java.text.SimpleDateFormat
import java.util.*


class ParkingAdapter(private val itemList: MutableList<ItemParking>) : RecyclerView.Adapter<ParkingAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.card_active_parking, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = itemList[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: ItemParking) {
            // Bind the data to the views in the item layout
            val pDate: TextView = itemView.findViewById(R.id.tvDate)
            val pCar: TextView = itemView.findViewById(R.id.tvCar)
            val pStart: TextView = itemView.findViewById(R.id.tvStartTime)
            val pEnd: TextView = itemView.findViewById(R.id.tvEndTime)


            val dateString = item.parking_date

            val inputDateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            val outputDateFormat = SimpleDateFormat("dd MMMM yyyy", Locale.getDefault())

            val date = inputDateFormat.parse(dateString)
            val formattedDate = outputDateFormat.format(date)

            pDate.text = formattedDate
            pCar.text = item.parking_car
            pStart.text = item.parking_startTime
            pEnd.text = item.parking_endTime




        }
    }

}

