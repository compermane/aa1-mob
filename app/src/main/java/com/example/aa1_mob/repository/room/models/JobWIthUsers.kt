package com.example.aa1_mob.repository.room.models

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.example.aa1_mob.repository.room.models.User

data class JobWithUsers(
    @Embedded val job: Job,
    @Relation(
        parentColumn = "idJob",
        entityColumn = "idUser",
        associateBy = Junction(JobUser::class)
    )
    val users: List<User>
)