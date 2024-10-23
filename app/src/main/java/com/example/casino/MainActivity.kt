package com.example.casino

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.ComponentActivity

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
