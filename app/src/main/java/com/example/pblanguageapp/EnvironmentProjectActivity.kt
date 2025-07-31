package com.example.pblanguageapp

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import kotlin.concurrent.thread

class EnvironmentProjectActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_environment_project)

        val db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "my-app-db"
        ).build()


        val writingInput = findViewById<EditText>(R.id.writingInput)
        val submitButton = findViewById<Button>(R.id.submitButton)

        submitButton.setOnClickListener {
            val userText = writingInput.text.toString()
            if (userText.isNotBlank()) {
                thread {
                    db.writingTaskDao().insert(
                        WritingTask(topic = "The Environment", answer = userText)
                    )
                }
                Toast.makeText(this, "Response saved!", Toast.LENGTH_SHORT).show()
                writingInput.text.clear()
            } else {
                Toast.makeText(this, "Please write something before submitting.", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
