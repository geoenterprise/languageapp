package com.example.pblanguageapp

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [WritingTask::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun writingTaskDao(): WritingTaskDao
}
