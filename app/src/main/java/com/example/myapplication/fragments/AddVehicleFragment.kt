package com.example.myapplication.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import com.example.myapplication.R
import com.example.myapplication.database.Vehicles
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class AddVehicleFragment : Fragment() {

    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var db: FirebaseFirestore

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_vehicle, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val type = resources.getStringArray(R.array.Vehicle)

        firebaseAuth =FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()

        var currentUser = firebaseAuth.currentUser

        var spinner = getView()?.findViewById<Spinner>(R.id.edtType)

        var vNumber = getView()?.findViewById<EditText>(R.id.edtNumber)
        var vName = getView()?.findViewById<EditText>(R.id.edtName)
        var btnAdd = getView()?.findViewById<Button>(R.id.buttonAdd)

        if(spinner!= null){
            val adapter = ArrayAdapter(requireContext(),android.R.layout.simple_spinner_dropdown_item,type)
            spinner.adapter = adapter
        }

        btnAdd?.setOnClickListener {
            val selectedType = spinner?.selectedItem.toString()
            val number = vNumber?.text.toString()
            val name = vName?.text.toString()


            if(number.isEmpty())
            {
                Toast.makeText(context,"Please Enter Your Vehicle Number.",Toast.LENGTH_SHORT).show()
            }

            else if(name.isEmpty())
            {
                Toast.makeText(context,"Please Enter Your Vehicle Name.",Toast.LENGTH_SHORT).show()
            }

            else
            {
                val vehicle = Vehicles(number,selectedType,name,currentUser!!.uid)

                // Add a new document with a generated ID
                db.collection("vehicles")
                    .add(vehicle)
                    .addOnSuccessListener { documentReference ->
                        Toast.makeText(context,"Your vehicle has been successfully added.",Toast.LENGTH_SHORT).show()

                    }
                    .addOnFailureListener { e ->
                        Toast.makeText(context,"Failed to Add!",Toast.LENGTH_SHORT).show()

                    }


            }



        }








    }
}