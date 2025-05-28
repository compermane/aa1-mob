// app/src/main/java/com/example/aa1_mob/viewmodel/AppViewModelProvider.kt
package com.example.aa1_mob.viewmodel

import androidx.lifecycle.ViewModelProvider.Factory
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.aa1_mob.AppApplication // Importe seu AppApplication
import com.example.aa1_mob.repository.JobRepository // Importe o JobRepository
import com.example.aa1_mob.repository.AuthRepository // Importe o AuthRepository

/**
 * Provides Factory to create instance of ViewModel for the entire Inventory app
 */
object AppViewModelProvider {
    val Factory = viewModelFactory {
        // Initializer for JobViewModel
        initializer {
            JobViewModel(
                aa1mobApplication().container.jobRepository
            )
        }
        // Initializer for LoginViewModel
        initializer {
            LoginViewModel(
                aa1mobApplication().container.authRepository // Use o authRepository aqui
            )
        }
        // Adicione outros ViewModels aqui conforme necessário
    }
}

/**
 * Extension function to queries for [Application] object and returns an instance of [AppApplication].
 */
fun CreationExtras.aa1mobApplication(): AppApplication =
    (this[androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as AppApplication)