package com.example.aa1_mob.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.aa1_mob.R
import com.example.aa1_mob.repository.UserRepository // Importe o AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class RegisterViewModel(
    private val userRepository: UserRepository,
    private val app: Application
) : AndroidViewModel(app) {

    private val _nome = MutableStateFlow("")
    val nome: StateFlow<String> = _nome.asStateFlow()

    private val _email = MutableStateFlow("")
    val email: StateFlow<String> = _email.asStateFlow()

    private val _password = MutableStateFlow("")
    val password: StateFlow<String> = _password.asStateFlow()

    private val _confirmPassword = MutableStateFlow("")
    val confirmPassword: StateFlow<String> = _confirmPassword.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _registrationSuccess = MutableStateFlow(false)
    val registrationSuccess: StateFlow<Boolean> = _registrationSuccess.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()

    fun onNomeChange(newName: String) {
        _nome.value = newName
        _errorMessage.value = null
    }

    fun onEmailChange(newEmail: String) {
        _email.value = newEmail
        _errorMessage.value = null
    }

    fun onPasswordChange(newPassword: String) {
        _password.value = newPassword
        _errorMessage.value = null
    }

    fun onConfirmPasswordChange(newConfirmPassword: String) {
        _confirmPassword.value = newConfirmPassword
        _errorMessage.value = null
    }

    fun register() {
        Log.i("RegisterViewModel", "register method called")
        _isLoading.value = true
        _errorMessage.value = null
        _registrationSuccess.value = false

        if (_nome.value.isBlank() || _email.value.isBlank() || _password.value.isBlank() || _confirmPassword.value.isBlank()) {
            _errorMessage.value = app.getString(R.string.fill_all_the_fields)
            _isLoading.value = false
            return
        }

        if (_password.value != _confirmPassword.value) {
            _errorMessage.value = app.getString(R.string.passwords_do_not_match)
            _isLoading.value = false
            return
        }

        viewModelScope.launch {
            Log.i("RegisterViewModel", "Register values: ${_nome.value}, ${_password.value}, ${_email.value}")
            val result = userRepository.register(
                name = _nome.value,
                password = _password.value,
                email = _email.value
            )

            _isLoading.value = false
            Log.i("RegisterViewModel [register]", "${result}")
            if (result != null) {
                _registrationSuccess.value = true
            } else {
                _errorMessage.value = app.getString(R.string.registration_error)
            }
        }
    }

    fun resetRegistrationState() {
        _registrationSuccess.value = false
        _errorMessage.value = null
    }
}