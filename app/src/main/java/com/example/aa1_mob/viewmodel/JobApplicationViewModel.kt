package com.example.aa1_mob.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.aa1_mob.repository.JobUserRepository
import com.example.aa1_mob.repository.getLoggedUserId
import com.example.aa1_mob.repository.room.dao.JobUserDao
import com.example.aa1_mob.repository.room.models.Job
import com.example.aa1_mob.repository.room.models.JobUser
import com.example.aa1_mob.repository.room.models.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class JobApplicationViewModel(
    private val repository: JobUserRepository,
    private val app: Application
) : AndroidViewModel(app) {

    val loggedUserId : Flow<Int?> = app.getLoggedUserId()

    fun applyToJob(jobId: Int) {
        viewModelScope.launch {
            loggedUserId.collect { userId ->
                if (userId != null) {
                    Log.i("JobApplicationViewModel", "Applying to job on user id ${userId} and job id ${jobId}")
                    repository.applyToJob(JobUser(jobId, userId))
                }
            }
        }
    }

    suspend fun getJobsForUser(userId: Int): List<Job> {
        return repository.getUserWithJobs(userId).jobs
    }

    suspend fun getUsersForJob(jobId: Int): List<User> {
        return repository.getJobWithUsers(jobId).users
    }
}
