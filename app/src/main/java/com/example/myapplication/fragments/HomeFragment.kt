package com.example.myapplication.fragments

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.core.os.bundleOf
import com.example.myapplication.R
import com.example.myapplication.Transaction
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import java.time.LocalDate

class HomeFragment : Fragment() {

    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var db: FirebaseFirestore
    private lateinit var carsRef: CollectionReference
    private lateinit var walletRef: CollectionReference

    private lateinit var radioGroup: RadioGroup

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    private fun populateSpinner(itemList: List<String>) {
        val spinner = getView()?.findViewById<Spinner>(R.id.spinnerCar)

            val adapter: ArrayAdapter<String> = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, itemList)
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner?.adapter = adapter

            spinner?.setSelection(0)



    }


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        firebaseAuth =FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()
        carsRef = db.collection("vehicles")

        var currentUser = firebaseAuth.currentUser

        val walletAmount = getView()?.findViewById<Button>(R.id.button2)

        db.collection("wallet").document(currentUser!!.uid)
            .get().addOnSuccessListener { documents->
                if(documents!=null)
                {
                    val item = documents.getDouble("walletAmount")

                    val amount = "My Wallet\n RM "+ item.toString() +"0"
                    walletAmount?.text = amount
                }
            }

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


        val btnMonthly = getView()?.findViewById<Button>(R.id.btnMontly)
        val btnHourly = getView()?.findViewById<Button>(R.id.btnHourly)
        val btnPurchase = getView()?.findViewById<Button>(R.id.btnPurchase)
        val spinner = getView()?.findViewById<Spinner>(R.id.spinnerCar)

        val btnReload = getView()?.findViewById<Button>(R.id.buttonReload)
        val btn1 = getView()?.findViewById<RadioButton>(R.id.btn1)
        val btn2 = getView()?.findViewById<RadioButton>(R.id.btn2)
        val btn3 = getView()?.findViewById<RadioButton>(R.id.btn3)
        val btn4 = getView()?.findViewById<RadioButton>(R.id.btn4)
        val btn5 = getView()?.findViewById<RadioButton>(R.id.btn5)
        val btn6 = getView()?.findViewById<RadioButton>(R.id.btn6)
        val btn7 = getView()?.findViewById<RadioButton>(R.id.btn7)
        val btn8 = getView()?.findViewById<RadioButton>(R.id.btn8)
        val btn9 = getView()?.findViewById<RadioButton>(R.id.btn9)

        //Radio button and radio group
        val radioGroup1 = getView()?.findViewById<RadioGroup>(R.id.radioGroup1)
        val radioGroup2 = getView()?.findViewById<RadioGroup>(R.id.radioGroup2)
        val radioGroup3 = getView()?.findViewById<RadioGroup>(R.id.radioGroup3)

        radioGroup1?.setOnCheckedChangeListener { group, checkedId ->
            // Clear the selection of radio buttons in radioGroup1 when a button in radioGroup2 is selected
            if (checkedId != -1) {
                radioGroup2?.clearCheck()
                radioGroup3?.clearCheck()

            }

            for (i in 0 until group.childCount) {
                val radioButton = group.getChildAt(i) as RadioButton
                radioButton.setBackgroundColor(Color.TRANSPARENT)
            }

            // Change the background color of the selected radio button
            val selectedRadioButton = getView()?.findViewById<RadioButton>(checkedId)
            selectedRadioButton?.setBackgroundColor(resources.getColor(R.color.purple_200))

        }

        radioGroup2?.setOnCheckedChangeListener { group, checkedId ->
            // Clear the selection of radio buttons in radioGroup2 when a button in radioGroup1 is selected
            if (checkedId != -1) {
                radioGroup1?.clearCheck()
                radioGroup3?.clearCheck()
            }

            for (i in 0 until group.childCount) {
                val radioButton = group.getChildAt(i) as RadioButton
                radioButton.setBackgroundColor(Color.TRANSPARENT)
            }

            // Change the background color of the selected radio button
            val selectedRadioButton = getView()?.findViewById<RadioButton>(checkedId)
            selectedRadioButton?.setBackgroundColor(resources.getColor(R.color.purple_200))

        }


        radioGroup3?.setOnCheckedChangeListener { group, checkedId ->
            // Clear the selection of radio buttons in radioGroup2 when a button in radioGroup1 is selected
            if (checkedId != -1) {
                radioGroup1?.clearCheck()
                radioGroup2?.clearCheck()

            }

            for (i in 0 until group.childCount) {
                val radioButton = group.getChildAt(i) as RadioButton
                radioButton.setBackgroundColor(Color.TRANSPARENT)
            }

            // Change the background color of the selected radio button
            val selectedRadioButton = getView()?.findViewById<RadioButton>(checkedId)
            selectedRadioButton?.setBackgroundColor(resources.getColor(R.color.purple_200))

        }


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

        btnPurchase?.setOnClickListener{
            if(btn1!!.isChecked && spinner?.selectedItem !="--------Select Your Vehicle--------")
            {

                val builder = AlertDialog.Builder(context)
                builder.setTitle("Confirm Purchase")
                builder.setMessage("Are you sure you want to purchase " + btn1.text + "?" )
                //builder.setPositiveButton("OK", DialogInterface.OnClickListener(function = x))

                builder.setPositiveButton(android.R.string.yes) { dialog, which ->
                    storeFirestore(currentUser,0.4)
                    Toast.makeText(requireContext(),
                       "Purchase Successfully!", Toast.LENGTH_SHORT).show()
                }

                builder.setNegativeButton(android.R.string.no) { dialog, which ->
                    Toast.makeText(requireContext(),
                        "You Have Cancel Your Purchase", Toast.LENGTH_SHORT).show()
                }

                builder.show()

            }
            else if(spinner?.selectedItem =="--------Select Your Vehicle--------"){
                Toast.makeText(requireContext(),
                    "Missing vehicle", Toast.LENGTH_SHORT).show()
            }

     }

    @RequiresApi(Build.VERSION_CODES.O)
    fun storeFirestore(currentUser: FirebaseUser, price: Double)
    {

        val transactionType = "Purchase"
        val currentDate = LocalDate.now().toString()

        var existAmount : Double = 0.0

        db.collection("wallet")
                .document(currentUser!!.uid)
                .get().addOnSuccessListener { documents->
                    if(documents!=null)
                    {
                        val item = documents.getDouble("walletAmount")
                        if (item != null) {
                            existAmount = item
                            existAmount -= price

                            val fieldUpdates = HashMap<String, Any>()
                            fieldUpdates["walletAmount"] = existAmount


                            db.collection("wallet")
                                .document(currentUser!!.uid)
                                .update(fieldUpdates)
                                .addOnSuccessListener{
                                    Toast.makeText(context,"Successfully purchase.", Toast.LENGTH_SHORT).show()

                                }.addOnFailureListener{
                                    Toast.makeText(context,"Failed.", Toast.LENGTH_SHORT).show()

                                }
                        }
                    }
                }




            val transaction = Transaction(transactionType,"+ RM "+ price +"0",currentDate,currentUser!!.uid)
            db.collection("transaction").document()
                .set(transaction)
                .addOnSuccessListener { documentReference ->

                }
                .addOnFailureListener { e ->


                }


    }
}

    @RequiresApi(Build.VERSION_CODES.O)
    private fun storeFirestore(currentUser: FirebaseUser, price: Double) {

        var currentDate = LocalDate.now().toString()
        var transactionType = "Purchase"

        var existAmount : Double = 0.0

        walletRef = db.collection("wallet")

            walletRef.document(currentUser!!.uid)
                .get().addOnSuccessListener { documents->
                    if(documents!=null)
                    {
                        val item = documents.getDouble("walletAmount")
                        if (item != null) {
                            existAmount = item
                            existAmount -= price

                            val fieldUpdates = HashMap<String, Any>()
                            fieldUpdates["walletAmount"] = existAmount


                            walletRef
                                .document(currentUser!!.uid)
                                .update(fieldUpdates)
                                .addOnSuccessListener{
                                    Toast.makeText(context,"Your wallet has been successfully deduct.", Toast.LENGTH_SHORT).show()

                                }.addOnFailureListener{
                                    Toast.makeText(context,"Failed deduct.", Toast.LENGTH_SHORT).show()

                                }
                        }
                    }
                }





            val transaction = Transaction(transactionType,"- RM "+ price +"0",currentDate,currentUser!!.uid)
            db.collection("transaction").document()
                .set(transaction)
                .addOnSuccessListener { documentReference ->

                }
                .addOnFailureListener { e ->


                }

    }

}
