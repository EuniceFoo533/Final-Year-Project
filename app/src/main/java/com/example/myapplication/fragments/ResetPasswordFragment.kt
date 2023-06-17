package com.example.myapplication.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.myapplication.R
import com.google.firebase.auth.FirebaseAuth


class ResetPasswordFragment : Fragment() {
private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_reset_password, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        firebaseAuth = FirebaseAuth.getInstance()
        val btnReset = getView()?.findViewById<Button>(R.id.buttonResetPassword)
        val emailAddress = getView()?.findViewById<EditText>(R.id.edtEmailAddress)

        val email = emailAddress?.text.toString()

        btnReset?.setOnClickListener{
            firebaseAuth.sendPasswordResetEmail(email)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        // Password reset email sent successfully
                        // You can show a confirmation message or take further actions
                        Toast.makeText(context,"Reset Link Sent! Please Check Your Email.",Toast.LENGTH_SHORT).show()

                    } else {
                        // Password reset email sending failed
                        // You can display an error message or handle the error
                        Toast.makeText(context,"Error",Toast.LENGTH_SHORT).show()

                    }
                }
        }





    }
}