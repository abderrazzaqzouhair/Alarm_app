package com.app.alarm

import android.os.Bundle
import android.os.CountDownTimer
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.NumberPicker
import android.widget.Toast

class TimerFragment : Fragment() {

    private lateinit var hoursPicker: NumberPicker
    private lateinit var minutesPicker: NumberPicker
    private lateinit var secondsPicker: NumberPicker
    private lateinit var startButton: Button

    private var countDownTimer: CountDownTimer? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_timer, container, false)
        hoursPicker = view.findViewById(R.id.hoursPicker)
        minutesPicker = view.findViewById(R.id.minutesPicker)
        secondsPicker = view.findViewById(R.id.secondsPicker)
        startButton = view.findViewById(R.id.startButton)

        hoursPicker.minValue = 0
        hoursPicker.maxValue = 23

        minutesPicker.minValue = 0
        minutesPicker.maxValue = 59

        secondsPicker.minValue = 0
        secondsPicker.maxValue = 59

        startButton.setOnClickListener {
            val hours = hoursPicker.value
            val minutes = minutesPicker.value
            val seconds = secondsPicker.value

            val totalTimeInMillis = (hours * 3600 + minutes * 60 + seconds) * 1000L

            if (totalTimeInMillis > 0) {
                startTimer(totalTimeInMillis)
            } else {
                Toast.makeText(requireContext(), "Please set a valid time.", Toast.LENGTH_SHORT)
                    .show()
            }
        }

        return view
    }

    private fun startTimer(totalTimeInMillis: Long) {
        countDownTimer?.cancel() // Cancel any previous timer

        countDownTimer = object : CountDownTimer(totalTimeInMillis, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val totalSeconds = millisUntilFinished / 1000
                val hours = (totalSeconds / 3600).toInt()
                val minutes = ((totalSeconds % 3600) / 60).toInt()
                val seconds = (totalSeconds % 60).toInt()

                // Update the NumberPickers dynamically
                hoursPicker.value = hours
                minutesPicker.value = minutes
                secondsPicker.value = seconds
            }

            override fun onFinish() {
                Toast.makeText(requireContext(), "Time's up!", Toast.LENGTH_SHORT).show()
            }
        }.start()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        countDownTimer?.cancel() // Cancel timer when the view is destroyed
    }
}