package com.example.testapp.ui.navigation

import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.navigation.NavArgument
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.createGraph
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.example.testapp.ui.screens.home.HomeScreen
import com.example.testapp.ui.screens.register.RegistrationScreen

@Composable
fun AppNavGraph() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Graph.Auth.route
    ) {
        authGraph(
            navController = navController
        )

        mainGraph(
            navController = navController
        )
    }
}

fun NavGraphBuilder.authGraph(
    navController: NavController
) {
    navigation(
        route = Graph.Auth.route,
        startDestination = Screen.Registration.route
    ) {
        composable(Screen.Registration.route) {
            RegistrationScreen(
                onNavigateHome = {encodedEmail ->
                    navController.navigate(Screen.Home.createRoute(encodedEmail)) {
                        popUpTo(Graph.Auth.route) {
                            inclusive = true
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}

fun NavGraphBuilder.mainGraph(
    navController: NavController
) {
    navigation(
        route = Graph.Main.route,
        startDestination = Screen.Home.route
    ){
        composable(
            route = Screen.Home.route,
            arguments = listOf(navArgument("email"){type = NavType.StringType})
        ) {
            HomeScreen(
                onLogout = {
                    navController.navigate(Graph.Auth.route) {
                        popUpTo(Graph.Main.route){inclusive = true}
                        launchSingleTop = true
                    }
                }
            )
        }
    }
}