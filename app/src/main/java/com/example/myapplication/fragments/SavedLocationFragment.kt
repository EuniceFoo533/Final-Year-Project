package com.example.myapplication.fragments

import LocationAdapter
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.example.myapplication.ItemLocation
import com.example.myapplication.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore


class SavedLocationFragment : Fragment(),LocationAdapter.OnItemClickListener {

    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var db: FirebaseFirestore
    private lateinit var adapter: LocationAdapter
    private lateinit var locationRef: CollectionReference


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_saved_location, container, false)
    }

    override fun onItemClick(item: ItemLocation) {

        val latitude = item.latitude
        val longitude = item.longitude

        val newFragment = GetLocationFragment.newInstance(latitude,longitude)
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.frame_layout, newFragment)
            .addToBackStack(null)
            .commit()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        db = FirebaseFirestore.getInstance()
        locationRef = db.collection("location")
        firebaseAuth = FirebaseAuth.getInstance()

        var currentUser = firebaseAuth.currentUser

        locationRef
            .whereEqualTo("userID", currentUser!!.uid)
            .get().addOnSuccessListener { documents ->
                if (documents != null && !documents.isEmpty) {
                    val itemList = mutableListOf<ItemLocation>()
                    for (document in documents) {
                        val id = document.id
                        val pDate = document.getString("date")
                        val latitude = document.getDouble("latitude")
                        val longitude = document.getDouble("longitude")
                        val cars = document.getString("vehicleNumber")

                        if (cars != null) {
                            val item = ItemLocation(id,latitude,longitude,cars,pDate)
                            itemList.add(item)
                        }
                    }
                    val recyclerView =
                        getView()?.findViewById<RecyclerView>(R.id.recyclerview) // Replace "recyclerView" with your actual RecyclerView ID
                    val adapter = LocationAdapter(itemList,this)
                    recyclerView?.adapter = adapter
                    recyclerView?.layoutManager = LinearLayoutManager(requireContext())


                }
            }.addOnFailureListener { exception ->
                // Handle any errors that occurred during data retrieval
                Log.e("Firestore", "Error getting documents: ", exception)
            }




    }





}
