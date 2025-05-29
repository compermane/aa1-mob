package com.example.aa1_mob.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.aa1_mob.repository.JobUserRepository
import com.example.aa1_mob.repository.getLoggedUserId
import com.example.aa1_mob.repository.room.models.Job
import com.example.aa1_mob.repository.room.models.JobUser
import com.example.aa1_mob.repository.room.models.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.firstOrNull

class JobApplicationViewModel(
    private val repository: JobUserRepository,
    private val app: Application
) : AndroidViewModel(app) {

    val loggedUserId : Flow<Int?> = app.getLoggedUserId()

    fun applyToJob(jobId: Int) {
        viewModelScope.launch {
            // Use firstOrNull() para garantir que o userId seja obtido antes de prosseguir
            val userId = loggedUserId.firstOrNull()
            if (userId != null) {
                Log.d("JobAppViewModel", "Attempting to apply: UserId=$userId, JobId=$jobId")
                try {
                    repository.applyToJob(JobUser(jobId, userId))
                    Log.d("JobAppViewModel", "Application successful for UserId=$userId, JobId=$jobId")
                } catch (e: Exception) {
                    Log.e("JobAppViewModel", "Error applying to job: ${e.message}", e)
                }
            } else {
                Log.w("JobAppViewModel", "Cannot apply: User not logged in (userId is null).")
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
