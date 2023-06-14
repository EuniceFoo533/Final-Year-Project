package com.example.myapplication.fragments

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import com.example.myapplication.R
import com.example.myapplication.databinding.ActivityLoginBinding
import com.example.myapplication.databinding.FragmentHomeBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore

class HomeFragment : Fragment() {

    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var db: FirebaseFirestore
    private lateinit var carsRef: CollectionReference

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    private fun populateSpinner(itemList: List<String>) {
        val spinner = getView()?.findViewById<Spinner>(R.id.spinnerCar)
        val adapter: ArrayAdapter<String> = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, itemList)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner?.adapter = adapter
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        firebaseAuth =FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()
        carsRef = db.collection("vehicles")

        var currentUser = firebaseAuth.currentUser

        // Retrieve data into spinner
        carsRef
            .whereEqualTo("userID",currentUser!!.uid)
            .get().addOnSuccessListener { documents ->
            if(documents!=null && !documents.isEmpty)
            {
                val itemList = mutableListOf<String>()
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


        val btnMonthly = getView()?.findViewById<Button>(R.id.btnMontly)
        val btnHourly = getView()?.findViewById<Button>(R.id.btnHourly)

        val btnReload = getView()?.findViewById<Button>(R.id.buttonReload)
        val btn1 = getView()?.findViewById<Button>(R.id.btn1)
        val btn2 = getView()?.findViewById<Button>(R.id.btn2)
        val btn3 = getView()?.findViewById<Button>(R.id.btn3)
        val btn4 = getView()?.findViewById<Button>(R.id.btn4)
        val btn5 = getView()?.findViewById<Button>(R.id.btn5)
        val btn6 = getView()?.findViewById<Button>(R.id.btn6)
        val btn7 = getView()?.findViewById<Button>(R.id.btn7)
        val btn8 = getView()?.findViewById<Button>(R.id.btn8)
        val btn9 = getView()?.findViewById<Button>(R.id.btn9)


        btnHourly?.setOnClickListener {
            Toast.makeText(context,"Hourly Pass is Selected",Toast.LENGTH_SHORT).show()
        }

        btnMonthly?.setOnClickListener {
            Toast.makeText(context,"Seasonal Pass is Selected",Toast.LENGTH_SHORT).show()

            val transaction = activity?.supportFragmentManager?.beginTransaction()
            transaction?.replace(R.id.frame_layout,SeasonalFragment())
            transaction?.disallowAddToBackStack()
            transaction?.commit()
        }


        btnReload?.setOnClickListener {
            val transaction = activity?.supportFragmentManager?.beginTransaction()
            transaction?.replace(R.id.frame_layout,ReloadFragment())
            transaction?.disallowAddToBackStack()
            transaction?.commit()
        }

        btn1?.setOnClickListener {
            btn1?.setBackgroundColor(resources.getColor(R.color.purple))
            btn1.setTextColor(resources.getColor(R.color.white))

            btn2?.setBackgroundColor(resources.getColor(R.color.grey))
            btn2?.setTextColor(resources.getColor(R.color.blue))

            btn3?.setBackgroundColor(resources.getColor(R.color.grey))
            btn3?.setTextColor(resources.getColor(R.color.blue))

            btn4?.setBackgroundColor(resources.getColor(R.color.grey))
            btn4?.setTextColor(resources.getColor(R.color.blue))

            btn5?.setBackgroundColor(resources.getColor(R.color.grey))
            btn5?.setTextColor(resources.getColor(R.color.blue))

            btn6?.setBackgroundColor(resources.getColor(R.color.grey))
            btn6?.setTextColor(resources.getColor(R.color.blue))

            btn7?.setBackgroundColor(resources.getColor(R.color.grey))
            btn7?.setTextColor(resources.getColor(R.color.blue))

            btn8?.setBackgroundColor(resources.getColor(R.color.grey))
            btn8?.setTextColor(resources.getColor(R.color.blue))

            btn9?.setBackgroundColor(resources.getColor(R.color.grey))
            btn9?.setTextColor(resources.getColor(R.color.blue))

        }

        btn2?.setOnClickListener {
            btn2?.setBackgroundColor(resources.getColor(R.color.purple))
            btn2.setTextColor(resources.getColor(R.color.white))

            btn1?.setBackgroundColor(resources.getColor(R.color.grey))
            btn1?.setTextColor(resources.getColor(R.color.blue))

            btn3?.setBackgroundColor(resources.getColor(R.color.grey))
            btn3?.setTextColor(resources.getColor(R.color.blue))

            btn4?.setBackgroundColor(resources.getColor(R.color.grey))
            btn4?.setTextColor(resources.getColor(R.color.blue))

            btn5?.setBackgroundColor(resources.getColor(R.color.grey))
            btn5?.setTextColor(resources.getColor(R.color.blue))

            btn6?.setBackgroundColor(resources.getColor(R.color.grey))
            btn6?.setTextColor(resources.getColor(R.color.blue))

            btn7?.setBackgroundColor(resources.getColor(R.color.grey))
            btn7?.setTextColor(resources.getColor(R.color.blue))

            btn8?.setBackgroundColor(resources.getColor(R.color.grey))
            btn8?.setTextColor(resources.getColor(R.color.blue))

            btn9?.setBackgroundColor(resources.getColor(R.color.grey))
            btn9?.setTextColor(resources.getColor(R.color.blue))

        }

        btn3?.setOnClickListener {
            btn3?.setBackgroundColor(resources.getColor(R.color.purple))
            btn3.setTextColor(resources.getColor(R.color.white))

            btn1?.setBackgroundColor(resources.getColor(R.color.grey))
            btn1?.setTextColor(resources.getColor(R.color.blue))

            btn2?.setBackgroundColor(resources.getColor(R.color.grey))
            btn2?.setTextColor(resources.getColor(R.color.blue))

            btn4?.setBackgroundColor(resources.getColor(R.color.grey))
            btn4?.setTextColor(resources.getColor(R.color.blue))

            btn5?.setBackgroundColor(resources.getColor(R.color.grey))
            btn5?.setTextColor(resources.getColor(R.color.blue))

            btn6?.setBackgroundColor(resources.getColor(R.color.grey))
            btn6?.setTextColor(resources.getColor(R.color.blue))

            btn7?.setBackgroundColor(resources.getColor(R.color.grey))
            btn7?.setTextColor(resources.getColor(R.color.blue))

            btn8?.setBackgroundColor(resources.getColor(R.color.grey))
            btn8?.setTextColor(resources.getColor(R.color.blue))

            btn9?.setBackgroundColor(resources.getColor(R.color.grey))
            btn9?.setTextColor(resources.getColor(R.color.blue))

        }

        btn4?.setOnClickListener {
            btn4?.setBackgroundColor(resources.getColor(R.color.purple))
            btn4.setTextColor(resources.getColor(R.color.white))

            btn1?.setBackgroundColor(resources.getColor(R.color.grey))
            btn1?.setTextColor(resources.getColor(R.color.blue))

            btn3?.setBackgroundColor(resources.getColor(R.color.grey))
            btn3?.setTextColor(resources.getColor(R.color.blue))

            btn2?.setBackgroundColor(resources.getColor(R.color.grey))
            btn2?.setTextColor(resources.getColor(R.color.blue))

            btn5?.setBackgroundColor(resources.getColor(R.color.grey))
            btn5?.setTextColor(resources.getColor(R.color.blue))

            btn6?.setBackgroundColor(resources.getColor(R.color.grey))
            btn6?.setTextColor(resources.getColor(R.color.blue))

            btn7?.setBackgroundColor(resources.getColor(R.color.grey))
            btn7?.setTextColor(resources.getColor(R.color.blue))

            btn8?.setBackgroundColor(resources.getColor(R.color.grey))
            btn8?.setTextColor(resources.getColor(R.color.blue))

            btn9?.setBackgroundColor(resources.getColor(R.color.grey))
            btn9?.setTextColor(resources.getColor(R.color.blue))

        }

        btn5?.setOnClickListener {
            btn5?.setBackgroundColor(resources.getColor(R.color.purple))
            btn5.setTextColor(resources.getColor(R.color.white))

            btn1?.setBackgroundColor(resources.getColor(R.color.grey))
            btn1?.setTextColor(resources.getColor(R.color.blue))

            btn3?.setBackgroundColor(resources.getColor(R.color.grey))
            btn3?.setTextColor(resources.getColor(R.color.blue))

            btn4?.setBackgroundColor(resources.getColor(R.color.grey))
            btn4?.setTextColor(resources.getColor(R.color.blue))

            btn2?.setBackgroundColor(resources.getColor(R.color.grey))
            btn2?.setTextColor(resources.getColor(R.color.blue))

            btn6?.setBackgroundColor(resources.getColor(R.color.grey))
            btn6?.setTextColor(resources.getColor(R.color.blue))

            btn7?.setBackgroundColor(resources.getColor(R.color.grey))
            btn7?.setTextColor(resources.getColor(R.color.blue))

            btn8?.setBackgroundColor(resources.getColor(R.color.grey))
            btn8?.setTextColor(resources.getColor(R.color.blue))

            btn9?.setBackgroundColor(resources.getColor(R.color.grey))
            btn9?.setTextColor(resources.getColor(R.color.blue))

        }

        btn6?.setOnClickListener {
            btn6?.setBackgroundColor(resources.getColor(R.color.purple))
            btn6.setTextColor(resources.getColor(R.color.white))

            btn1?.setBackgroundColor(resources.getColor(R.color.grey))
            btn1?.setTextColor(resources.getColor(R.color.blue))

            btn3?.setBackgroundColor(resources.getColor(R.color.grey))
            btn3?.setTextColor(resources.getColor(R.color.blue))

            btn4?.setBackgroundColor(resources.getColor(R.color.grey))
            btn4?.setTextColor(resources.getColor(R.color.blue))

            btn5?.setBackgroundColor(resources.getColor(R.color.grey))
            btn5?.setTextColor(resources.getColor(R.color.blue))

            btn2?.setBackgroundColor(resources.getColor(R.color.grey))
            btn2?.setTextColor(resources.getColor(R.color.blue))

            btn7?.setBackgroundColor(resources.getColor(R.color.grey))
            btn7?.setTextColor(resources.getColor(R.color.blue))

            btn8?.setBackgroundColor(resources.getColor(R.color.grey))
            btn8?.setTextColor(resources.getColor(R.color.blue))

            btn9?.setBackgroundColor(resources.getColor(R.color.grey))
            btn9?.setTextColor(resources.getColor(R.color.blue))

        }

        btn7?.setOnClickListener {
            btn7?.setBackgroundColor(resources.getColor(R.color.purple))
            btn7.setTextColor(resources.getColor(R.color.white))

            btn1?.setBackgroundColor(resources.getColor(R.color.grey))
            btn1?.setTextColor(resources.getColor(R.color.blue))

            btn3?.setBackgroundColor(resources.getColor(R.color.grey))
            btn3?.setTextColor(resources.getColor(R.color.blue))

            btn4?.setBackgroundColor(resources.getColor(R.color.grey))
            btn4?.setTextColor(resources.getColor(R.color.blue))

            btn5?.setBackgroundColor(resources.getColor(R.color.grey))
            btn5?.setTextColor(resources.getColor(R.color.blue))

            btn6?.setBackgroundColor(resources.getColor(R.color.grey))
            btn6?.setTextColor(resources.getColor(R.color.blue))

            btn2?.setBackgroundColor(resources.getColor(R.color.grey))
            btn2?.setTextColor(resources.getColor(R.color.blue))

            btn8?.setBackgroundColor(resources.getColor(R.color.grey))
            btn8?.setTextColor(resources.getColor(R.color.blue))

            btn9?.setBackgroundColor(resources.getColor(R.color.grey))
            btn9?.setTextColor(resources.getColor(R.color.blue))

        }

        btn8?.setOnClickListener {
            btn8?.setBackgroundColor(resources.getColor(R.color.purple))
            btn8.setTextColor(resources.getColor(R.color.white))

            btn1?.setBackgroundColor(resources.getColor(R.color.grey))
            btn1?.setTextColor(resources.getColor(R.color.blue))

            btn3?.setBackgroundColor(resources.getColor(R.color.grey))
            btn3?.setTextColor(resources.getColor(R.color.blue))

            btn4?.setBackgroundColor(resources.getColor(R.color.grey))
            btn4?.setTextColor(resources.getColor(R.color.blue))

            btn5?.setBackgroundColor(resources.getColor(R.color.grey))
            btn5?.setTextColor(resources.getColor(R.color.blue))

            btn6?.setBackgroundColor(resources.getColor(R.color.grey))
            btn6?.setTextColor(resources.getColor(R.color.blue))

            btn7?.setBackgroundColor(resources.getColor(R.color.grey))
            btn7?.setTextColor(resources.getColor(R.color.blue))

            btn2?.setBackgroundColor(resources.getColor(R.color.grey))
            btn2?.setTextColor(resources.getColor(R.color.blue))

            btn9?.setBackgroundColor(resources.getColor(R.color.grey))
            btn9?.setTextColor(resources.getColor(R.color.blue))

        }

        btn9?.setOnClickListener {
            btn9?.setBackgroundColor(resources.getColor(R.color.purple))
            btn9.setTextColor(resources.getColor(R.color.white))

            btn1?.setBackgroundColor(resources.getColor(R.color.grey))
            btn1?.setTextColor(resources.getColor(R.color.blue))

            btn3?.setBackgroundColor(resources.getColor(R.color.grey))
            btn3?.setTextColor(resources.getColor(R.color.blue))

            btn4?.setBackgroundColor(resources.getColor(R.color.grey))
            btn4?.setTextColor(resources.getColor(R.color.blue))

            btn5?.setBackgroundColor(resources.getColor(R.color.grey))
            btn5?.setTextColor(resources.getColor(R.color.blue))

            btn6?.setBackgroundColor(resources.getColor(R.color.grey))
            btn6?.setTextColor(resources.getColor(R.color.blue))

            btn7?.setBackgroundColor(resources.getColor(R.color.grey))
            btn7?.setTextColor(resources.getColor(R.color.blue))

            btn8?.setBackgroundColor(resources.getColor(R.color.grey))
            btn8?.setTextColor(resources.getColor(R.color.blue))

            btn2?.setBackgroundColor(resources.getColor(R.color.grey))
            btn2?.setTextColor(resources.getColor(R.color.blue))

        }



    }
}