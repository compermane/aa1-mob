package com.example.aa1_mob.repository.room.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "job")
data class Job (
    @PrimaryKey(autoGenerate = true)
    val idJob       : Int = 0,
    val titulo      : String,
    val empresa     : String,
    val descricao   : String,
    val localizacao : String
)