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
import com.example.myapplication.adapter.TransactAdapter
import com.example.myapplication.item.Transact
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore


class TransactionFragment : Fragment() {

    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var db: FirebaseFirestore
    private lateinit var transactRef: CollectionReference

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_transaction, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        db = FirebaseFirestore.getInstance()
        transactRef = db.collection("transaction")
        firebaseAuth = FirebaseAuth.getInstance()

        var currentUser = firebaseAuth.currentUser


        transactRef
            .whereEqualTo("userID",currentUser!!.uid)
            .get().addOnSuccessListener { documents ->
                if(documents != null && !documents.isEmpty)
                {
                    val itemList = mutableListOf<Transact>()
                    for (document in documents)
                    {
                        val id = document.id
                        val type = document.getString("transactType")
                        val tDate = document.getString("transactDate")
                        val amount = document.getString("transactAmount")!!


                        if(type!= null)
                        {
                            val item = Transact(id,type, amount,tDate.toString())
                            itemList.add(item)
                        }

                        val recyclerView =
                            getView()?.findViewById<RecyclerView>(R.id.recyclerview) // Replace "recyclerView" with your actual RecyclerView ID
                        val adapter = TransactAdapter(itemList)
                        recyclerView?.adapter = adapter
                        recyclerView?.layoutManager = LinearLayoutManager(requireContext())



                    }
                }
            }.addOnFailureListener{exception ->
                Log.e("Firestore", "Error getting documents: ", exception)
            }
    }
}