package com.example.aa1_mob.repository.room.models

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

data class UserWithJobs(
    @Embedded val user: User,
    @Relation(
        parentColumn = "idUser",
        entityColumn = "idJob",
        associateBy = Junction(JobUser::class)
    )
    val jobs: List<Job>
)