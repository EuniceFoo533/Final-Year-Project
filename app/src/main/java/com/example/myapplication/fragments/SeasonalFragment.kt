package com.example.myapplication.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import com.example.myapplication.R

// TODO: Rename parameter arguments, choose names that match


class SeasonalFragment : Fragment() {



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_seasonal, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val btnHourly = getView()?.findViewById<Button>(R.id.btnHourly)
        btnHourly?.setOnClickListener {
            Toast.makeText(context,"Hourly Pass is Selected", Toast.LENGTH_SHORT).show()

            val transaction = activity?.supportFragmentManager?.beginTransaction()
            transaction?.replace(R.id.frame_layout,HomeFragment())
            transaction?.disallowAddToBackStack()
            transaction?.commit()
        }

        val btnMonthly = getView()?.findViewById<Button>(R.id.btnMontly)
        btnMonthly?.setOnClickListener {
            Toast.makeText(context,"Seasonal Pass is Selected", Toast.LENGTH_SHORT).show()
        }

        val btnReload = getView()?.findViewById<Button>(R.id.buttonReload)
        btnReload?.setOnClickListener {
            val transaction = activity?.supportFragmentManager?.beginTransaction()
            transaction?.replace(R.id.frame_layout,ReloadFragment())
            transaction?.disallowAddToBackStack()
            transaction?.commit()
        }

        val btn1 = getView()?.findViewById<Button>(R.id.btn1)
        val btn2 = getView()?.findViewById<Button>(R.id.btn2)
        val btn3 = getView()?.findViewById<Button>(R.id.btn3)
        val btn4 = getView()?.findViewById<Button>(R.id.btn4)
        val btn5 = getView()?.findViewById<Button>(R.id.btn5)
        val btn6 = getView()?.findViewById<Button>(R.id.btn6)
        val btn7 = getView()?.findViewById<Button>(R.id.btn7)


        btn1?.setOnClickListener {
            btn1?.setBackgroundColor(resources.getColor(R.color.purple))
            btn1.setTextColor(resources.getColor(R.color.white))

            btn2?.setBackgroundColor(resources.getColor(R.color.grey))
            btn2?.setTextColor(resources.getColor(R.color.blue))

            btn3?.setBackgroundColor(resources.getColor(R.color.grey))
            btn3?.setTextColor(resources.getColor(R.color.blue))

            btn4?.setBackgroundColor(resources.getColor(R.color.grey))
            btn4?.setTextColor(resources.getColor(R.color.blue))

            btn5?.setBackgroundColor(resources.getColor(R.color.grey))
            btn5?.setTextColor(resources.getColor(R.color.blue))

            btn6?.setBackgroundColor(resources.getColor(R.color.grey))
            btn6?.setTextColor(resources.getColor(R.color.blue))

            btn7?.setBackgroundColor(resources.getColor(R.color.grey))
            btn7?.setTextColor(resources.getColor(R.color.blue))


        }

        btn2?.setOnClickListener {
            btn2?.setBackgroundColor(resources.getColor(R.color.purple))
            btn2.setTextColor(resources.getColor(R.color.white))

            btn1?.setBackgroundColor(resources.getColor(R.color.grey))
            btn1?.setTextColor(resources.getColor(R.color.blue))

            btn3?.setBackgroundColor(resources.getColor(R.color.grey))
            btn3?.setTextColor(resources.getColor(R.color.blue))

            btn4?.setBackgroundColor(resources.getColor(R.color.grey))
            btn4?.setTextColor(resources.getColor(R.color.blue))

            btn5?.setBackgroundColor(resources.getColor(R.color.grey))
            btn5?.setTextColor(resources.getColor(R.color.blue))

            btn6?.setBackgroundColor(resources.getColor(R.color.grey))
            btn6?.setTextColor(resources.getColor(R.color.blue))

            btn7?.setBackgroundColor(resources.getColor(R.color.grey))
            btn7?.setTextColor(resources.getColor(R.color.blue))


        }

        btn3?.setOnClickListener {
            btn3?.setBackgroundColor(resources.getColor(R.color.purple))
            btn3.setTextColor(resources.getColor(R.color.white))

            btn1?.setBackgroundColor(resources.getColor(R.color.grey))
            btn1?.setTextColor(resources.getColor(R.color.blue))

            btn2?.setBackgroundColor(resources.getColor(R.color.grey))
            btn2?.setTextColor(resources.getColor(R.color.blue))

            btn4?.setBackgroundColor(resources.getColor(R.color.grey))
            btn4?.setTextColor(resources.getColor(R.color.blue))

            btn5?.setBackgroundColor(resources.getColor(R.color.grey))
            btn5?.setTextColor(resources.getColor(R.color.blue))

            btn6?.setBackgroundColor(resources.getColor(R.color.grey))
            btn6?.setTextColor(resources.getColor(R.color.blue))

            btn7?.setBackgroundColor(resources.getColor(R.color.grey))
            btn7?.setTextColor(resources.getColor(R.color.blue))

        }

