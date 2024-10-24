package com.example.casino.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.ComponentActivity
import com.example.casino.R

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun toGames(v: View) {
        val intent = Intent(this, Games::class.java)
        startActivity(intent)
    }

    fun toSettings(v: View) {
        val intent = Intent(this, Settings::class.java)
        startActivity(intent)
    }
}