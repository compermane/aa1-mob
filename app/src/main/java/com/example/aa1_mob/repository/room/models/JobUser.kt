package com.example.aa1_mob.repository.room.models

import androidx.room.Entity
import androidx.room.ForeignKey

@Entity(primaryKeys = ["idJob", "idUser"])
data class JobUser (
    val idJob  : Int,
    val idUser : Int
)