        btn4?.setOnClickListener {
            btn4?.setBackgroundColor(resources.getColor(R.color.purple))
            btn4.setTextColor(resources.getColor(R.color.white))

            btn1?.setBackgroundColor(resources.getColor(R.color.grey))
            btn1?.setTextColor(resources.getColor(R.color.blue))

            btn3?.setBackgroundColor(resources.getColor(R.color.grey))
            btn3?.setTextColor(resources.getColor(R.color.blue))

            btn2?.setBackgroundColor(resources.getColor(R.color.grey))
            btn2?.setTextColor(resources.getColor(R.color.blue))

            btn5?.setBackgroundColor(resources.getColor(R.color.grey))
            btn5?.setTextColor(resources.getColor(R.color.blue))

            btn6?.setBackgroundColor(resources.getColor(R.color.grey))
            btn6?.setTextColor(resources.getColor(R.color.blue))

            btn7?.setBackgroundColor(resources.getColor(R.color.grey))
            btn7?.setTextColor(resources.getColor(R.color.blue))


        }

        btn5?.setOnClickListener {
            btn5?.setBackgroundColor(resources.getColor(R.color.purple))
            btn5.setTextColor(resources.getColor(R.color.white))

            btn1?.setBackgroundColor(resources.getColor(R.color.grey))
            btn1?.setTextColor(resources.getColor(R.color.blue))

            btn3?.setBackgroundColor(resources.getColor(R.color.grey))
            btn3?.setTextColor(resources.getColor(R.color.blue))

            btn4?.setBackgroundColor(resources.getColor(R.color.grey))
            btn4?.setTextColor(resources.getColor(R.color.blue))

            btn2?.setBackgroundColor(resources.getColor(R.color.grey))
            btn2?.setTextColor(resources.getColor(R.color.blue))

            btn6?.setBackgroundColor(resources.getColor(R.color.grey))
            btn6?.setTextColor(resources.getColor(R.color.blue))

            btn7?.setBackgroundColor(resources.getColor(R.color.grey))
            btn7?.setTextColor(resources.getColor(R.color.blue))

        }

        btn6?.setOnClickListener {
            btn6?.setBackgroundColor(resources.getColor(R.color.purple))
            btn6.setTextColor(resources.getColor(R.color.white))

            btn1?.setBackgroundColor(resources.getColor(R.color.grey))
            btn1?.setTextColor(resources.getColor(R.color.blue))

            btn3?.setBackgroundColor(resources.getColor(R.color.grey))
            btn3?.setTextColor(resources.getColor(R.color.blue))

            btn4?.setBackgroundColor(resources.getColor(R.color.grey))
            btn4?.setTextColor(resources.getColor(R.color.blue))

            btn5?.setBackgroundColor(resources.getColor(R.color.grey))
            btn5?.setTextColor(resources.getColor(R.color.blue))

            btn2?.setBackgroundColor(resources.getColor(R.color.grey))
            btn2?.setTextColor(resources.getColor(R.color.blue))

            btn7?.setBackgroundColor(resources.getColor(R.color.grey))
            btn7?.setTextColor(resources.getColor(R.color.blue))

        }

        btn7?.setOnClickListener {
            btn7?.setBackgroundColor(resources.getColor(R.color.purple))
            btn7.setTextColor(resources.getColor(R.color.white))

            btn1?.setBackgroundColor(resources.getColor(R.color.grey))
            btn1?.setTextColor(resources.getColor(R.color.blue))

            btn3?.setBackgroundColor(resources.getColor(R.color.grey))
            btn3?.setTextColor(resources.getColor(R.color.blue))

            btn4?.setBackgroundColor(resources.getColor(R.color.grey))
            btn4?.setTextColor(resources.getColor(R.color.blue))

            btn5?.setBackgroundColor(resources.getColor(R.color.grey))
            btn5?.setTextColor(resources.getColor(R.color.blue))

            btn6?.setBackgroundColor(resources.getColor(R.color.grey))
            btn6?.setTextColor(resources.getColor(R.color.blue))

            btn2?.setBackgroundColor(resources.getColor(R.color.grey))
            btn2?.setTextColor(resources.getColor(R.color.blue))


        }
    }
}