# MessengerApp

Android app project written in **Kotlin** using **Jetpack Compose**.

## Overview (confirmed)
- Compose-based entry point (`ComponentActivity` + `setContent { ... }`).
- Uses a navigation layer (`AppNavigation()`).
- Gradle is configured with Firebase (BOM) and the Google Services plugin.
- Includes the Google Generative AI client dependency (configured in Gradle).

## Tech Stack (confirmed)
- Kotlin
- Gradle Kotlin DSL (`.kts`)
- Jetpack Compose + Material3
- Android SDK: `minSdk 24`, `targetSdk 35`, `compileSdk 35`

## Libraries (confirmed)
- **Firebase (BOM)**  
  - Firebase Auth  
  - Firebase Realtime Database
- **Koin** (`koin-android`, `koin-androidx-compose`)
- **DataStore Preferences**
- **AndroidX Navigation Compose**
- **Google Generative AI client** (`com.google.ai.client.generativeai`)
- Compose UI / Foundation / Runtime + Tooling

## Notes (confirmed)
- Versions and coordinates are managed via `gradle/libs.versions.toml` (Version Catalog).
- `README.md` in this repo is intentionally minimal and avoids assumptions about features/screens.

## How to run
1. Clone the repo
2. Open in Android Studio
3. Sync Gradle
4. Run the `app` configuration on a device/emulator

## TODO (intentionally minimal)
- Add screenshots / GIF demo
- Document screens and user flows once confirmed
