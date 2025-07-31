package com.example.pblanguageapp

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class BeginnersVocabActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_beginners_vocab)

        val vocabListView = findViewById<ListView>(R.id.vocabListView)

        // Sample vocabulary list
        val vocabMap = mapOf(
            "Apple - Manzana" to "I eat an apple every day.",
            "Water - Agua" to "She drinks a glass of water.",
            "Dog - Perro" to "The dog is barking.",
            "House - Casa" to "We live in a blue house.",
            "Book - Libro" to "He reads a book at night.",
            "Teacher - Profesor/a" to "The teacher is friendly.",
            "School - Escuela" to "My school is big and modern.",
            "Food - Comida" to "Mexican food is delicious.",
            "Sun - Sol" to "The sun is shining.",
            "Moon - Luna" to "The moon looks full tonight."
        )
        val vocabItems = vocabMap.keys.toList()
        // Adapter connects list to ListView
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, vocabItems)
        vocabListView.adapter = adapter

        vocabListView.setOnItemClickListener { parent, view, position, id ->
            val selectedWord = vocabItems[position]
            val sentence = vocabMap[selectedWord]
            Toast.makeText(this, sentence, Toast.LENGTH_SHORT).show()
        }
    }
}
