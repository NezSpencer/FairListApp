package com.nezspencer.fairlistapp.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.nezspencer.fairlistapp.data.Job

@Dao
interface JobDao {
    @Query("SELECT * FROM Job")
    fun getJobs(): List<Job>

    @Query("SELECT * FROM Job WHERE id = :userId LIMIT 1")
    fun getJob(userId: String): Job

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveJobs(jobs: List<Job>)

}