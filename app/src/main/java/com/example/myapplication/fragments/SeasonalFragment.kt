package com.example.myapplication.fragments

import android.app.AlertDialog
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
import com.example.myapplication.R
import com.example.myapplication.database.Transaction
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import java.text.DecimalFormat
import java.time.LocalDate

class SeasonalFragment : Fragment() {

    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var db: FirebaseFirestore
    private lateinit var carsRef: CollectionReference
    private lateinit var walletRef: CollectionReference

    private fun populateSpinner(itemList: List<String>) {
        val spinner = getView()?.findViewById<Spinner>(R.id.spinnerCar)
        val adapter: ArrayAdapter<String> = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, itemList)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner?.adapter = adapter

        spinner?.setSelection(0)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_seasonal, container, false)
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

                    val decimalFormat = DecimalFormat("#.00")
                    val formattedValue = decimalFormat.format(item)
                    val amount = "My Wallet\n RM $formattedValue"

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


        val btnHourly = getView()?.findViewById<Button>(R.id.btnHourly)
        btnHourly?.setOnClickListener {
            Toast.makeText(context,"Hourly Pass is Selected", Toast.LENGTH_SHORT).show()

            val transaction = activity?.supportFragmentManager?.beginTransaction()
            transaction?.replace(R.id.frame_layout,HomeFragment())
            transaction?.disallowAddToBackStack()
            transaction?.commit()
        }

        val btnMonthly = getView()?.findViewById<Button>(R.id.btnMontly)
        btnMonthly?.setOnClickListener {
            Toast.makeText(context,"Seasonal Pass is Selected", Toast.LENGTH_SHORT).show()
        }

        val btnReload = getView()?.findViewById<Button>(R.id.buttonReload)
        btnReload?.setOnClickListener {
            val transaction = activity?.supportFragmentManager?.beginTransaction()
            transaction?.replace(R.id.frame_layout,ReloadFragment())
            transaction?.disallowAddToBackStack()
            transaction?.commit()
        }

        val btn1 = getView()?.findViewById<RadioButton>(R.id.btn1)
        val btn2 = getView()?.findViewById<RadioButton>(R.id.btn2)
        val btn3 = getView()?.findViewById<RadioButton>(R.id.btn3)
        val btn4 = getView()?.findViewById<RadioButton>(R.id.btn4)
        val btn5 = getView()?.findViewById<RadioButton>(R.id.btn5)
        val btn6 = getView()?.findViewById<RadioButton>(R.id.btn6)
        val btn7 = getView()?.findViewById<RadioButton>(R.id.btn7)

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

        val btnPurchase = getView()?.findViewById<Button>(R.id.btnPurchase)
        val spinner = getView()?.findViewById<Spinner>(R.id.spinnerCar)

        btnPurchase?.setOnClickListener{
            if(btn1!!.isChecked && spinner?.selectedItem !="--------Select Your Vehicle--------")
            {
                val price :Double = 23.00

                val builder = AlertDialog.Builder(context)
                builder.setTitle("Confirmation")
                builder.setMessage("Please Confirm Your Purchase\n\nPass: 1 week\nVehicle: " + spinner?.selectedItem.toString()
                        + "\n\nRM " + price.toString() + "0 Will Be Deduct From Your Wallet." )

                builder.setPositiveButton(android.R.string.yes) { dialog, which ->
                    storeFirestore(currentUser,price)
                    Toast.makeText(requireContext(),
                        "Purchase Successfully!", Toast.LENGTH_SHORT).show()
                }

                builder.setNegativeButton(android.R.string.no) { dialog, which ->
                    Toast.makeText(requireContext(),
                        "You Have Cancel Your Purchase", Toast.LENGTH_SHORT).show()
                }

                builder.show()

            }

            else if(btn2!!.isChecked && spinner?.selectedItem !="--------Select Your Vehicle--------")
            {
                val price :Double = 90.00

                val builder = AlertDialog.Builder(context)
                builder.setTitle("Confirmation")
                builder.setMessage("Please Confirm Your Purchase\n\nPass: 1 month\nVehicle: " + spinner?.selectedItem.toString()
                        + "\n\nRM " + price.toString() + "0 Will Be Deduct From Your Wallet." )

                builder.setPositiveButton(android.R.string.yes) { dialog, which ->
                    storeFirestore(currentUser,price)
                    Toast.makeText(requireContext(),
                        "Purchase Successfully!", Toast.LENGTH_SHORT).show()
                }

                builder.setNegativeButton(android.R.string.no) { dialog, which ->
                    Toast.makeText(requireContext(),
                        "You Have Cancel Your Purchase", Toast.LENGTH_SHORT).show()
                }

                builder.show()

            }

            else if(btn3!!.isChecked && spinner?.selectedItem !="--------Select Your Vehicle--------")
            {
                val price :Double = 180.00

                val builder = AlertDialog.Builder(context)
                builder.setTitle("Confirmation")
                builder.setMessage("Please Confirm Your Purchase\n\nPass: 2 months\nVehicle: " + spinner?.selectedItem.toString()
                        + "\n\nRM " + price.toString() + "0 Will Be Deduct From Your Wallet." )

                builder.setPositiveButton(android.R.string.yes) { dialog, which ->
                    storeFirestore(currentUser,price)
                    Toast.makeText(requireContext(),
                        "Purchase Successfully!", Toast.LENGTH_SHORT).show()
                }

                builder.setNegativeButton(android.R.string.no) { dialog, which ->
                    Toast.makeText(requireContext(),
                        "You Have Cancel Your Purchase", Toast.LENGTH_SHORT).show()
                }

                builder.show()

            }

            else if(btn4!!.isChecked && spinner?.selectedItem !="--------Select Your Vehicle--------")
            {
                val price :Double = 225.00

                val builder = AlertDialog.Builder(context)
                builder.setTitle("Confirmation")
                builder.setMessage("Please Confirm Your Purchase\n\nPass: 3 months\nVehicle: " + spinner?.selectedItem.toString()
                        + "\n\nRM " + price.toString() + "0 Will Be Deduct From Your Wallet." )

                builder.setPositiveButton(android.R.string.yes) { dialog, which ->
                    storeFirestore(currentUser,price)
                    Toast.makeText(requireContext(),
                        "Purchase Successfully!", Toast.LENGTH_SHORT).show()
                }

                builder.setNegativeButton(android.R.string.no) { dialog, which ->
                    Toast.makeText(requireContext(),
                        "You Have Cancel Your Purchase", Toast.LENGTH_SHORT).show()
                }

                builder.show()

            }

            else if(btn5!!.isChecked && spinner?.selectedItem !="--------Select Your Vehicle--------")
            {
                val price :Double = 360.00

                val builder = AlertDialog.Builder(context)
                builder.setTitle("Confirmation")
                builder.setMessage("Please Confirm Your Purchase\n\nPass: 4 months\nVehicle: " + spinner?.selectedItem.toString()
                        + "\n\nRM " + price.toString() + "0 Will Be Deduct From Your Wallet." )

                builder.setPositiveButton(android.R.string.yes) { dialog, which ->
                    storeFirestore(currentUser,price)
                    Toast.makeText(requireContext(),
                        "Purchase Successfully!", Toast.LENGTH_SHORT).show()
                }

                builder.setNegativeButton(android.R.string.no) { dialog, which ->
                    Toast.makeText(requireContext(),
                        "You Have Cancel Your Purchase", Toast.LENGTH_SHORT).show()
                }

                builder.show()

            }

            else if(btn6!!.isChecked && spinner?.selectedItem !="--------Select Your Vehicle--------")
            {
                val price :Double = 450.00

                val builder = AlertDialog.Builder(context)
                builder.setTitle("Confirmation")
                builder.setMessage("Please Confirm Your Purchase\n\nPass: 5 months\nVehicle: " + spinner?.selectedItem.toString()
                        + "\n\nRM " + price.toString() + "0 Will Be Deduct From Your Wallet." )

                builder.setPositiveButton(android.R.string.yes) { dialog, which ->
                    storeFirestore(currentUser,price)
                    Toast.makeText(requireContext(),
                        "Purchase Successfully!", Toast.LENGTH_SHORT).show()
                }

                builder.setNegativeButton(android.R.string.no) { dialog, which ->
                    Toast.makeText(requireContext(),
                        "You Have Cancel Your Purchase", Toast.LENGTH_SHORT).show()
                }

                builder.show()

            }

            else if(btn7!!.isChecked && spinner?.selectedItem !="--------Select Your Vehicle--------")
            {
                val price :Double = 540.00

                val builder = AlertDialog.Builder(context)
                builder.setTitle("Confirmation")
                builder.setMessage("Please Confirm Your Purchase\n\nPass: 6 months\nVehicle: " + spinner?.selectedItem.toString()
                        + "\n\nRM " + price.toString() + "0 Will Be Deduct From Your Wallet." )

                builder.setPositiveButton(android.R.string.yes) { dialog, which ->
                    storeFirestore(currentUser,price)
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