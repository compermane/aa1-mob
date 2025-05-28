package com.example.aa1_mob.repository

import android.content.Context
import androidx.datastore.dataStoreFile
import androidx.datastore.preferences.core.edit
import com.example.aa1_mob.repository.room.dao.UserDao // Use o UserDao do seu Room
import com.example.aa1_mob.repository.room.models.User
import org.mindrot.jbcrypt.BCrypt // Import para o BCrypt

class AuthRepository(private val userDao: UserDao, private val context : Context) { // O construtor recebe o UserDao

    suspend fun saveLoggedUserId(userId : Int) {
        context.dataStore.edit {
            preferences ->
            preferences[PreferencesKeys.USER_ID] = userId
        }
    }

    // Método para registrar um novo usuário (se você tiver uma tela de cadastro)
    suspend fun registerUser(nome: String, email: String, senhaPlain: String): Boolean {
        // Verifica se o usuário já existe
        if (userDao.getUserByEmail(email) != null) {
            return false // Usuário já existe
        }
        // Gera o hash da senha antes de salvar
        val hashedPassword = BCrypt.hashpw(senhaPlain, BCrypt.gensalt())
        val newUser = User(nome = nome, email = email, senha = hashedPassword) // Use 'senha' aqui
        userDao.insertUser(newUser)
        return true
    }

    // Método para autenticar um usuário
    // Retorna o usuário autenticado ou null se as credenciais forem inválidas
    suspend fun authenticateUser(email: String, senhaPlain: String): User? {
        val user = userDao.getUserByEmail(email)
        if (user != null) {
            // Compara a senha fornecida com o hash armazenado
            if (BCrypt.checkpw(senhaPlain, user.senha)) { // Use 'senha' aqui
                return user
            }
        }
        return null // Credenciais inválidas
    }
}