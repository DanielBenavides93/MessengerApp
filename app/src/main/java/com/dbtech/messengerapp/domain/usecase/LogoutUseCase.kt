package com.dbtech.messengerapp.domain.usecase

import com.dbtech.messengerapp.domain.repository.AuthRepository

class LogoutUseCase(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(): Result<Unit> {
        return repository.logout()
    }
}