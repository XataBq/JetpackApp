package com.example.testapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.example.testapp.ui.screens.auth.AuthViewModel
import com.example.testapp.ui.screens.auth.login.LoginScreen
import com.example.testapp.ui.screens.auth.register.RegistrationScreen
import com.example.testapp.ui.screens.home.HomeScreen

@Composable
fun AppNavGraph() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Graph.Auth.route,
    ) {
        authGraph(
            navController = navController,
        )

        mainGraph(
            navController = navController,
        )
    }
}

fun NavGraphBuilder.authGraph(navController: NavController) {
    navigation(
        route = Graph.Auth.route,
        startDestination = Screen.Registration.route,
    ) {
        composable(Screen.Registration.route) { backStackEntry ->
            val parentEntry =
                remember(backStackEntry) {
                    navController.getBackStackEntry(Graph.Auth.route)
                }
            val authViewModel: AuthViewModel = viewModel(parentEntry)
            RegistrationScreen(
                viewModel = authViewModel,
                onNavigateHome = { encodedEmail ->
                    navController.navigate(Screen.Home.createRoute(encodedEmail)) {
                        popUpTo(Graph.Auth.route) {
                            inclusive = true
                        }
                        launchSingleTop = true
                    }
                },
                onNavigateLogin = {
                    navController.navigate(Screen.Login.route) {
                        launchSingleTop = true
                    }
                },
            )
        }

        composable(Screen.Login.route) { backStackEntry ->
            val parentEntry =
                remember(backStackEntry) {
                    navController.getBackStackEntry(Graph.Auth.route)
                }
            val authViewModel: AuthViewModel = viewModel(parentEntry)
            LoginScreen(
                viewModel = authViewModel,
                onRegisterScreen = {
                    navController.navigate(Screen.Registration.route) {
                        launchSingleTop = true
                    }
                },
                onNavigateHome = { encodedEmail ->
                    navController.navigate(Screen.Home.createRoute(encodedEmail)) {
                        popUpTo(Graph.Auth.route) {
                            inclusive = true
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
            )
        }
    }
}

fun NavGraphBuilder.mainGraph(navController: NavController) {
    navigation(
        route = Graph.Main.route,
        startDestination = Screen.Home.route,
    ) {
        composable(
            route = Screen.Home.route,
            arguments = listOf(navArgument("email") { type = NavType.StringType }),
        ) {
            HomeScreen(
                onLogout = {
                    navController.navigate(Graph.Auth.route) {
                        popUpTo(Graph.Main.route) { inclusive = true }
                        launchSingleTop = true
                    }
                },
            )
        }
    }
}
