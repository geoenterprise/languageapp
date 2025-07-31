# Overview

This mobile app was created as part of my journey to become a better software engineer and mobile developer. The goal was to build a real-world, interactive Android application that helps language learners engage in Project-Based Learning (PBL), combining writing practice with pronunciation and instant AI feedback.

The app allows users to:
- Write short English responses to project-based prompts (like community improvement ideas).
- Record their voice to practice pronunciation.
- Get real-time writing feedback from an AI assistant (using OpenAI's GPT-3.5 API).
- Store and retrieve their answers with a Room database, even after the app is closed.

This is not a simple vocabulary app — it simulates real-world English tasks in a learner-centered way. My purpose is to expand this into a modern language learning tool with creative, project-based engagement and personalized AI feedback.

[Software Demo Video](https://youtu.be/ryoU3L19bJg)

# Development Environment

- IDE: Android Studio Hedgehog
- Build Tools: Gradle (Kotlin DSL)
- Emulator/Device: Android Emulator (API 24) and physical Android phone for testing
- Database: Room (Jetpack persistence library)
- AI Integration: OpenAI GPT-3.5 via HTTPS/OkHttp
- Media Components: MediaRecorder and MediaPlayer for microphone access

# Programming Language and Libraries

- Language: Kotlin
- Libraries Used:
    - Room Database (`androidx.room`)
    - OkHttp for HTTP requests
    - AndroidX core + AppCompat
    - Material Components for UI
    - MediaRecorder / MediaPlayer
    - AlertDialog + Toast UI Components

# Useful Websites

[Android Developer Guide](https://developer.android.com/)
[Room Persistence Library](https://developer.android.com/training/data-storage/room)
[OpenAI API Docs](https://platform.openai.com/docs/)
[Kotlin Android Fundamentals](https://developer.android.com/kotlin/first)
[Stack Overflow](https://stackoverflow.com/)
[OkHttp Documentation](https://square.github.io/okhttp/)
[YouTube Kotlin Tutorials](https://www.youtube.com/results?search_query=kotlin+android+app+tutorial)

# Future Work

Improve user interface design and theming across screens
Add more project categories and vocabulary modes
Create listening and reading-based challenges
Show history of user submissions with feedback
Add user login and cloud sync for personalized learning
Use AI to evaluate pronunciation recordings (future audio AI integration)
Add internationalization for multilingual interface
