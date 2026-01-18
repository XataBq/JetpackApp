package com.example.testapp.ui.navigation

sealed class Graph(val route: String) {
    data object Auth: Graph("graph_auth")
    data object Main: Graph("graph_main")
}
sealed class Screen (val route: String){
    //auth
    data object Registration: Screen("registration")
    // позже: Login, Forgot

    //main
    data object Home: Screen("home")
    // позже: Profile, Settings
}