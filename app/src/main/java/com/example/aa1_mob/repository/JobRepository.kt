package com.example.aa1_mob.repository

import com.example.aa1_mob.repository.room.dao.JobDao
import com.example.aa1_mob.repository.room.models.Job
import kotlinx.coroutines.flow.Flow

class JobRepository(private val dao: JobDao) {
    val allJobs: Flow<List<Job>> = dao.getAllJobs()

    suspend fun insert(job: Job) {
        dao.insertJob(job)
    }

    suspend fun findById(id : Int): Job {
        return dao.getJobById(id)
    }

    suspend fun clearJobs() {
        dao.deleteJobs()
    }

    fun searchJobByName(name: String): Flow<List<Job>> {
        return dao.searchJobByName(name)
    }
}