package com.salihakbas.ecommercecompose.data.repository

import com.google.firebase.auth.FirebaseAuth
import com.salihakbas.ecommercecompose.common.Resource
import com.salihakbas.ecommercecompose.domain.repository.FirebaseAuthRepository
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FirebaseAuthRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth
) : FirebaseAuthRepository {
    override suspend fun createUserWithEmailAndPassword(
        name: String,
        surname: String,
        email: String,
        password: String
    ): Resource<String> {
        return try {
            val result = firebaseAuth.createUserWithEmailAndPassword(email, password).await()
            Resource.Success(result.user?.uid.orEmpty())
        } catch (e: Exception) {
            Resource.Error(e.message ?: "Error")
        }
    }

    override suspend fun signInWithEmailAndPassword(
        email: String,
        password: String
    ): Resource<String> {
        return try {
            val result = firebaseAuth.signInWithEmailAndPassword(email, password).await()
            Resource.Success(result.user?.uid.orEmpty())
        } catch (e: Exception) {
            Resource.Error(e.message ?: "Error")
        }

    }

    override suspend fun isUserLoggedIn(): Boolean = firebaseAuth.currentUser != null
}


