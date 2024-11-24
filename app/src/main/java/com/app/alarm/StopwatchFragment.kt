package com.app.alarm

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast


class StopwatchFragment : Fragment() {

    private lateinit var stopwatchDisplay: TextView
    private lateinit var startButton: Button
    private lateinit var lapButton: Button

    private var isRunning = false
    private var elapsedTime: Long = 0
    private var startTime: Long = 0
    private val handler = Handler(Looper.getMainLooper())

    private val updateRunnable = object : Runnable {
        override fun run() {
            if (isRunning) {
                val currentTime = System.currentTimeMillis()
                elapsedTime = currentTime - startTime
                updateStopwatchDisplay(elapsedTime)
                handler.postDelayed(this, 10) // Update every 10 milliseconds
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_stopwatch, container, false)

        stopwatchDisplay = view.findViewById(R.id.stopwatchDisplay)
        startButton = view.findViewById(R.id.startButton)
        lapButton = view.findViewById(R.id.lapButton)

        // Start Button Click Listener
        startButton.setOnClickListener {
            if (isRunning) {
                // Stop the stopwatch
                isRunning = false
                handler.removeCallbacks(updateRunnable)
                startButton.text = "Start"
            } else {
                // Start the stopwatch
                isRunning = true
                startTime = System.currentTimeMillis() - elapsedTime
                handler.post(updateRunnable)
                startButton.text = "Stop"
            }
        }

        // Lap Button Click Listener
        lapButton.setOnClickListener {
            if (isRunning) {
                // Save and display the lap time
                val lapTime = formatTime(elapsedTime)
                Toast.makeText(requireContext(), "Lap: $lapTime", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(requireContext(), "Start the stopwatch first!", Toast.LENGTH_SHORT)
                    .show()
            }
        }

        return view
    }

    private fun updateStopwatchDisplay(timeInMillis: Long) {
        val formattedTime = formatTime(timeInMillis)
        stopwatchDisplay.text = formattedTime
    }

    private fun formatTime(timeInMillis: Long): String {
        val totalSeconds = timeInMillis / 1000
        val minutes = (totalSeconds / 60).toInt()
        val seconds = (totalSeconds % 60).toInt()
        val milliseconds = (timeInMillis % 1000).toInt()

        return String.format("%02d : %02d : %03d", minutes, seconds, milliseconds)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        handler.removeCallbacks(updateRunnable) // Stop updates when the view is destroyed
    }
}