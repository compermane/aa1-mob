package com.example.aa1_mob.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.aa1_mob.repository.JobUserRepository
import com.example.aa1_mob.repository.getLoggedUserId
import com.example.aa1_mob.repository.room.models.UserWithJobs
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.firstOrNull // Mantenha esta importação
import kotlinx.coroutines.launch

class UserProfileViewModel(
    private val jobUserRepository: JobUserRepository,
    private val application: Application
) : AndroidViewModel(application) {

    private val _userProfile = MutableStateFlow<UserWithJobs?>(null)
    val userProfile: StateFlow<UserWithJobs?> = _userProfile.asStateFlow()

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()

    // REMOVA O BLOCO init {} AQUI
    // init {
    //     loadUserProfile()
    // }

    fun loadUserProfile() {
        _isLoading.value = true
        _errorMessage.value = null
        viewModelScope.launch {
            try {
                val userId = application.getLoggedUserId().firstOrNull()

                if (userId != null) {
                    val profile = jobUserRepository.getUserWithJobs(userId)
                    _userProfile.value = profile
                } else {
                    _errorMessage.value = "Nenhum usuário logado encontrado."
                    _userProfile.value = null
                }
            } catch (e: Exception) {
                _errorMessage.value = "Erro ao carregar o perfil: ${e.localizedMessage ?: "Erro desconhecido"}"
                _userProfile.value = null
                e.printStackTrace()
            } finally {
                _isLoading.value = false
            }
        }
    }
}