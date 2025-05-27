package com.example.aa1_mob.repository.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.aa1_mob.repository.room.dao.JobDao
import com.example.aa1_mob.repository.room.dao.UserDao
import com.example.aa1_mob.repository.room.models.Job
import com.example.aa1_mob.repository.room.models.JobUser
import com.example.aa1_mob.repository.room.models.User

@Database(
    entities = [Job::class, User::class, JobUser::class],
    version = 1
)
abstract class JobDatabase : RoomDatabase() {
    abstract fun jobDao(): JobDao
    abstract fun userDao(): UserDao
}