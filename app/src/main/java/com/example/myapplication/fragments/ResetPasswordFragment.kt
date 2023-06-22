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
import com.example.myapplication.databinding.FragmentResetPasswordBinding
import com.google.firebase.auth.FirebaseAuth


class ResetPasswordFragment : Fragment() {

    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var binding : FragmentResetPasswordBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentResetPasswordBinding.inflate(
            inflater,
            container,
            false
        )


        return binding.root
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val btnReset = getView()?.findViewById<Button>(R.id.buttonReset)

            btnReset?.setOnClickListener {
                firebaseAuth = FirebaseAuth.getInstance()
                var email = binding.editEmail.text.toString()


                if (email.isEmpty()) {
                    Toast.makeText(context,"Please Enter Your Email Address.",Toast.LENGTH_SHORT).show()
                }

                else if(email.isNotEmpty()){
                    firebaseAuth.sendPasswordResetEmail(email)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                // Password reset email sent successfully
                                Toast.makeText(context,"Password Reset Email Sent To $email",Toast.LENGTH_SHORT).show()
                            } else {
                                // Failed to send password reset email
                                Toast.makeText(context,"Failed To Send Password Reset Email: ${task.exception?.message}",Toast.LENGTH_SHORT).show()
                            }
                        }
                }


            }
    }
}