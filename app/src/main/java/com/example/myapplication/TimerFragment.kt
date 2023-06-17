package com.example.myapplication

import android.content.SharedPreferences
import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.myapplication.databinding.FragmentTimerBinding
import java.util.*

class TimerFragment : Fragment() {
    private lateinit var binding: FragmentTimerBinding
    private var timeLeftInMillis = 0L
    private var countDownTimer: CountDownTimer? = null
    private var timerIsRunning = false
    private var remainingTimeInMillis = 300000L
    private var endTime = 0L

    private lateinit var prefs: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTimerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        prefs = requireContext().getSharedPreferences("COUNT_DOWN_TIMER", MODE_PRIVATE)

        binding.startOrStopBtn.setOnClickListener {
            startOrStopTimer()
        }

        binding.resetBtn.setOnClickListener {
            stopOrResetTimer()
        }
    }

    override fun onStart() {
        super.onStart()
        timeLeftInMillis = prefs.getLong("millisecondsLeft", 0L)
        timerIsRunning = prefs.getBoolean("isTimerRunning", false)
        endTime = prefs.getLong("endTimeInMillis", 0L)

        if (endTime != 0L && timerIsRunning) {
            val timeSpentInBackground: Long = (System.currentTimeMillis() - endTime)
            remainingTimeInMillis = timeLeftInMillis - timeSpentInBackground
        } else {
            remainingTimeInMillis = timeLeftInMillis
        }
        binding.timerTV.text = remainingTimeInMillis.convertToTimeFormat()

        if (remainingTimeInMillis != 0L && timerIsRunning) {
            binding.startOrStopBtn.text = "Stop"
            countDownTimer = object : CountDownTimer(remainingTimeInMillis, 1000) {
                override fun onTick(millisUntilFinished: Long) {
                    remainingTimeInMillis = millisUntilFinished
                    timeLeftInMillis = millisUntilFinished
                    timerIsRunning = true

                    binding.timerTV.text = millisUntilFinished.convertToTimeFormat()
                }

                override fun onFinish() {
                    timerIsRunning = false
                    remainingTimeInMillis = 0L
                    timeLeftInMillis = 0L
                    binding.startOrStopBtn.text = "Start"
                }
            }.start()
        }
    }

    override fun onStop() {
        super.onStop()
        countDownTimer?.cancel()

        val editor = prefs.edit()
        editor.putLong("millisecondsLeft", timeLeftInMillis)
        editor.putBoolean("isTimerRunning", timerIsRunning)
        editor.putLong("endTimeInMillis", System.currentTimeMillis())
        editor.apply()
    }

    private fun startOrStopTimer() {
        if (!timerIsRunning) {
            countDownTimer?.cancel()
            binding.startOrStopBtn.text = "Stop"
            countDownTimer = object : CountDownTimer(remainingTimeInMillis, 1000) {
                override fun onTick(millisUntilFinished: Long) {
                    remainingTimeInMillis = millisUntilFinished
                    timeLeftInMillis = millisUntilFinished
                    timerIsRunning = true

                    binding.timerTV.text = millisUntilFinished.convertToTimeFormat()
                }

                override fun onFinish() {
                    timerIsRunning = false
                    remainingTimeInMillis = 0L
                    timeLeftInMillis = 0L
                    binding.startOrStopBtn.text = "Start"
                }
            }.start()
        } else {
            countDownTimer?.cancel()
            timerIsRunning = false
            binding.startOrStopBtn.text = "Start"
        }
    }

    private fun stopOrResetTimer() {
        countDownTimer?.cancel()
        timerIsRunning = false
        timeLeftInMillis = 300000
        remainingTimeInMillis = 300000
        binding.timerTV.text = remainingTimeInMillis.convertToTimeFormat()
        binding.startOrStopBtn.text = "Start"
    }

    private fun Long.convertToTimeFormat(): String {
        val minutes = (this / 1000).toInt() / 60
        val seconds = (this / 1000).toInt() % 60

        return String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds)
    }

    companion object {
        const val MODE_PRIVATE = 0x0000
    }
}
