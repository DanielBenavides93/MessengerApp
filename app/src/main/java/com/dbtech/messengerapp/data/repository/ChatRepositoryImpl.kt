package com.dbtech.messengerapp.data.repository

import android.util.Log
import com.dbtech.messengerapp.domain.repository.ChatRepository
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import com.dbtech.messengerapp.domain.model.Message


class ChatRepositoryImpl(

    private val firebaseDatabase: FirebaseDatabase

) : ChatRepository {

    private val chatRoom = "global"

    override suspend fun sendMessage(message: Message): Result<Unit> {
        return kotlin.runCatching {
            val ref =
                firebaseDatabase.reference.child("chats").child(chatRoom).child("messages").push()
            val msgWithId = message.copy(id = ref.key ?: "")
            Log.d("ChatRepositoryImpl", "Enviando mensaje: $msgWithId")
            ref.setValue(msgWithId).await()
            Log.d("ChatRepositoryImpl", "Mensaje enviado correctamente con ID: ${msgWithId.id}")
            Unit
        }.onFailure { e ->
            Log.e("ChatRepositoryImpl", "Error al enviar mensaje", e)
        }
    }

    override fun getMessages(): Flow<List<Message>> = callbackFlow {
        val ref = firebaseDatabase.reference.child("chats").child(chatRoom).child("messages")
        val listener = ref.orderByChild("timestamp")
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val messages =
                        snapshot.children.mapNotNull { snap -> snap.getValue(Message::class.java) }
                    Log.d("ChatRepositoryImpl", "Mensajes recibidos: ${messages.size}")
                    trySend(messages)
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.e("ChatRepositoryImpl", "Error al escuchar mensajes: ${error.message}")
                    close(error.toException())
                }
            })

        awaitClose {
            Log.d("ChatRepositoryImpl", "Listener de mensajes removido")
            ref.removeEventListener(listener) }
    }
}