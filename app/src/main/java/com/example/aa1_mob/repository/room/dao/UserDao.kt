package com.example.aa1_mob.repository.room.dao

import androidx.room.*
import com.example.aa1_mob.repository.room.models.JobUser
import com.example.aa1_mob.repository.room.models.User
import com.example.aa1_mob.repository.room.models.UserWithJobs
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {

    @Query("SELECT * FROM user")
    fun getAllUsers(): Flow<List<User>>

    @Insert
    suspend fun insertUser(user: User)

    @Delete
    suspend fun deleteUser(user: User)

    @Transaction
    @Query("SELECT * FROM user WHERE id = :userId")
    fun getUserWithJobs(userId: Int): Flow<UserWithJobs>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun applyToJob(crossRef: JobUser)
}