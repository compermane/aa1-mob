package com.example.aa1_mob.repository.room.dao

import androidx.room.*
import com.example.aa1_mob.repository.room.models.Job
import com.example.aa1_mob.repository.room.models.JobWithUsers
import kotlinx.coroutines.flow.Flow

@Dao
interface JobDao {

    @Query("SELECT * FROM job")
    fun getAllJobs(): Flow<List<Job>>

    @Insert
    suspend fun insertJob(job: Job)

    @Delete
    suspend fun deleteJob(job: Job)

    @Query("DELETE FROM job")
    suspend fun deleteJobs()

    @Transaction
    @Query("SELECT * FROM job WHERE idJob = :jobId")
    suspend fun getJobById(jobId: Int): Job
}