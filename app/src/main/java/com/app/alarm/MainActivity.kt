package com.app.alarm

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.FrameLayout
import android.widget.GridLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        var title: TextView = findViewById(R.id.title)
        var alarm: ImageView = findViewById(R.id.alarm)
        var clock: ImageView = findViewById(R.id.clock)
        var stopwatch: ImageView = findViewById(R.id.stopwatch)
        var timer: ImageView = findViewById(R.id.timer)
        val fragment = AlarmFragment()
        fragment.setButton(findViewById(R.id.addAlarm))
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
        alarm.setOnClickListener {

            title.text = "Alarm"
            alarm.setImageResource(R.drawable.alarmyellow)
            clock.setImageResource(R.drawable.clock)
            stopwatch.setImageResource(R.drawable.stopwatch)
            timer.setImageResource(R.drawable.hourglass)
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit()
        }
        clock.setOnClickListener {
            title.text = "Clock"
            alarm.setImageResource(R.drawable.alarmclock)
            clock.setImageResource(R.drawable.clockyellow)
            stopwatch.setImageResource(R.drawable.stopwatch)
            timer.setImageResource(R.drawable.hourglass)
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, ClockFragment())
                .commit()
        }
        stopwatch.setOnClickListener {
            title.text = "Stopwatch"
            alarm.setImageResource(R.drawable.alarmclock)
            clock.setImageResource(R.drawable.clock)
            stopwatch.setImageResource(R.drawable.stopwatchyellow)
            timer.setImageResource(R.drawable.hourglass)
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, StopwatchFragment())
                .commit()


        }
        timer.setOnClickListener{
            title.text = "Timer"
            alarm.setImageResource(R.drawable.alarmclock)
            clock.setImageResource(R.drawable.clock)
            stopwatch.setImageResource(R.drawable.stopwatch)
            timer.setImageResource(R.drawable.hourglassyellow)
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, TimerFragment())
                .commit()

        }
    }



}