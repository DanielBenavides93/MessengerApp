package com.dbtech.messengerapp.presentation.chat

import androidx.compose.foundation.background
import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.dbtech.messengerapp.domain.model.Message
import com.dbtech.messengerapp.presentation.login.AuthViewModel
import org.koin.androidx.compose.koinViewModel
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatScreen(
    chatViewModel: ChatViewModel = koinViewModel(),
    authViewModel: AuthViewModel = koinViewModel(),
    onLogout: () -> Unit // callback para volver al Login
) {
    val uiState by chatViewModel.uiState.collectAsStateWithLifecycle()
    val authUiState by authViewModel.uiState.collectAsStateWithLifecycle()

    val newMessage by chatViewModel.newMessage.collectAsStateWithLifecycle()

    val listState = rememberLazyListState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Messenger") },
                actions = {
                    TextButton(onClick = { authViewModel.logout() }) {// aquí llamamos al logout del ViewModel
                        Text("Logout", color = MaterialTheme.colorScheme.onPrimary)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(8.dp)
        ) {
            // Lista de mensajes
            LazyColumn(
                state = listState,
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
            ) {
                items(uiState.messages) { message ->
                    MessageBubble(
                        message = message,
                        isOwnMessage = message.senderId == authViewModel.currentUserId())
                }
            }

            Spacer(Modifier.height(8.dp))

            // Caja de enviar mensaje
            Row {
                TextField(
                    value = newMessage,
                    onValueChange = { chatViewModel.onMessageChanged(it) },
                    modifier = Modifier.weight(1f)
                )
                Button(
                    onClick = {
                        chatViewModel.sendMessage(authViewModel.currentUserId() ?: "unknown")
                    }
                ) {
                    Text("Send")
                }
            }
        }
    }

    // Si logout fue exitoso → volvemos al LoginScreen
    LaunchedEffect(authUiState.isSuccess) {
        if (authUiState.isSuccess && !authUiState.isLoading) {
            onLogout()
        }
    }
    // Scroll automático al último mensaje
    LaunchedEffect(uiState.messages.size) {
        if (uiState.messages.isNotEmpty()) {
            listState.animateScrollToItem(uiState.messages.lastIndex)
        }
    }
}

@Composable
fun MessageBubble(
    message: Message,
    isOwnMessage: Boolean
) {
    val bubbleColor = if (isOwnMessage) Color(0xFFDCF8C6) else Color.White
    val alignment = if (isOwnMessage) Alignment.End else Alignment.Start
    val time = remember(message.timestamp) {
        SimpleDateFormat("HH:mm", Locale.getDefault()).format(Date(message.timestamp))
    }

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = alignment
    ) {
        Box(
            modifier = Modifier
                .background(bubbleColor, shape = MaterialTheme.shapes.medium)
                .padding(10.dp)
        ) {
            Column {
                Text(message.content)
                Spacer(Modifier.height(4.dp))
                Text(
                    text = time,
                    style = MaterialTheme.typography.labelSmall,
                    color = Color.Gray,
                    textAlign = TextAlign.End,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}
