package com.example.testapp.ui.navigation

import android.net.Uri

sealed class Graph(val route: String) {
    data object Auth : Graph("graph_auth")

    data object Main : Graph("graph_main")
}

sealed class Screen(val route: String) {
    // auth
    data object Registration : Screen("registration")

    data object Login : Screen("login")

    data object ForgotPassword : Screen("forgot_password")

    // main
    data object Home : Screen("home/{email}") {
        fun createRoute(email: String) = "home/${Uri.encode(email)}"
    }
    // позже: Profile, Settings
}
