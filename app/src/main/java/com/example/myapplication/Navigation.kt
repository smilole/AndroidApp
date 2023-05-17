package com.example.myapplication

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.myapplication.screens.MainScreen
import com.example.myapplication.screens.OutputScreen

@Composable
fun Navigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screen.MainScreen.route) {
        composable(Screen.MainScreen.route) {
            MainScreen(navController = navController)
        }

        composable("${Screen.OutputScreen.route}/{out}",
            arguments = listOf(
                navArgument("out"){type = NavType.StringType}
            )){
            val out = it.arguments?.getString("out")!!
            OutputScreen(navController = navController, output = out)
        }
    }
}

