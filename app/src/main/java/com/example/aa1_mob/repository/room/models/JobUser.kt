// com.example.aa1_mob.repository.room.models.JobUser.kt
package com.example.aa1_mob.repository.room.models

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index // Importe este

@Entity(
    tableName = "job_user_cross_ref", // Um nome mais descritivo para uma tabela de junção
    primaryKeys = ["idJob", "idUser"], // Chave primária composta
    foreignKeys = [
        ForeignKey(
            entity = Job::class,
            parentColumns = ["idJob"], // Coluna da tabela Job
            childColumns = ["idJob"], // Coluna nesta tabela
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = User::class,
            parentColumns = ["idUser"], // Coluna da tabela User
            childColumns = ["idUser"], // Coluna nesta tabela
            onDelete = ForeignKey.CASCADE
        )
    ],
    // Adicione os índices aqui:
    indices = [
        Index(value = ["idUser"]), // Índice para idUser
        Index(value = ["idJob"])  // Índice para idJob (geralmente bom ter para ambas as FKs)
    ]
)
data class JobUser(
    val idJob: Int,
    val idUser: Int
)