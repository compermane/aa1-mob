package com.example.aa1_mob.repository

import android.content.Context
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore: androidx.datastore.core.DataStore<Preferences> by preferencesDataStore(
    name = "user_prefs"
)

object PreferencesKeys {
    val USER_ID = intPreferencesKey("user_id")
}

// Função para salvar o ID do usuário logado
suspend fun Context.saveLoggedUserId(userId: Int) {
    dataStore.edit { preferences ->
        preferences[PreferencesKeys.USER_ID] = userId
    }
}

// Função para recuperar o ID do usuário logado
fun Context.getLoggedUserId(): Flow<Int?> {
    return dataStore.data.map { preferences ->
        preferences[PreferencesKeys.USER_ID]
    }
}

// Função para limpar (logout)
suspend fun Context.clearLoggedUserId() {
    dataStore.edit { preferences ->
        preferences.remove(PreferencesKeys.USER_ID)
    }
}