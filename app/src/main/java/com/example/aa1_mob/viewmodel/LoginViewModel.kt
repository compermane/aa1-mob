// app/src/main/java/com/example/aa1_mob/viewmodel/LoginViewModel.kt
package com.example.aa1_mob.viewmodel

import android.app.Application
import android.util.Log
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.aa1_mob.R
import com.example.aa1_mob.repository.UserRepository // Use o AuthRepository
import com.example.aa1_mob.repository.retrofit.UserData
import com.example.aa1_mob.repository.saveLoggedUserId
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class LoginViewModel(
    private val userRepository: UserRepository,
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

    private val _user = MutableStateFlow<UserData?>(null)
    val user: StateFlow<UserData?> = _user

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
            _errorMessage.value = app.getString(R.string.fill_all_the_fields)
            _isLoading.value = false
            return
        }

        viewModelScope.launch {
            Log.i("DEBUG", "${userRepository.getAllUsersOnce()}")
            val result = userRepository.login(email.value, password.value)
            _user.value = result

            _isLoading.value = false

            Log.i("LoginViewModel [login]", "${result}")

            if(result != null) {
                app.saveLoggedUserId(result.userId!!)
                _loginSuccess.value = true
            } else {
                _errorMessage.value = app.getString(R.string.invalid_credentials)
            }
        }
    }

    fun resetLoginState() {
        _loginSuccess.value = false
        _errorMessage.value = null
    }
}