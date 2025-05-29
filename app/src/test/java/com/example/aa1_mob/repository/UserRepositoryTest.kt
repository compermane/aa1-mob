package com.example.aa1_mob.repository

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.example.aa1_mob.repository.retrofit.LoginRequest
import com.example.aa1_mob.repository.retrofit.RegisterRequest
import com.example.aa1_mob.repository.room.dao.UserDao
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import org.mockito.kotlin.mock
import kotlin.test.assertEquals


class UserApiTest {
    private lateinit var userDao: UserDao
    private lateinit var context: Context
    private lateinit var userRepository: UserRepository

    @Before
    fun setup() {
        userDao = Mockito.mock<UserDao>(UserDao::class.java)
        context = Mockito.mock(Context::class.java)
        userRepository = UserRepository(userDao, context, useTestApi = true)
    }

    @Test
    fun testRegisterAndLogin() = runBlocking {
        val registerResponse = userRepository.register(
                name = "Teste Usuario",
                email = "teste@email.com",
                password = "123456"
        )

        assertEquals("Teste Usuario", registerResponse!!.name)
        assertEquals("teste@email.com", registerResponse!!.email)

        val loginResponse = userRepository.login(
            email = "teste@email.com",
            senha = "123456"
        )

        assertEquals(registerResponse!!.email, loginResponse!!.email)
        assertEquals(registerResponse!!.name, loginResponse!!.name)
    }
}