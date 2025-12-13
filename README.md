# MessengerApp

Android app project written in **Kotlin** using **Jetpack Compose**.

## Overview
- Compose-based entry point (`ComponentActivity` + `setContent { ... }`).
- Uses a navigation layer (`AppNavigation()`).
- Gradle is configured with Firebase (BOM) and the Google Services plugin.
- Includes the Google Generative AI client dependency (configured in Gradle).

## Tech Stack
- Kotlin
- Gradle Kotlin DSL (`.kts`)
- Jetpack Compose + Material3
- Android SDK: `minSdk 24`, `targetSdk 35`, `compileSdk 35`

## Libraries
- **Firebase (BOM)**  
  - Firebase Auth  
  - Firebase Realtime Database
- **Koin** (`koin-android`, `koin-androidx-compose`)
- **DataStore Preferences**
- **AndroidX Navigation Compose**
- **Google Generative AI client** (`com.google.ai.client.generativeai`)
- Compose UI / Foundation / Runtime + Tooling
