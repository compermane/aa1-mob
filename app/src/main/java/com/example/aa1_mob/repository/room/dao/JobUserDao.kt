package com.example.aa1_mob.repository.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.aa1_mob.repository.room.models.JobUser
import com.example.aa1_mob.repository.room.models.JobWithUsers
import com.example.aa1_mob.repository.room.models.UserWithJobs

@Dao
interface JobUserDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun applyToJob(crossRef : JobUser)

    @Transaction
    @Query("SELECT * FROM job where idJob = :idJob")
    suspend fun getJobWithUsers(idJob : Int): JobWithUsers

    @Transaction
    @Query("SELECT * FROM user where idUser = :idUser")
    suspend fun getUserWithJobs(idUser : Int): UserWithJobs
}

