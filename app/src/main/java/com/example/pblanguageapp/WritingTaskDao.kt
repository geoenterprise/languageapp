package com.example.pblanguageapp

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface WritingTaskDao {
    @Insert
    fun insert(task: WritingTask)

    @Query("SELECT * FROM WritingTask")
    fun getAll(): List<WritingTask>
}
