package com.example.pblanguageapp

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import kotlin.concurrent.thread
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import org.json.JSONObject
import java.io.IOException
import androidx.appcompat.app.AlertDialog
import android.Manifest
import android.content.pm.PackageManager
import android.media.MediaRecorder
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import android.media.MediaPlayer

private var mediaPlayer: MediaPlayer? = null


class MyCommunityProjectActivity : AppCompatActivity() {
    private var mediaRecorder: MediaRecorder? = null
    private var isRecording = false
    private lateinit var audioFilePath: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // ✅ Load your layout first
        setContentView(R.layout.activity_my_community_project)

        // ✅ Create database *after* layout is loaded
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
                        WritingTask(topic = "My Community", answer = userText)
                    )
                }
                Toast.makeText(this, "Response saved!", Toast.LENGTH_SHORT).show()

                // ✅ NEW: Call AI for feedback
                val pblTopic = "My Community"
                val pblPrompt = "What is one idea you would propose to improve your community?"

                val prompt = """
                You are helping an English learner complete a Project-Based Learning (PBL) writing task.
                
                Topic: $pblTopic
                Prompt: $pblPrompt
                
                Here is the student's writing:
                "$userText"
                
                Please provide friendly, constructive grammar and vocabulary feedback, and suggest a corrected version if helpful.
                """.trimIndent()

                val loadingDialog = AlertDialog.Builder(this)
                    .setTitle("Analyzing your writing...")
                    .setMessage("Please wait while AI gives you feedback.")
                    .setCancelable(false)
                    .create()
                loadingDialog.show()

                callOpenAI(prompt, BuildConfig.OPENAI_API_KEY) { aiResponse ->
                    runOnUiThread {
                        loadingDialog.dismiss()

                        if (aiResponse != null) {
                            androidx.appcompat.app.AlertDialog.Builder(this)
                                .setTitle("AI Feedback")
                                .setMessage(aiResponse)
                                .setPositiveButton("OK") { dialog, _ -> dialog.dismiss() }
                                .show()
                        } else {
                            Toast.makeText(this, "AI did not respond.", Toast.LENGTH_SHORT).show()
                        }
                    }
                }

                writingInput.text.clear()
            } else {
                Toast.makeText(this, "Please write something before submitting.", Toast.LENGTH_SHORT).show()
            }
        }
        val recordButton = findViewById<Button>(R.id.recordButton)
        audioFilePath = "${externalCacheDir?.absolutePath}/community_audio.3gp"

        recordButton.setOnClickListener {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO)
                != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.RECORD_AUDIO), 200)
            } else {
                if (!isRecording) {
                    startRecording()
                    recordButton.text = "⏹️ Stop Recording"
                } else {
                    stopRecording()
                    recordButton.text = "🎙️ Record Voice"
                    Toast.makeText(this, "Audio saved!", Toast.LENGTH_SHORT).show()
                }
            }
        }

        val playButton = findViewById<Button>(R.id.playButton)
        playButton.setOnClickListener {
            if (mediaPlayer == null) {
                mediaPlayer = MediaPlayer().apply {
                    setDataSource(audioFilePath)
                    prepare()
                    start()
                }
                Toast.makeText(this, "Playing recording...", Toast.LENGTH_SHORT).show()
            } else {
                if (mediaPlayer!!.isPlaying) {
                    mediaPlayer!!.stop()
                    mediaPlayer!!.release()
                    mediaPlayer = null
                    Toast.makeText(this, "Playback stopped.", Toast.LENGTH_SHORT).show()
                }
            }
        }


    }
    private fun startRecording() {
        mediaRecorder = MediaRecorder().apply {
            setAudioSource(MediaRecorder.AudioSource.MIC)
            setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP)
            setOutputFile(audioFilePath)
            setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB)
            prepare()
            start()
        }
        isRecording = true
    }

    private fun stopRecording() {
        mediaRecorder?.apply {
            stop()
            release()
        }
        mediaRecorder = null
        isRecording = false
    }
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 200 && grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "Microphone permission granted", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Microphone permission denied", Toast.LENGTH_SHORT).show()
        }
    }
    override fun onDestroy() {
        mediaPlayer?.release()
        mediaPlayer = null
        super.onDestroy()
    }

}

fun callOpenAI(prompt: String, apiKey: String, onResult: (String?) -> Unit) {
    val client = OkHttpClient()

    val json = JSONObject()
    json.put("model", "gpt-3.5-turbo")
    json.put("messages", listOf(
        JSONObject().put("role", "system").put("content", "You are a helpful English writing assistant."),
        JSONObject().put("role", "user").put("content", prompt)
    ))

    val mediaType = "application/json; charset=utf-8".toMediaType()
    val body = RequestBody.create(mediaType, json.toString())

    val request = Request.Builder()
        .url("https://api.openai.com/v1/chat/completions")
        .addHeader("Authorization", "Bearer $apiKey") // ✅ use the passed-in key
        .post(body)
        .build()

    client.newCall(request).enqueue(object : Callback {
        override fun onFailure(call: Call, e: IOException) {
            onResult(null)
        }

        override fun onResponse(call: Call, response: Response) {
            response.body?.string()?.let { responseBody ->
                val json = JSONObject(responseBody)
                val message = json
                    .getJSONArray("choices")
                    .getJSONObject(0)
                    .getJSONObject("message")
                    .getString("content")
                onResult(message.trim())
            }
        }
    })
}



