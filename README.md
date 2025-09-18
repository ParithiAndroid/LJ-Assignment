# Loyalty Juggernaut Assignment Github Repos App

A modern Android application that fetches, caches, and displays trending Swift repositories from GitHub. Built with **Kotlin**, **Jetpack Compose**, **Hilt**, **Room**, and **Retrofit**.

---

## Features

- Fetch trending Swift repositories using the GitHub REST API.
- Offline caching with Room Database.
- Search repositories by name, owner, or ID.
- Toggle between Light and Dark themes.
- Smooth UI with animations and placeholder images.
- Error handling and retry mechanism for network issues.
- Open repository in WebView on item click.

---

## Screenshots

| Light Mode | Dark Mode |
|------------|-----------|
| ![light](screenshots/light.jpg) | ![dark](screenshots/dark.jpg) |

---

## Architecture

The app follows **MVVM (Model-View-ViewModel)** architecture with Hilt for dependency injection.

+------------------+
| UI (Compose) |
+------------------+
|
v
+------------------+
| ViewModel |
| (RepoViewModel) |
+------------------+
|
v
+------------------+
| Repository |
| (ReposRepository)|
+------------------+
|
v
+------------------+
| API / Database |
+------------------+

---

## Key Components

### **Networking**

- **Retrofit** for API calls.
- **OkHttp Interceptor** to check internet connectivity.
- Handles network exceptions and API errors gracefully.

### **Caching**

- **Room Database** stores repository data locally.
- Reduces network load and allows offline access.

### **UI**

- **Jetpack Compose** for declarative UI.
- **LazyColumn** for repository listing.
- **Card** components for each repo with fade-in images.
- **Search Bar** with animation.
- **WebView** to view repository details.

### **Theme**

- Custom **Light** and **Dark** themes.
- User can toggle themes dynamically.
- App theme independent of system theme.

---

