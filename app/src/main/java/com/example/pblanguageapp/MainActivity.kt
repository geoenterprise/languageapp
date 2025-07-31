package com.example.pblanguageapp

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button
import android.widget.TextView
import android.content.Intent
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val titleText = findViewById<TextView>(R.id.titleText)
        val vocabButton = findViewById<Button>(R.id.vocabButton)
        val pblButton = findViewById<Button>(R.id.pblButton)

        pblButton.setOnClickListener {
            val intent = Intent(this, PblTrackActivity::class.java)
            startActivity(intent)
        }

        vocabButton.setOnClickListener {
            val intent = Intent(this, BeginnersVocabActivity::class.java)
            startActivity(intent)
        }
//        startButton.setOnClickListener {
//            Toast.makeText(this, "PBL tracking coming soon", Toast.LENGTH_SHORT).show()
//        }
        val submissionsButton = findViewById<Button>(R.id.submissionsButton)
        submissionsButton.setOnClickListener {
            val intent = Intent(this, SubmissionsActivity::class.java)
            startActivity(intent)
        }
    }
}