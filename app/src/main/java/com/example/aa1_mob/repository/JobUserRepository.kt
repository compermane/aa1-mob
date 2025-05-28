package com.example.aa1_mob.repository

import android.util.Log
import com.example.aa1_mob.repository.room.dao.JobUserDao
import com.example.aa1_mob.repository.room.models.JobUser
import com.example.aa1_mob.repository.room.models.JobWithUsers
import com.example.aa1_mob.repository.room.models.UserWithJobs

class JobUserRepository(private val dao: JobUserDao) {
    suspend fun applyToJob(jobUser : JobUser) {
        Log.i("JobUserRepository", "Applying to job")
        dao.applyToJob(jobUser)
    }

    suspend fun getJobWithUsers(jobId : Int) : JobWithUsers {
        return dao.getJobWithUsers(jobId)
    }

    suspend fun getUserWithJobs(userId : Int) : UserWithJobs {
        return dao.getUserWithJobs(userId)
    }
}