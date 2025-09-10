package com.dbtech.messengerapp.di

import com.dbtech.messengerapp.data.local.DataStoreManager
import com.dbtech.messengerapp.data.repository.AuthRepositoryImpl
import com.dbtech.messengerapp.data.repository.ChatRepositoryImpl
import com.dbtech.messengerapp.domain.repository.AuthRepository
import com.dbtech.messengerapp.domain.repository.ChatRepository
import com.dbtech.messengerapp.domain.usecase.GetMessagesUseCase
import com.dbtech.messengerapp.domain.usecase.LoginUseCase
import com.dbtech.messengerapp.domain.usecase.LogoutUseCase
import com.dbtech.messengerapp.domain.usecase.RegisterUseCase
import com.dbtech.messengerapp.domain.usecase.SendMessageUseCase
import com.dbtech.messengerapp.presentation.chat.ChatViewModel
import com.dbtech.messengerapp.presentation.login.AuthViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    //DataStore
    single { DataStoreManager(androidContext()) }

    //Firebase
    single { FirebaseAuth.getInstance() }
    single { FirebaseDatabase.getInstance() }

    //dependencias de data (repositorios)
    single<AuthRepository> { AuthRepositoryImpl(get(), get()) }
    single<ChatRepository> { ChatRepositoryImpl(get()) }

    //dependencias de presentation (ViewModels)
    viewModel { AuthViewModel(get(), get(), get(), get(), get()) } //Inyecta AuthRepo
    viewModel { ChatViewModel(get(), get()) } //Inyecta ChatRepo

    //casos de uso
    factory { LoginUseCase(get()) }
    factory { RegisterUseCase(get()) }
    factory { LogoutUseCase (get())}
    factory { SendMessageUseCase(get()) }
    factory { GetMessagesUseCase(get()) }
}