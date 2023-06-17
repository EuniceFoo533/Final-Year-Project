package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import kotlin.math.sign

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var loginActivity = findViewById<Button>(R.id.buttonLogin)
        var signUpActivity = findViewById<Button>(R.id.buttonRegister)
        var adminLogin = findViewById<TextView>(R.id.tvAdmin)

        loginActivity.setOnClickListener{
            val intent = Intent(this,LoginActivity::class.java)
            startActivity(intent)
        }

        signUpActivity.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

        adminLogin.setOnClickListener{
            val intent = Intent(this, AdminLoginActivity::class.java)
            startActivity(intent)
        }

    }
}