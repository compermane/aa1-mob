package com.example.aa1_mob.repository

import android.content.Context
import android.util.Log
import androidx.datastore.preferences.core.edit
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.aa1_mob.repository.retrofit.LoginRequest
import com.example.aa1_mob.repository.retrofit.RegisterRequest
import com.example.aa1_mob.repository.retrofit.RetrofitInstance
import com.example.aa1_mob.repository.retrofit.UserApiInterface
import com.example.aa1_mob.repository.retrofit.UserData
import com.example.aa1_mob.repository.room.dao.UserDao // Use o UserDao do seu Room
import com.example.aa1_mob.repository.room.models.User
import kotlinx.coroutines.flow.compose
import kotlinx.coroutines.flow.first
import org.mindrot.jbcrypt.BCrypt // Import para o BCrypt

class UserRepository(private val userDao : UserDao, private val context : Context,
                     val useTestApi : Boolean = false) {

    private var client : UserApiInterface

    init {
        client = if (useTestApi) RetrofitInstance.testapi else RetrofitInstance.api
    }

    suspend fun saveLoggedUserId(userId : Int) {
        context.dataStore.edit {
            preferences ->
            preferences[PreferencesKeys.USER_ID] = userId
        }
    }

    // Auth
    suspend fun register(name: String, email: String, password: String): UserData? {
        return try {
            val response = RetrofitInstance.api.register(
                RegisterRequest(name = name, email = email, password = password)
            )

            if (response.isSuccessful) {
                response.body()?.let {
                    userDataFromApi ->
                    val userLocal = User(
                        nome = userDataFromApi.name,
                        senha = userDataFromApi.password,
                        email = userDataFromApi.email
                    )
                    Log.i("UserRepository", "foi até aqui")
                    userDao.insertUser(userLocal)
                }
                response.body()
            } else {
                null
            }
        } catch (e: Exception) {
            Log.e("UserRepository", "BRUH ", e)
            e.printStackTrace()
            null
        }
    }

    suspend fun getAllUsersOnce(): List<User> = userDao.getAllUsers().first()

    suspend fun login(email: String, senha: String): UserData? {
        return try {
            val response = RetrofitInstance.api.login(LoginRequest(email, senha))
            if (response.isSuccessful) {
                response.body()?.let {
                    userDataFromApi ->
                    val emailData = userDataFromApi.email
                    val senhaData = userDataFromApi.password

                    Log.i("UserRepository", "foi até aqui ${emailData}, ${senhaData}")
                    val user = userDao.getUserByEmailAndSenha(emailData, senhaData)
                    if(user != null) {
                        Log.i("UserRepository", "agora veio até aqui")
                        return UserData(user.idUser, user.email, user.senha, user.nome)
                    }
                    null
                }
            } else {
                null
            }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

}