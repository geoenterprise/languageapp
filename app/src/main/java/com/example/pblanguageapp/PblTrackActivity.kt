package com.example.pblanguageapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class PblTrackActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pbl_track)

        val topicOne = findViewById<Button>(R.id.topicOneButton)
        val topicTwo = findViewById<Button>(R.id.topicTwoButton)

        topicOne.setOnClickListener {
            val intent = Intent(this, MyCommunityProjectActivity::class.java)
            startActivity(intent)
        }

        topicTwo.setOnClickListener {
            val intent = Intent(this, EnvironmentProjectActivity::class.java)
            startActivity(intent)
        }
    }
}
