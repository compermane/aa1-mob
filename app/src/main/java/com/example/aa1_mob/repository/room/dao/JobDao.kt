package com.example.aa1_mob.repository.room.dao

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface JobDao {

    @Query("SELECT * FROM job_table")
    fun getAllJobs(): Flow<List<Job>>

    @Insert
    suspend fun insertJob(job: Job)

    @Delete
    suspend fun deleteJob(job: Job)

    @Transaction
    @Query("SELECT * FROM job_table WHERE id = :jobId")
    fun getJobWithUsers(jobId: Int): Flow<JobWithUsers>
}