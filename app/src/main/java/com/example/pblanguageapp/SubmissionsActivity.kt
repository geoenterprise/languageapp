package com.example.pblanguageapp

import android.os.Bundle
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import kotlin.concurrent.thread

class SubmissionsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_submissions)

        val container = findViewById<LinearLayout>(R.id.submissionsContainer)

        val db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "my-app-db"
        ).build()

        thread {
            val submissions = db.writingTaskDao().getAll()

            runOnUiThread {
                if (submissions.isEmpty()) {
                    val noDataText = TextView(this)
                    noDataText.text = "No submissions yet."
                    noDataText.textSize = 18f
                    container.addView(noDataText)
                } else {
                    submissions.forEach { task ->
                        val taskView = TextView(this)
                        taskView.text = "📝 ${task.topic}\n➡️ ${task.answer}"
                        taskView.textSize = 16f
                        taskView.setPadding(0, 0, 0, 24)
                        container.addView(taskView)
                    }
                }
            }
        }
    }
}
