package com.app.alarm

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.GridLayout
import android.widget.Switch
import android.widget.TextView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.util.Calendar

class AlarmFragment : Fragment() {
    private  var requestCode = 0

    @SuppressLint("MissingInflatedId")
    private var button: FloatingActionButton? = null

    fun setButton(button: FloatingActionButton) {
        this.button = button
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.fragment_alarm, container, false)
        var gridLayout: GridLayout = view.findViewById(R.id.rectangleContainer)
        var i: Int

        button?.setOnClickListener {
            i = gridLayout.childCount
            if (i < 10) {
                addCardToGridLayout(gridLayout)
                requestCode++
            }
        }


        return view
    }
    fun calculateTimeDifference(hour: Int, minute: Int): Long {
        val currentTime = Calendar.getInstance()
        val targetTime = Calendar.getInstance()

        targetTime.set(Calendar.HOUR_OF_DAY, hour)
        targetTime.set(Calendar.MINUTE, minute)
        targetTime.set(Calendar.SECOND, 0)
        targetTime.set(Calendar.MILLISECOND, 0)
        if (targetTime.before(currentTime)) {
            targetTime.add(Calendar.DAY_OF_YEAR, 1)
        }
        return targetTime.timeInMillis - currentTime.timeInMillis
    }

    @SuppressLint("ScheduleExactAlarm")
    private fun setAlarm(timeInMillis: Long, requestCode: Int) {
        val alarmManager = requireContext().getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(requireContext(), AlarmReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(requireContext(), requestCode, intent, PendingIntent.FLAG_UPDATE_CURRENT)
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, timeInMillis, pendingIntent)
    }
    private fun cancelAlarm(requestCode: Int) {
        val alarmManager = requireContext().getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(requireContext(), AlarmReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(requireContext(), requestCode, intent, PendingIntent.FLAG_UPDATE_CURRENT)

        alarmManager.cancel(pendingIntent)
        pendingIntent.cancel()
    }

    @SuppressLint("MissingInflatedId")
    private fun addCardToGridLayout(gridLayout: GridLayout) {
        val cardView = LayoutInflater.from(requireContext()).inflate(R.layout.alarm_card, gridLayout, false)
        var time: TextView = cardView.findViewById(R.id.time)
        var timeTitle: TextView = cardView.findViewById(R.id.alarmTitle)
        var sun: TextView = cardView.findViewById(R.id.sun)
        var mon: TextView = cardView.findViewById(R.id.mon)
        var tue: TextView = cardView.findViewById(R.id.tue)
        var wed: TextView = cardView.findViewById(R.id.wed)
        var thu: TextView = cardView.findViewById(R.id.thu)
        var fri: TextView = cardView.findViewById(R.id.fri)
        var sat: TextView = cardView.findViewById(R.id.sat)
        var daysList = listOf(sun, mon, tue, wed, thu, fri, sat)
        var switch: Switch = cardView.findViewById(R.id.switchv)

        daysList.forEach { day->
            day.setOnClickListener {
                val currentColor = day.currentTextColor

                if (currentColor == Color.parseColor("#F0F757")) {
                    day.setTextColor(Color.parseColor("#CFCFCF"))
                } else {
                    day.setTextColor(Color.parseColor("#F0F757"))
                }
            }
        }
        val calendar = Calendar.getInstance()
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)
        val timePickerDialog = TimePickerDialog(
            requireContext(),
            { _, hourOfDay, minut ->
                var timeText = String.format("%02d:%02d", hourOfDay, minut)
                time.text = "$timeText AM"
                val difference = calculateTimeDifference(hourOfDay, minut)
                Log.d("AlarmFragment", "Time difference: $difference milliseconds")
                val timeInMillis = System.currentTimeMillis() + difference
                setAlarm(timeInMillis, requestCode)
                gridLayout.addView(cardView)
            }, hour, minute, true)
        timePickerDialog.show()
        time.setOnClickListener {
            TimePickerDialog(
                requireContext(),
                { _, hourOfDay, minute ->
                    var timeText = String.format("%02d:%02d", hourOfDay, minute)
                    time.text = "$timeText AM"
                },
                hour, minute,
                true
            ).show()
        }

        switch.setOnClickListener {
            if (switch.isChecked) {
                var houre = time.text.split(":")[0].toInt()
                var minutee = time.text.split(":")[1].split(" ")[0].toInt()
                val difference = calculateTimeDifference(houre, minutee)
                val timeInMillis = System.currentTimeMillis() + difference
                setAlarm(timeInMillis, requestCode)
            } else {
                cancelAlarm(requestCode)
            }
        }
    }





}