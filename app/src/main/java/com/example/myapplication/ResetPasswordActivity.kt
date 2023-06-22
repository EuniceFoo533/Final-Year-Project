package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.myapplication.databinding.ActivityResetPasswordBinding
import com.google.firebase.auth.FirebaseAuth

class ResetPasswordActivity : AppCompatActivity() {

    private lateinit var binding: ActivityResetPasswordBinding
    private lateinit var firebaseAuth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        binding = ActivityResetPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)



        binding.buttonReset.setOnClickListener{

            val email = binding.editEmail.text.toString()
            firebaseAuth = FirebaseAuth.getInstance()
            if (email.isNotEmpty())
            {
                firebaseAuth.sendPasswordResetEmail(email)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            // Password reset email sent successfully
                            Toast.makeText(this,"Password Reset Email Sent To $email",Toast.LENGTH_SHORT).show()
                        } else {
                            // Failed to send password reset email
                            Toast.makeText(this,"Failed To Send Password Reset Email: ${task.exception?.message}",Toast.LENGTH_SHORT).show()
                        }
                    }
            }
            else
            {
                Toast.makeText(this,"Please Enter Email To Reset Password",Toast.LENGTH_SHORT).show()
            }
        }




    }
}