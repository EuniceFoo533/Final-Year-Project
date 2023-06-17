package com.example.myapplication.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.example.myapplication.LoginActivity
import com.example.myapplication.R
import com.google.firebase.auth.FirebaseAuth

// TODO: Rename parameter arguments, choose names that match

class ProfileFragment : Fragment() {

    private lateinit var user: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val btnVehicle = getView()?.findViewById<Button>(R.id.buttonVehicle)
        val btnLocation = getView()?.findViewById<Button>(R.id.buttonLocation)
        val btnReset = getView()?.findViewById<Button>(R.id.buttonChangePassword)
        val btnTransaction = getView()?.findViewById<Button>(R.id.btnTransaction)
        val btnFAQ = getView()?.findViewById<Button>(R.id.buttonFAQ)
        val btnLogout = getView()?.findViewById<Button>(R.id.buttonLogout)

        user = FirebaseAuth.getInstance()

        btnVehicle?.setOnClickListener {
            val transaction = activity?.supportFragmentManager?.beginTransaction()
            transaction?.replace(R.id.frame_layout,VehicleFragment())
            transaction?.disallowAddToBackStack()
            transaction?.commit()
        }

        btnLocation?.setOnClickListener {
            val transaction = activity?.supportFragmentManager?.beginTransaction()
            transaction?.replace(R.id.frame_layout,SavedLocationFragment())
            transaction?.disallowAddToBackStack()
            transaction?.commit()
        }

        btnReset?.setOnClickListener {
            val transaction = activity?.supportFragmentManager?.beginTransaction()
            transaction?.replace(R.id.frame_layout,ResetPasswordFragment())
            transaction?.disallowAddToBackStack()
            transaction?.commit()
        }

        btnTransaction?.setOnClickListener {
            val transaction = activity?.supportFragmentManager?.beginTransaction()
            transaction?.replace(R.id.frame_layout,TransactionFragment())
            transaction?.disallowAddToBackStack()
            transaction?.commit()
        }

        btnFAQ?.setOnClickListener {
            val transaction = activity?.supportFragmentManager?.beginTransaction()
            transaction?.replace(R.id.frame_layout,FAQFragment())
            transaction?.disallowAddToBackStack()
            transaction?.commit()
        }

        btnLogout?.setOnClickListener {
            user.signOut()
            startActivity(Intent(context,LoginActivity::class.java))
            activity?.finish()
        }



    }
}