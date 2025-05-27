package com.example.aa1_mob

import android.app.Activity
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.example.aa1_mob.ui.theme.Aa1mobTheme
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import kotlinx.coroutines.launch

@Composable
fun App(
    navController : NavHostController = rememberNavController(),
    startingRoute : String = "homepage"
) {
    val context = LocalContext.current
    val activity = context as? Activity
    val scope = rememberCoroutineScope()

    Scaffold() { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = startingRoute
        ) {
            composable("homepage") {
                HomePageScreen()
            }
//            composable("main") {
//                MainScreen(
//                    navigateUp = {
//                        scope.launch {
//                            activity?.finish()
//                        }
//                    }
//                )
//            }
//            composable("logTries") {
//                LogTriesScreen(
//                    navigateUp = {
//                        navController.popBackStack()
//                    }
//                )
//            }
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