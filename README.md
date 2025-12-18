# MessengerApp

An Android messaging app written in **Kotlin** using **Jetpack Compose**.  
It supports **Email/Password authentication** with **Firebase Auth** and a simple **global chat room** powered by **Firebase Realtime Database**.


## Technical Sheet

### UI & Navigation
- **Jetpack Compose** (Material3)
- **Navigation Compose**
- App navigation has 2 routes:
  - `login` → authentication screen (login/register)
  - `chat` → global chat screen
- Logout returns the user to `login`


## Features
### Authentication
- Register with **Email + Password** (Firebase Auth)
- Login with **Email + Password** (Firebase Auth)
- Logout

### Messaging (Global Room)
- A single global room is used (hardcoded): **`"global"`**
- Messages are stored under the path:
  - `chats/global/messages`
- Messages are retrieved in **real time** and **ordered by timestamp**
- Sending a message uses a `push()`-style generated key (auto ID)

### Local Session Flag
- A local flag is saved using **DataStore Preferences**
- Preferences file name: **`messenger_prefs`**
- Key: **`is_logged_in`**


## Architecture
This project follows a clean separation by packages (Clean Architecture style) and uses **MVVM**.

### Layers / Responsibilities
- **domain/**
  - Models (business entities)
  - Repository interfaces
  - Use cases
- **data/**
  - Firebase repository implementations (Auth + Realtime Database)
  - DataStore manager (session flag)
- **presentation/**
  - Compose screens
  - ViewModels
  - Navigation

### MVVM + State
- ViewModels expose state using **StateFlow**
- UI observes and reacts to ViewModel state

### Use Cases (present)
- `LoginUseCase`
- `RegisterUseCase`
- `LogoutUseCase`
- `SendMessageUseCase`
- `GetMessagesUseCase`


## Firebase Details

### Firebase dependencies (confirmed)
- Firebase **BOM**
- Firebase **Auth**
- Firebase **Realtime Database**

### Database structure used
