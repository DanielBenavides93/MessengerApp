package com.dbtech.messengerapp.domain.repository

import com.dbtech.messengerapp.domain.model.Message
import kotlinx.coroutines.flow.Flow

interface ChatRepository {
    suspend fun sendMessage(message: Message): Result<Unit>
    fun getMessages(): Flow<List<Message>>
}