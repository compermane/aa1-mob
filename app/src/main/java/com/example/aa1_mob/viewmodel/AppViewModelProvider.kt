package com.example.aa1_mob.viewmodel

import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.aa1_mob.AppApplication

object AppViewModelProvider {
    val Factory = viewModelFactory {
        initializer {
            JobViewModel(
                aa1mobApplication().container.jobRepository
            )
        }
        initializer {
            LoginViewModel(
                aa1mobApplication().container.userRepository,
                aa1mobApplication()
            )
        }
        initializer {
            RegisterViewModel(
                aa1mobApplication().container.userRepository,
                aa1mobApplication()
            )
        }
        initializer {
            JobApplicationViewModel(
                aa1mobApplication().container.jobUserRepository,
                aa1mobApplication(),
            )
        }

        initializer {
            UserProfileViewModel(
                aa1mobApplication().container.jobUserRepository,
                aa1mobApplication()
            )
        }
    }
}

fun CreationExtras.aa1mobApplication(): AppApplication =
    (this[androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as AppApplication)