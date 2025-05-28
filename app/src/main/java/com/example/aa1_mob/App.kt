package com.example.aa1_mob

import android.app.Activity
import android.util.Log
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.example.aa1_mob.ui.theme.Aa1mobTheme
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.aa1_mob.viewmodel.JobViewModel
import kotlinx.coroutines.launch
import com.example.aa1_mob.presentation.ui.screens.HomePageScreen
import com.example.aa1_mob.presentation.ui.screens.JobDetailScreen
import com.example.aa1_mob.presentation.ui.screens.LoginScreen
import com.example.aa1_mob.presentation.ui.screens.RegisterScreen // <--- Importe a RegisterScreen

@Composable
fun App(
    navController : NavHostController = rememberNavController(),
    startingRoute : String = "login"
) {
    val context = LocalContext.current
    val activity = context as? Activity
    val scope = rememberCoroutineScope()

    Scaffold() { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = startingRoute
        ) {
            composable("login") {
                LoginScreen(
                    onLoginSuccess = {
                        navController.navigate("homepage") {
                            popUpTo("login") { inclusive = true }
                        }
                    },
                    onNavigateToRegister = {
                        navController.navigate("register") // <--- Adicione a navegação para a tela de cadastro
                    }
                )
            }
            // <--- Adicione a rota para a tela de cadastro
            composable("register") {
                RegisterScreen(
                    onRegistrationSuccess = {
                        // Após o cadastro, você pode navegar para a tela de login
                        // ou diretamente para a home, dependendo da sua lógica.
                        // O mais comum é voltar para o login e pedir para o usuário fazer login.
                        navController.navigate("login") {
                            popUpTo("register") { inclusive = true } // Limpa a tela de cadastro do back stack
                        }
                    },
                    onNavigateToLogin = {
                        navController.navigate("login") {
                            popUpTo("register") { inclusive = true }
                        }
                    }
                )
            }
            composable("homepage") {
                HomePageScreen(navController)
            }

            composable(
                route = "jobDetail/{jobId}",
                arguments = listOf(navArgument("jobId") { type = NavType.IntType })
            ) { backStackEntry ->
                val jobId = backStackEntry.arguments?.getInt("jobId") ?: 0
                JobDetailScreen(jobId = jobId, onBack = {
                    Log.i("App", "Going back to homepage")
                    navController.navigate("homepage")
                })
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AppPreview() {
    Aa1mobTheme {
        App()
    }
}