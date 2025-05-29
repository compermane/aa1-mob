// app/src/main/java/com/example/aa1_mob/repository/room/AppDatabase.kt
package com.example.aa1_mob.repository.room

import android.content.Context
import android.util.Log // Import para Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase // Import para SupportSQLiteDatabase
import com.example.aa1_mob.repository.room.dao.JobDao
import com.example.aa1_mob.repository.room.dao.JobUserDao
import com.example.aa1_mob.repository.room.dao.UserDao
import com.example.aa1_mob.repository.room.models.Job
import com.example.aa1_mob.repository.room.models.JobUser
import com.example.aa1_mob.repository.room.models.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first // Import para .first()
import kotlinx.coroutines.launch
import org.mindrot.jbcrypt.BCrypt // Import para o BCrypt
import kotlin.reflect.KParameter

@Database(
    entities = [Job::class, User::class, JobUser::class],
    version = 3, // <--- VERSÃO INCREMENTADA!
    exportSchema = false // Recomendado para desenvolvimento, em produção gere o schema
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun jobDao(): JobDao
    abstract fun userDao(): UserDao
    abstract fun jobUserDao(): JobUserDao

    companion object {

        @Volatile
        private var Instance: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return Instance ?: synchronized(this) {
                val instance = Room.databaseBuilder(context.applicationContext, AppDatabase::class.java, "app_database")
                    .fallbackToDestructiveMigration() // <--- Adicionado para facilitar o desenvolvimento
                    .addCallback(AppDatabaseCallback(context.applicationContext)) // <--- Adicionado callback para popular dados
                    .build()
                Instance = instance
                instance
            }
        }
    }

    private class AppDatabaseCallback(private val context: Context) : RoomDatabase.Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            Instance?.let { database -> // <-- Aqui era INSTANCE? errado
                CoroutineScope(Dispatchers.IO).launch {
                    val userDao = database.userDao()
                    val jobDao = database.jobDao()
                    val userCount = userDao.getUserCount().first()
                    if (userCount == 0) {
                        // Crie um usuário de teste apenas se não houver nenhum
                        val hashedPassword = BCrypt.hashpw("password", BCrypt.gensalt())
                        val testUser = User(
                            nome = "Usuário Teste", // Nome do usuário
                            email = "test@example.com",
                            senha = hashedPassword // Armazenar o hash da senha
                        )
                        userDao.insertUser(testUser)
                        Log.d("AppDatabase", "Usuário de teste inserido: ${testUser.email}")
                    }
                }
            }
        }
    }
}