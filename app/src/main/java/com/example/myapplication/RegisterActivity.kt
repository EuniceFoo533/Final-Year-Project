package com.example.myapplication

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import com.example.myapplication.databinding.ActivityRegisterBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.auth.User
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class RegisterActivity : AppCompatActivity() {

    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var binding: ActivityRegisterBinding
    private lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth =FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()

        binding.textViewLogin.setOnClickListener {
            val intent = Intent(this,LoginActivity::class.java)
            startActivity(intent)
        }

        binding.buttonSignUp.setOnClickListener {
            val name = binding.editTextName.text.toString()
            val email = binding.editTextEmail.text.toString()
            val password = binding.editTextPassword.text.toString()

            var currentUser = firebaseAuth.currentUser



            if(email.isNotEmpty() && password.isNotEmpty())
            {
                firebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener{
                    if (it.isSuccessful)
                    {
                        val user = Users(currentUser!!.uid,name,email,password)

                        // Add a new document with a generated ID
                        db.collection("users")
                            .add(user)
                            .addOnSuccessListener { documentReference ->
                                Toast.makeText(this,"Registered successfully!",Toast.LENGTH_SHORT).show()
                            }
                            .addOnFailureListener { e ->
                                Toast.makeText(this,"Failed to Register!",Toast.LENGTH_SHORT).show()

                            }

                        val intent = Intent(this,LoginActivity::class.java)
                        startActivity(intent)
                    }
                    else
                    {
                        Toast.makeText(this,it.exception.toString(),Toast.LENGTH_LONG).show()
                    }
                }
            }

            else if(email.isEmpty() || password.isEmpty())
            {
                Toast.makeText(this,"Please fill in the blank",Toast.LENGTH_LONG).show()
            }


            else if (password.length< 8)
            {
                Toast.makeText(this,"Password should be at least 8 characters",Toast.LENGTH_LONG).show()
            }

            else
            {
                Toast.makeText(this,"Please fill in the blank",Toast.LENGTH_LONG).show()
            }

        }
    }
}