# 📱 InternTaskApp

> A modern Android application built with Kotlin featuring secure authentication and Firebase integration

[![Android](https://img.shields.io/badge/Platform-Android-green.svg)](https://developer.android.com/)
[![Kotlin](https://img.shields.io/badge/Language-Kotlin-blue.svg)](https://kotlinlang.org/)
[![Firebase](https://img.shields.io/badge/Backend-Firebase-orange.svg)](https://firebase.google.com/)
[![API Level](https://img.shields.io/badge/API-24+-brightgreen.svg)](https://developer.android.com/guide/topics/manifest/uses-sdk-element.html#ApiLevels)

## ✨ Features

- 🔐 **Secure Authentication** - Sign up and sign in with email verification
- 📧 **Email OTP Verification** - Two-factor authentication for enhanced security  
- 🏠 **Dashboard** - Clean and intuitive user interface
- 👤 **User Profile** - Personalized user experience
- ⚙️ **Settings** - Customizable app preferences
- 🔥 **Firebase Integration** - Real-time database and authentication
- 📱 **Modern UI** - Material Design components with elegant styling

## 🛠️ Tech Stack

- **Language**: Kotlin
- **UI Framework**: Android Views with Material Design
- **Backend**: Firebase (Auth, Realtime Database, Analytics)
- **Email Service**: JavaMail API
- **Async Operations**: Kotlin Coroutines
- **Build System**: Gradle with Kotlin DSL

## 📋 Requirements

- Android Studio Arctic Fox or newer
- Android SDK API 24+ (Android 7.0)
- Java 11
- Firebase project setup

## 🚀 Getting Started

### 1. Clone the Repository
```bash
git clone <repository-url>
cd intern
```

### 2. Firebase Setup
1. Create a new Firebase project at [Firebase Console](https://console.firebase.google.com/)
2. Add your Android app to the project
3. Download `google-services.json` and place it in the `app/` directory
4. Enable Authentication and Realtime Database in Firebase Console

### 3. Build and Run
```bash
./gradlew assembleDebug
```

Or open the project in Android Studio and click **Run** ▶️

## 📱 App Structure

```
app/
├── src/main/
│   ├── java/com/example/interntaskapp/
│   │   ├── activities/          # Activity classes
│   │   ├── fragments/           # Fragment classes
│   │   └── utils/              # Utility classes
│   ├── res/
│   │   ├── layout/             # XML layouts
│   │   ├── drawable/           # UI resources
│   │   └── values/             # Colors, strings, themes
│   └── AndroidManifest.xml
└── build.gradle.kts
```

## 🎨 Screenshots

| Splash Screen | Sign In | Dashboard |
|:-------------:|:-------:|:---------:|
| ![Splash](https://via.placeholder.com/200x400/4CAF50/FFFFFF?text=Splash) | ![SignIn](https://via.placeholder.com/200x400/2196F3/FFFFFF?text=Sign+In) | ![Dashboard](https://via.placeholder.com/200x400/FF9800/FFFFFF?text=Dashboard) |

## 🔧 Configuration

### Email Configuration
Update email settings in your configuration:
```kotlin
// Configure SMTP settings for OTP emails
val emailConfig = EmailConfig(
    host = "smtp.gmail.com",
    port = 587,
    username = "your-email@gmail.com",
    password = "your-app-password"
)
```

### Firebase Configuration
Ensure your `google-services.json` is properly configured with:
- Authentication providers
- Realtime Database rules
- Analytics settings

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 👨‍💻 Developer

**Shubham Krishna**

- 📧 Email: [your-email@example.com](mailto:your-email@example.com)
- 💼 LinkedIn: [Your LinkedIn Profile](https://linkedin.com/in/yourprofile)
- 🐙 GitHub: [Your GitHub Profile](https://github.com/yourusername)

## 🙏 Acknowledgments

- Firebase team for excellent backend services
- Material Design team for beautiful UI components
- Android development community for continuous support

---

<div align="center">
  <p>Made with ❤️ for learning and development</p>
  <p>⭐ Star this repo if you found it helpful!</p>
</div>