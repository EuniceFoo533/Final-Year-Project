package com.example.myapplication.fragments

import com.example.myapplication.adapter.ItemAdapter
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.item.Item
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore


class VehicleFragment : Fragment() {

    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var db: FirebaseFirestore
    private lateinit var carsRef: CollectionReference

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_vehicle, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val btnAdd = getView()?.findViewById<Button>(R.id.buttonAdd)

        btnAdd?.setOnClickListener {

            val transaction = activity?.supportFragmentManager?.beginTransaction()
            transaction?.replace(R.id.frame_layout, AddVehicleFragment())
            transaction?.disallowAddToBackStack()
            transaction?.commit()
        }

        db = FirebaseFirestore.getInstance()
        carsRef = db.collection("vehicles")
        firebaseAuth = FirebaseAuth.getInstance()

        var currentUser = firebaseAuth.currentUser

        carsRef
            .whereEqualTo("userID", currentUser!!.uid)
            .get().addOnSuccessListener { documents ->
                if (documents != null && !documents.isEmpty) {
                    val itemList = mutableListOf<Item>()
                    for (document in documents) {
                        val id = document.id
                        val name = document.getString("vehicle_number")
                        if (name != null) {
                            val item = Item(id,name)
                            itemList.add(item)
                        }
                    }
                    val recyclerView =
                        getView()?.findViewById<RecyclerView>(R.id.recyclerview) // Replace "recyclerView" with your actual RecyclerView ID
                    val adapter = ItemAdapter(itemList)
                    recyclerView?.adapter = adapter
                    recyclerView?.layoutManager = LinearLayoutManager(requireContext())


                }
            }.addOnFailureListener { exception ->
                // Handle any errors that occurred during data retrieval
                Log.e("Firestore", "Error getting documents: ", exception)
            }

    }




}
