package com.example.aa1_mob.repository.room.models

import androidx.room.Entity

@Entity(primaryKeys = ["jobId", "userId"])
data class JobUser (
    val jobId  : Int,
    val userId : Int
)