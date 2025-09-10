package com.dbtech.messengerapp.data.repository

import com.dbtech.messengerapp.data.local.DataStoreManager
import com.dbtech.messengerapp.domain.repository.AuthRepository
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.tasks.await

class AuthRepositoryImpl(
    private val firebaseAuth: FirebaseAuth,
    private val dataStoreManager: DataStoreManager

) : AuthRepository {

    override suspend fun login(email: String, password: String): Result<Boolean> {
        return try {
            firebaseAuth.signInWithEmailAndPassword(email, password).await()
            dataStoreManager.setLoggedIn(true)
            Result.success(true)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun register(email: String, password: String): Result<Unit> {
        return try {
            firebaseAuth.createUserWithEmailAndPassword(email, password).await()
            dataStoreManager.setLoggedIn(true)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override fun currentUserId(): String? {
        return firebaseAuth.currentUser?.uid
    }

    override suspend fun logout(): Result<Unit> {
        return try {
            firebaseAuth.signOut()
            dataStoreManager.setLoggedIn(false)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}