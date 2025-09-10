package com.dbtech.messengerapp.presentation.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dbtech.messengerapp.data.local.DataStoreManager
import com.dbtech.messengerapp.domain.repository.AuthRepository
import com.dbtech.messengerapp.domain.usecase.LoginUseCase
import com.dbtech.messengerapp.domain.usecase.LogoutUseCase
import com.dbtech.messengerapp.domain.usecase.RegisterUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch


class AuthViewModel(
    private val loginUseCase: LoginUseCase,
    private val registerUseCase: RegisterUseCase,
    private val logoutUseCase: LogoutUseCase,
    private val dataStore: DataStoreManager,
    private val authRepository: AuthRepository

) : ViewModel() {
    private val _uiState = MutableStateFlow(AuthUiState())
    val uiState: StateFlow<AuthUiState> = _uiState

    private val _email = MutableStateFlow("")
    val email: StateFlow<String> = _email.asStateFlow()

    private val _password = MutableStateFlow("")
    val password: StateFlow<String> = _password.asStateFlow()

    fun onEmailChanged(newEmail: String){_email.value = newEmail}
    fun onPasswordChanged(newPassword: String){_password.value = newPassword}

    fun login(email: String, password: String) {
        viewModelScope.launch {
            _uiState.value = AuthUiState(isLoading = true)
            val result = loginUseCase(email, password)
            _uiState.value = if (result.isSuccess) {
                dataStore.setLoggedIn(true)
                AuthUiState(isSuccess = true)
            } else {
                AuthUiState(errorMessage = result.exceptionOrNull()?.message)
            }
        }
    }

    fun register(email: String, password: String) {
        viewModelScope.launch {
            _uiState.value = AuthUiState(isLoading = true)
            val result = registerUseCase(email, password)
            _uiState.value = if (result.isSuccess) {
                dataStore.setLoggedIn(true) // guardamos sesión
                AuthUiState(isSuccess = true)
            } else {
                AuthUiState(errorMessage = result.exceptionOrNull()?.message)
            }
        }
    }

    fun logout() {
        viewModelScope.launch {
            _uiState.value = AuthUiState(isLoading = true)
            val result = logoutUseCase()
            _uiState.value = if (result.isSuccess) {
                dataStore.setLoggedIn(false) // limpiamos sesión
                AuthUiState(isSuccess = true)
            } else {
                AuthUiState(errorMessage = result.exceptionOrNull()?.message)
            }
        }
    }
    fun currentUserId(): String? = authRepository.currentUserId()
    // Observamos si hay sesión activa
    fun isUserLoggedIn(): Flow<Boolean> = dataStore.isLoggedIn()
}


data class AuthUiState(
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val errorMessage: String? = null
)