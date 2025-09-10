package com.dbtech.messengerapp.domain.usecase

import com.dbtech.messengerapp.domain.repository.AuthRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RegisterUseCase(private val repository: AuthRepository) {
    suspend operator fun invoke(email: String, password: String): Result<Unit> {
        return withContext(Dispatchers.IO) {
            try {
                repository.register(email, password)
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }
}