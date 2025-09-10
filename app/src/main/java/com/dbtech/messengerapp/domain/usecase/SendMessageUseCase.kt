package com.dbtech.messengerapp.domain.usecase

import com.dbtech.messengerapp.domain.repository.ChatRepository

class SendMessageUseCase(private val repository: ChatRepository){
    suspend operator fun invoke(senderId: String, content: String): Result<Unit> {
        val message = com.dbtech.messengerapp.domain.model.Message(
            id = "",
            senderId = senderId,
            content = content,
            timestamp = System.currentTimeMillis()
        )
        return repository.sendMessage(message)
    }
}