package com.dbtech.messengerapp.presentation.chat

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dbtech.messengerapp.domain.model.Message
import com.dbtech.messengerapp.domain.usecase.GetMessagesUseCase
import com.dbtech.messengerapp.domain.usecase.SendMessageUseCase
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class ChatViewModel(
    private val getMessagesUseCase: GetMessagesUseCase,
    private val sendMessageUseCase: SendMessageUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(ChatUiState())
    val uiState: StateFlow<ChatUiState> = _uiState

    private val _newMessage = MutableStateFlow("")
    val newMessage: StateFlow<String> = _newMessage

    init { observeMessages()
    newMessage
        .onEach {  }
        .launchIn(viewModelScope)
    }

    private fun observeMessages() {
        getMessagesUseCase()
            .onStart {
                Log.d("ChatViewModel", "Cargando mensajes desde Firebase...")
            _uiState.value = _uiState.value.copy(isLoading = true)
        }
            .catch{e->
                Log.e("ChatViewModel", "Error al obtener mensajes", e)
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                 errorMessage = e.message ?: "Error al cargar mensajes"
                    )
                }
            .onEach{
                messages ->
                Log.d("ChatViewModel", "Mensajes recibidos: ${messages.size}")
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    messages = messages,
                    errorMessage = null
                )
            }
            .launchIn(viewModelScope)
        }

    fun onMessageChanged(value: String){
        _newMessage.value = value
        Log.d("ChatViewModel", "Nuevo mensaje escrito: $value")
    }

    fun sendMessage(senderId: String) {
        val content = _newMessage.value
        if(content.isBlank()){
            Log.w("ChatViewModel", "No se envió el mensaje porque está vacío"); return}
        Log.d("ChatViewModel", "Enviando mensaje: '$content' de $senderId")
        _uiState.value = _uiState.value.copy(isLoading = true)

        viewModelScope.launch {
            val result = sendMessageUseCase(senderId, content)
                _uiState.value = if (result.isSuccess) {
                    Log.d("ChatViewModel", "Mensaje enviado correctamente")
                    _uiState.value.copy(isLoading = false)
                } else {
                    Log.e("ChatViewModel", "Error al enviar mensaje: ${result.exceptionOrNull()?.message}")
                    _uiState.value.copy(
                        isLoading = false,
                        errorMessage = result.exceptionOrNull()?.message)
                }
            _newMessage.value = ""
            }
        }
    }

data class ChatUiState(
    val isLoading: Boolean = false,
    val messages: List<Message> = emptyList(),
    val errorMessage: String? = null
)