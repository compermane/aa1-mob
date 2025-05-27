package com.example.aa1_mob.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.aa1_mob.repository.JobRepository
import com.example.aa1_mob.repository.room.models.Job
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class JobViewModel(private val repository: JobRepository) : ViewModel() {
    val jobs : StateFlow<List<Job>> = repository.allJobs
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    fun insert(job : Job) = viewModelScope.launch {
        repository.insert(job)
    }

    fun insertSampleJobs() {
        viewModelScope.launch {
            repository.insert(
                Job(
                    titulo = "Dev Backend",
                    descricao = "Desenvolvimento com Spring Boot.",
                    empresa = "Empresa X",
                    localizacao = "São Carlos"
                )
            )
        }
    }
}