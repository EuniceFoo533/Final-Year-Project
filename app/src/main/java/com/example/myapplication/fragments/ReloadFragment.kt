package com.example.myapplication.fragments

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.example.myapplication.R
import com.example.myapplication.Transaction
import com.example.myapplication.Vehicles
import com.example.myapplication.Wallet
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import java.time.LocalDate


class ReloadFragment : Fragment() {

    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var db: FirebaseFirestore
    private lateinit var walletRef: CollectionReference



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_reload, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        firebaseAuth =FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()
        walletRef = db.collection("wallet")

        var currentUser = firebaseAuth.currentUser

        var walletAmount = getView()?.findViewById<EditText>(R.id.editAmount)
        var btnReload = getView()?.findViewById<Button>(R.id.btnReload)

        btnReload?.setOnClickListener {
            var str : String = walletAmount?.text.toString()
            var amount : Double = str.toDouble()

            var currentDate = LocalDate.now().toString()
            var transactionType = "Reload"

            var existAmount : Double = 0.0


            if(str.isNotEmpty())
            {
                db.collection("wallet").document(currentUser!!.uid)
                    .get().addOnSuccessListener { documents->
                        if(documents!=null)
                        {
                            val item = documents.getDouble("walletAmount")
                            if (item != null) {
                                existAmount = item
                                existAmount += amount

                                val fieldUpdates = HashMap<String, Any>()
                                fieldUpdates["walletAmount"] = existAmount


                                walletRef
                                    .document(currentUser!!.uid)
                                    .update(fieldUpdates)
                                    .addOnSuccessListener{
                                        Toast.makeText(context,"Your wallet has been successfully reload.", Toast.LENGTH_SHORT).show()

                                    }.addOnFailureListener{
                                        Toast.makeText(context,"Failed reload.", Toast.LENGTH_SHORT).show()

                                    }
                            }
                        }
                    }





                val transaction = Transaction(transactionType,"+ RM "+ amount.toString() +"0",currentDate,currentUser!!.uid)
                db.collection("transaction").document()
                    .set(transaction)
                    .addOnSuccessListener { documentReference ->

                    }
                    .addOnFailureListener { e ->


                    }
            }
        }




    }
}