package com.dbtech.messengerapp

import android.app.Application
import com.dbtech.messengerapp.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MessengerApp : Application(){
    override fun onCreate() {
        super.onCreate()

        //Se inicia Koin (DI)
        startKoin {
            androidContext(this@MessengerApp) //se da el contexto de Android para las dependencias
            modules (appModule) //se carga el module de dependencias de AppModule.kt
        }
    }
}