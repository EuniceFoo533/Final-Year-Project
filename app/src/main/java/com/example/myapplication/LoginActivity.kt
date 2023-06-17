package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.myapplication.databinding.ActivityLoginBinding
import com.example.myapplication.fragments.ResetPasswordFragment
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {


    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()

        binding.textViewRegister.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

        binding.tvReset.setOnClickListener{
            val intent = Intent(this, ResetPasswordFragment::class.java)
            startActivity(intent)
        }

        binding.buttonLogin.setOnClickListener {

            val email = binding.edtEmailAddress.text.toString().trim()
            val password = binding.edtPassword.text.toString().trim()

            firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        val verification = firebaseAuth.currentUser?.isEmailVerified

                        if (verification == true) {
                            val user = firebaseAuth.currentUser
                            val intent = Intent(this, HomeActivity::class.java)
                            startActivity(intent)
                        } else {
                            Toast.makeText(this, "Please Verify Your Email ", Toast.LENGTH_SHORT)
                                .show()
                        }

                    } else {
                        Toast.makeText(baseContext, "Authentication Failed", Toast.LENGTH_SHORT)
                            .show()
                    }

                }


        }
    }
}