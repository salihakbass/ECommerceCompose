package com.salihakbas.ecommercecompose.data.repository

import com.google.firebase.auth.EmailAuthProvider
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

    override suspend fun sendPasswordResetEmail(email: String): Resource<String> {
        return try {
            firebaseAuth.sendPasswordResetEmail(email).await()
            Resource.Success("Şifre sıfırlama bağlantısı gönderildi.")
        } catch (e: Exception) {
            Resource.Error(e.message ?: "Error")
        }
    }

    override suspend fun signOut(): Resource<String> {
        return try {
            firebaseAuth.signOut()
            Resource.Success("Çıkış yapıldı.")
        } catch (e: Exception) {
            Resource.Error(e.message ?: "Error")
        }
    }

    override suspend fun changePassword(
        email: String,
        oldPassword: String,
        newPassword: String
    ): Resource<String> {
        return try {
            val user = firebaseAuth.currentUser
            user?.let {
                val credential = EmailAuthProvider.getCredential(email, oldPassword)
                it.reauthenticate(credential).await()
                it.updatePassword(newPassword).await()
                Resource.Success("Şifre başarıyla değiştirildi.")
            } ?: Resource.Error("Kullanıcı oturum açmamış.")
        } catch (e: Exception) {
            Resource.Error(e.message ?: "Şifre değiştirilemedi.")
        }
    }

    override suspend fun getCurrentUserEmail(): String? {
        return firebaseAuth.currentUser?.email
    }
}


