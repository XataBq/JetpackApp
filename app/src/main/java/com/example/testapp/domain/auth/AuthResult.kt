package com.example.testapp.domain.auth

sealed class AuthResult {
    data object Success : AuthResult()

    sealed class Error : AuthResult() {
        data object InvalidEmail : Error()

        data object Server : Error()

        data object Network : Error()

        data class Unknown(
            val cause: Throwable,
        ) : Error()
    }
}
