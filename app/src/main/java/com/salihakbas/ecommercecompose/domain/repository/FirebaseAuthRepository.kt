package com.salihakbas.ecommercecompose.domain.repository

import com.salihakbas.ecommercecompose.common.Resource

interface FirebaseAuthRepository {
    suspend fun createUserWithEmailAndPassword(
        name: String,
        surname: String,
        email: String,
        password: String,
    ): Resource<String>

    suspend fun signInWithEmailAndPassword(
        email: String,
        password: String
    ): Resource<String>

    suspend fun isUserLoggedIn(): Boolean
}