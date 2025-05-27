package com.example.aa1_mob.viewmodel

import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.aa1_mob.AppApplication


/**
 * Provides Factory to create instance of ViewModel for the entire app
 */
object AppViewModelProvider {
    val Factory = viewModelFactory {

        // Initializer for JobViewModel
        initializer {
            JobViewModel(appApplication().container.jobRepository)
        }

    }
}

fun CreationExtras.appApplication() : AppApplication = (this[AndroidViewModelFactory.APPLICATION_KEY] as AppApplication)