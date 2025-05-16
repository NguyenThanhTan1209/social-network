package com.example.socialnetworkapp.repository

import com.example.socialnetworkapp.model.User
import com.example.socialnetworkapp.service.AuthenticationService
import javax.inject.Inject

class AuthRepositoryImp @Inject constructor(
    private val authenService: AuthenticationService
) : AuthenticationRepository {

    override suspend fun signUpWithEmail(
        email: String,
        password: String,
        userName: String
    ): Result<User> {
        return authenService.signUpWithEmail(
            email = email,
            password = password,
            userName = userName
        )
    }

    override suspend fun signInWithGoogle(): Result<User> {
        return authenService.signInWithGoogle()
    }

    override suspend fun signInWithEmail(email: String, password: String): Result<User?> {
        return authenService.signInWithEmail(
            email = email,
            password = password,
        )
    }

    override fun isUserLoggedIn(): Boolean {
        return authenService.isUserLoggedIn()
    }

    override suspend fun getCurrentUser(): User {
        return authenService.getCurrentUser()
    }

    override suspend fun signOut(): String {
        TODO("Not yet implemented")
    }

}