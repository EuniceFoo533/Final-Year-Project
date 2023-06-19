package com.example.myapplication.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.adapter.ParkingAdapter
import com.example.myapplication.item.ItemLocation
import com.example.myapplication.item.ItemParking
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore


class ActiveFragment : Fragment() {

    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var db: FirebaseFirestore
    private lateinit var parkingRef: CollectionReference

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_active, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        db = FirebaseFirestore.getInstance()
        parkingRef = db.collection("parking")
        firebaseAuth = FirebaseAuth.getInstance()

        var currentUser = firebaseAuth.currentUser

        parkingRef
            .whereEqualTo("parking_userID", currentUser!!.uid)
            .get().addOnSuccessListener { documents ->
                if (documents != null && !documents.isEmpty) {
                    val itemList = mutableListOf<ItemParking>()
                    for (document in documents) {
                        val id = document.id
                        val car = document.getString("parking_car")
                        val pDate = document.getString("parking_date")
                        val startTime = document.getString("parking_startTime")
                        val endTime = document.getString("parking_endTime")

                        if (car != null) {
                            val item = ItemParking(id,car,pDate.toString(),startTime.toString(),endTime.toString())
                            itemList.add(item)
                        }
                    }
                    val recyclerView =
                        getView()?.findViewById<RecyclerView>(R.id.recyclerview) // Replace "recyclerView" with your actual RecyclerView ID
                    val adapter = ParkingAdapter(itemList)
                    recyclerView?.adapter = adapter
                    recyclerView?.layoutManager = LinearLayoutManager(requireContext())


                }
            }.addOnFailureListener { exception ->
                // Handle any errors that occurred during data retrieval
                Log.e("Firestore", "Error getting documents: ", exception)
            }

    }
}