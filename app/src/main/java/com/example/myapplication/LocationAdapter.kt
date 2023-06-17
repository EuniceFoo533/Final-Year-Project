import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.ItemLocation
import com.example.myapplication.R
import com.example.myapplication.fragments.Item
import java.text.SimpleDateFormat
import java.util.*

class LocationAdapter(private val itemList: MutableList<ItemLocation>,private val listener: OnItemClickListener) : RecyclerView.Adapter<LocationAdapter.ViewHolder>() {

    interface OnItemClickListener {
        fun onItemClick(item: ItemLocation)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.card_location, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = itemList[position]
        holder.bind(item)

        //When recycler view item is clicked
        holder.itemView.setOnClickListener{
            listener.onItemClick(item)
        }


    }

    override fun getItemCount(): Int {
        return itemList.size
    }




    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: ItemLocation) {
            // Bind the data to the views in the item layout
            val tvDate: TextView = itemView.findViewById(R.id.tvDate)
            val tvCar: TextView = itemView.findViewById(R.id.tvCar)
            val tvLocation: TextView = itemView.findViewById(R.id.tvLatitude)

            val dateString = item.date

            val inputDateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            val outputDateFormat = SimpleDateFormat("dd MMMM yyyy", Locale.getDefault())

            val date = inputDateFormat.parse(dateString)
            val formattedDate = outputDateFormat.format(date)

            tvDate.text = formattedDate
            tvCar.text = item.vehicle
            tvLocation.text = "("+item.latitude.toString() +","+ item.longitude.toString()+")"




        }
}
}


