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

    suspend fun findById(id : Int): Job {
        return repository.findById(id)
    }

    suspend fun insertSampleJobs() {
        repository.clearJobs()
        viewModelScope.launch {
            val jobs = listOf(
                Job(titulo = "Dev Android", descricao = "Desenvolvimento mobile", empresa = "Tech Ltda", localizacao ="Remoto"),
                Job(titulo = "Dev Backend", descricao = "APIs com Spring", empresa = "Empresa X", localizacao = "São Paulo"),
                Job(titulo = "Dev Frontend", descricao = "React e Compose Web", empresa = "StartUp Y", localizacao = "Remoto")
            )
            jobs.forEach { repository.insert(it) }
        }
    }
}