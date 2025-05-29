package com.example.aa1_mob

import android.app.Activity
import android.util.Log
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.aa1_mob.ui.theme.Aa1mobTheme
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.aa1_mob.presentation.ui.screens.ApplyToJobScreen
import com.example.aa1_mob.viewmodel.JobViewModel
import kotlinx.coroutines.launch
import com.example.aa1_mob.presentation.ui.screens.HomePageScreen
import com.example.aa1_mob.presentation.ui.screens.JobDetailScreen
import com.example.aa1_mob.presentation.ui.screens.LoginScreen
import com.example.aa1_mob.presentation.ui.screens.RegisterScreen
import com.example.aa1_mob.presentation.ui.screens.UserProfileScreen // <--- Importe a UserProfileScreen
import com.example.aa1_mob.viewmodel.AppViewModelProvider
import com.example.aa1_mob.viewmodel.UserProfileViewModel

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
                        navController.navigate("register")
                    }
                )
            }
            composable("register") {
                RegisterScreen(
                    onRegistrationSuccess = {
                        navController.navigate("login") {
                            popUpTo("register") { inclusive = true }
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
                JobDetailScreen(jobId = jobId, navController = navController, onBack = { // Passe navController aqui
                    Log.i("App", "Going back to homepage")
                    navController.navigate("homepage")
                })
            }

            composable(
                route = "jobApplication/{jobId}",
                arguments = listOf(navArgument("jobId") { type = NavType.IntType })
            ) { backStackEntry ->
                val jobId = backStackEntry.arguments?.getInt("jobId") ?: 0
                // Você já tinha ApplyToJobScreen no seu código, só adicionei a rota
                ApplyToJobScreen(
                    jobId = jobId,
                    onBack = { navController.popBackStack() }, // Volta para a tela anterior
                    navController = navController
                )
            }

            // <--- Adicione a rota para a tela de perfil do usuário
            composable("userProfile") { backStackEntry ->
                val userProfileViewModel: UserProfileViewModel = viewModel(factory = AppViewModelProvider.Factory)
                // Chama loadUserProfile sempre que a rota "userProfile" é acessada
                // Usamos a key 'Unit' se queremos que seja chamado uma vez na composição inicial,
                // ou 'backStackEntry' se queremos que seja re-executado quando a tela retorna à pilha
                // e é recomposta. Para um perfil, 'backStackEntry' é mais robusto para refreshes.
                LaunchedEffect(key1 = backStackEntry.lifecycle.currentState) {
                    // Isso garante que loadUserProfile é chamado quando a tela está resumed
                    if (backStackEntry.lifecycle.currentState.isAtLeast(androidx.lifecycle.Lifecycle.State.RESUMED)) {
                        userProfileViewModel.loadUserProfile()
                    }
                }
                UserProfileScreen(
                    onBack = {
                        navController.popBackStack() // Volta para a tela anterior (provavelmente HomePage)
                    },
                    viewModel = userProfileViewModel // Passa a instância do ViewModel
                )
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