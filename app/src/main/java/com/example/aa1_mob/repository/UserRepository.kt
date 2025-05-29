package com.example.aa1_mob.repository

import android.content.Context
import android.util.Log
import androidx.datastore.preferences.core.edit
import com.example.aa1_mob.repository.retrofit.LoginRequest
import com.example.aa1_mob.repository.retrofit.RegisterRequest
import com.example.aa1_mob.repository.retrofit.RetrofitInstance
import com.example.aa1_mob.repository.retrofit.UserApiInterface
import com.example.aa1_mob.repository.retrofit.UserData
import com.example.aa1_mob.repository.room.dao.UserDao // Use o UserDao do seu Room
import com.example.aa1_mob.repository.room.models.User
import org.mindrot.jbcrypt.BCrypt // Import para o BCrypt

class UserRepository(private val userDao : UserDao, private val context : Context,
                     val useTestApi : Boolean = false) { // O construtor recebe o UserDao

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
                RegisterRequest(name, email, password)
            )

            Log.i("UserRepository", "Response: ${response}")
            Log.i("UserRepository", "isSuccessful: ${response.isSuccessful}")
            Log.i("UserRepository", "code: ${response.code()}")
            Log.i("UserRepository", "message: ${response.message()}")
            Log.i("UserRepository", "errorBody: ${response.errorBody()?.string()}")

            Log.i("UserRepository [register]", "${response}")
            if (response.isSuccessful) {
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

    suspend fun login(email: String, senha: String): UserData? {
        return try {
            val response = RetrofitInstance.api.login(LoginRequest(email, senha))
            if (response.isSuccessful) {
                response.body()
            } else {
                null
            }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

}