package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment

import com.example.myapplication.databinding.ActivityHomeBinding
import com.example.myapplication.fragments.HomeFragment
import com.example.myapplication.fragments.LocationFragment
import com.example.myapplication.fragments.ProfileFragment

class HomeActivity : AppCompatActivity() {

    private  lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        replaceFragment(HomeFragment())

        binding.bottomNavigation.setOnItemSelectedListener {

            when(it.itemId) {
                R.id.parking -> replaceFragment(HomeFragment())
                R.id.locate -> replaceFragment(LocationFragment())
                R.id.profile -> replaceFragment(ProfileFragment())


                else -> {

                }
            }
            true

        }




    }

    // function change the fragment
    private fun replaceFragment(fragment: Fragment)
    {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame_layout,fragment)
        fragmentTransaction.commit()
    }

}