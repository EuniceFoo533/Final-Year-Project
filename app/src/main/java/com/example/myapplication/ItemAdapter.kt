import android.app.AlertDialog
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.fragments.Item
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore

class ItemAdapter(private val itemList: MutableList<Item>) : RecyclerView.Adapter<ItemAdapter.ViewHolder>() {

    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var db: FirebaseFirestore
    private lateinit var carsRef: CollectionReference


    fun deleteItem(position: Int) {
        val item = itemList[position]
        val db = FirebaseFirestore.getInstance()
        val collectionRef = db.collection("vehicles")
        val documentRef = collectionRef.document(item.itemId)

        documentRef.delete()
            .addOnSuccessListener {
                itemList.removeAt(position)
                notifyItemRemoved(position)
            }
            .addOnFailureListener { exception ->
                // Handle the failure to delete the document
                Log.e("Firestore", "Error deleting document", exception)
            }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.card_view_design, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = itemList[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    private fun showDeleteConfirmationDialog(context: Context, position: Int) {
        AlertDialog.Builder(context)
            .setTitle("Delete Item")
            .setMessage("Are you sure you want to delete this item?")
            .setPositiveButton("Delete") { dialog, _ ->
                // Call the deleteItem() method of the adapter
                deleteItem(position)
                dialog.dismiss()
            }
            .setNegativeButton("Cancel") { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }



    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: Item) {
            // Bind the data to the views in the item layout
            val nameTextView: TextView = itemView.findViewById(R.id.tvNumber)
            nameTextView.text = item.number

            val deleteButton : Button = itemView.findViewById(R.id.btnDelete)

                deleteButton.setOnClickListener {

                    val position = adapterPosition
                    if (position != RecyclerView.NO_POSITION) {
                        showDeleteConfirmationDialog(itemView.context, position)

                    }
                }



        }
    }
}


