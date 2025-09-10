package com.dbtech.messengerapp.domain.repository

interface AuthRepository {
    suspend fun register(email: String, password: String): Result<Unit>
    suspend fun login(email: String, password: String): Result<Boolean>
    fun currentUserId(): String?
    suspend fun logout(): Result<Unit>
}