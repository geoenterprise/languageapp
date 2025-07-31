package com.example.pblanguageapp

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class WritingTask(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val topic: String,
    val answer: String
)
