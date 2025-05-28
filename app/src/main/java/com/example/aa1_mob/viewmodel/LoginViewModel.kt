// app/src/main/java/com/example/aa1_mob/viewmodel/LoginViewModel.kt
package com.example.aa1_mob.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.aa1_mob.repository.AuthRepository // Use o AuthRepository
import com.example.aa1_mob.repository.saveLoggedUserId
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class LoginViewModel(
    private val authRepository: AuthRepository,
    private val app: Application
) : AndroidViewModel(app) { // Mude o nome do parâmetro para authRepository

    private val _email = MutableStateFlow("")
    val email: StateFlow<String> = _email.asStateFlow()

    private val _password = MutableStateFlow("") // Mude para password para ser consistente com o campo da tela
    val password: StateFlow<String> = _password.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _loginSuccess = MutableStateFlow(false)
    val loginSuccess: StateFlow<Boolean> = _loginSuccess.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()

    // Para armazenar o usuário logado (nome, role, etc.), se necessário para a UI
    private val _loggedInUserName = MutableStateFlow<String?>(null)
    val loggedInUserName: StateFlow<String?> = _loggedInUserName.asStateFlow()


    fun onEmailChange(newEmail: String) {
        _email.value = newEmail
        _errorMessage.value = null
    }

    fun onPasswordChange(newPassword: String) {
        _password.value = newPassword
        _errorMessage.value = null
    }

    fun login() {
        _isLoading.value = true
        _errorMessage.value = null
        _loginSuccess.value = false

        if (_email.value.isBlank() || _password.value.isBlank()) {
            _errorMessage.value = "Por favor, preencha todos os campos."
            _isLoading.value = false
            return
        }

        viewModelScope.launch {
            try {
                // Chama o método authenticateUser do AuthRepository
                val authenticatedUser = authRepository.authenticateUser(
                    email = _email.value,
                    senhaPlain = _password.value // Use senhaPlain conforme o AuthRepository
                )
                if (authenticatedUser != null) {
                    _loginSuccess.value = true
                    _loggedInUserName.value = authenticatedUser.nome // Exemplo: salva o nome do usuário logado
                    // Aqui você pode salvar o ID do usuário e o role/nome em DataStore para persistir a sessão (R4).
                    app.saveLoggedUserId(authenticatedUser.idUser)
                } else {
                    _errorMessage.value = "Credenciais inválidas. Verifique seu e-mail e senha."
                }
            } catch (e: Exception) {
                _errorMessage.value = "Erro ao tentar fazer login: ${e.localizedMessage ?: "Erro desconhecido"}"
                e.printStackTrace() // Para debug
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun resetLoginState() {
        _loginSuccess.value = false
        _errorMessage.value = null
    }
}