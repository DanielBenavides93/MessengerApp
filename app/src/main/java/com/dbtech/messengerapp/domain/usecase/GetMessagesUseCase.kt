package com.dbtech.messengerapp.domain.usecase

import com.dbtech.messengerapp.domain.model.Message
import com.dbtech.messengerapp.domain.repository.ChatRepository
import kotlinx.coroutines.flow.Flow

class GetMessagesUseCase(
    private val repository: ChatRepository
) {
    operator fun invoke(): Flow<List<Message>>{
        return repository.getMessages()
    }
}