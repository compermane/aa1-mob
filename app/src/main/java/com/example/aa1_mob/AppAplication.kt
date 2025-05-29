package com.example.aa1_mob

import android.app.Application
import android.content.Context
import android.util.Log
import com.example.aa1_mob.repository.UserRepository
import com.example.aa1_mob.repository.JobRepository
import com.example.aa1_mob.repository.JobUserRepository
import com.example.aa1_mob.repository.room.AppDatabase

/**
 * REQUIREMENT:
 * You need to specify attribute android:name=".AppApplication" in AndroidManifest.xml
 * Otherwise, this class is not initialized
 */
class AppApplication : Application() {

    // instance to obtain dependencies
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = AppContainer(this)
        Log.i("AppApplication", "onCreate")
    }
}

/**
 * We want to limit the visibility of Android-related objects to ViewModels and Composable.
 *
 * So, we attach here the repositories to a GameApplication object
 *    so that we can retrieve them in the AppViewModelProvider.
 */
class AppContainer(private val context: Context) {
    val jobRepository : JobRepository by lazy {
        JobRepository(AppDatabase.getDatabase(context).jobDao())
    }

    // Adicione o AuthRepository, passando o userDao do AppDatabase
    val userRepository : UserRepository by lazy { // Mude de userRepository para authRepository
        UserRepository(
            AppDatabase.getDatabase(context).userDao(),
            context = context
        )
    }

    val jobUserRepository : JobUserRepository by lazy {
        JobUserRepository(AppDatabase.getDatabase(context).jobUserDao())
    }
}