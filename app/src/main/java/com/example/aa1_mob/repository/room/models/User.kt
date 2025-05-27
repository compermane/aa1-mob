package com.example.aa1_mob.repository.room.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user")
data class User (
    @PrimaryKey(autoGenerate = true)
    val id    : Int = 0,
    val nome  : String,
    val email : String,
    val senha : String
)