package com.example.myapplication.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.item.Transact
import java.text.SimpleDateFormat
import java.util.*


class TransactAdapter(private val itemList: MutableList<Transact>) : RecyclerView.Adapter<TransactAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.card_transaction, parent, false)
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
        fun bind(item: Transact) {
            // Bind the data to the views in the item layout
            val textViewType: TextView = itemView.findViewById(R.id.tvType)
            val textViewDate: TextView = itemView.findViewById(R.id.tvDate)
            val textViewAmount: TextView = itemView.findViewById(R.id.tvAmount)


            val dateString = item.transactDate

            val inputDateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            val outputDateFormat = SimpleDateFormat("dd MMMM yyyy", Locale.getDefault())

            val date = inputDateFormat.parse(dateString)
            val formattedDate = outputDateFormat.format(date)

            textViewDate.text = formattedDate
            textViewType.text = item.transactType
            textViewAmount.text = item.transactAmount

            val icon :Button = itemView.findViewById(R.id.iconReload)
            if(textViewType.text == "Purchase"){
                val newDrawable = ContextCompat.getDrawable(itemView.context,
                    R.drawable.transaction_icon_out
                )
                icon.setCompoundDrawablesWithIntrinsicBounds(newDrawable, null, null, null)
            }


        }
    }

}

