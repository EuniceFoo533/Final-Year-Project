package com.example.myapplication.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import com.example.myapplication.SavedLocation
import com.example.myapplication.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore

class AddLocationFragment : Fragment() {

    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var db: FirebaseFirestore
    private lateinit var carsRef: CollectionReference


    private fun populateSpinner(itemList: List<String>) {
        val spinner = getView()?.findViewById<Spinner>(R.id.spinnerCar)

        val adapter: ArrayAdapter<String> = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, itemList)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner?.adapter = adapter

        spinner?.setSelection(0)

    }

    companion object {
        private const val ARG_LATITUDE = "latitude"
        private const val ARG_LONGITUDE = "longitude"

        fun newInstance(latitude: String, longitude: String): AddLocationFragment {
            val fragment = AddLocationFragment()
            val args = Bundle()
            args.putString(ARG_LATITUDE, latitude)
            args.putString(ARG_LONGITUDE, longitude)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_add_location, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        firebaseAuth =FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()
        carsRef = db.collection("vehicles")

        var currentUser = firebaseAuth.currentUser

        val hint = "--------Select Your Vehicle--------"
        // Retrieve data into spinner
        carsRef
            .whereEqualTo("userID",currentUser!!.uid)
            .get().addOnSuccessListener { documents ->
                if(documents!=null && !documents.isEmpty)
                {
                    val itemList = mutableListOf<String>().apply {
                        add(hint)
                    }
                    for(document in documents){
                        val item = document.getString("vehicle_number")
                        if(item !=null){
                            itemList.add(item)
                        }
                        populateSpinner(itemList)
                    }
                }

            }.addOnFailureListener{ exception ->
                Log.e("Firestore", "Error getting documents: ", exception)
            }


        val latitude = arguments?.getString(ARG_LATITUDE)
        val longitude = arguments?.getString(ARG_LONGITUDE)

        var latitudeTextView = getView()?.findViewById<TextView>(R.id.tvLatitude)
        var longitudeTextView = getView()?.findViewById<TextView>(R.id.tvLongitude)

        // Use the latitude and longitude values as needed
        // For example, update TextViews with latitude and longitude:
        latitudeTextView?.text = "$latitude"
        longitudeTextView?.text = "$longitude"


        var btnSave = getView()?.findViewById<Button>(R.id.buttonSave)


        btnSave?.setOnClickListener {
            var spinner = getView()?.findViewById<Spinner>(R.id.spinnerCar)
            val selectedItem = spinner?.selectedItem.toString()

            val location = SavedLocation(latitude!!.toDouble(),longitude!!.toDouble(),currentUser!!.uid,selectedItem)
            db.collection("location")
                .document()
                .set(location)
                .addOnSuccessListener { documentReference ->
                    Toast.makeText(context,"Location Have Successfully Stored.",Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener { e ->
                    Toast.makeText(context,"Failed to store.",Toast.LENGTH_SHORT).show()
                }
        }
    }
}