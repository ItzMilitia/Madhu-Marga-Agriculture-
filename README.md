# Project No. 64: 🐝 Madhu-Marga — Digital Beekeeper's Diary

> *Madhu* (Sanskrit: मधु) — Honey &nbsp;|&nbsp; *Marga* (Sanskrit: मार्ग) — Path or Way

**Madhu-Marga** is an AI-guided Android application designed to empower beekeepers and farmers with smart hive management. Log daily observations from your apiary and receive intelligent, data-driven recommendations on when to harvest honey, when to split a colony, and how to keep your hives thriving.

---

## 📱 Screenshots

> <img width="720" height="1600" alt="WhatsApp Image 2026-05-05 at 11 55 53 PM" src="https://github.com/user-attachments/assets/d9ca259f-1b78-4fa8-a5c9-d32025c91b30" />
<img width="720" height="1600" alt="WhatsApp Imag2026-05-05 at 11 55 53 PM" src="https://github.com/user-attachments/assets/97bc8ed6-4637-4f2e-acfa-2b8692eec4f8" />
<img width="720" height="1600" alt="WhatsApp Image 2026-05-05 at 11 55 52 PM" src="https://github.com/user-attachments/assets/f02dca78-b805-4d75-88a9-5d20482b8e31" />




---

## ✨ Features

- **Observation Diary** — Log hive events in real time: queen sightings, honey flow status, activity levels, brood health, and more.
- **AI-Guided Analysis** — The app analyzes your logged observations and surfaces actionable tips tailored to your hive's current state.
- **Harvest Recommendations** — Get notified when conditions indicate the right time to collect honey.
- **Colony Split Alerts** — Receive guidance when your colony shows signs of being ready to split, preventing uncontrolled swarming.
- **Hive History** — Review past observations in a clean chronological diary view.
- **Offline-First** — Core logging works without an internet connection, synced to the cloud when available.

---

## 🛠️ Tech Stack

| Layer | Technology |
|---|---|
| Language | Kotlin |
| UI | Jetpack Compose |
| Architecture | MVVM |
| Local Database | Room (via Kotlin KAPT) |
| Backend / Sync | Firebase (Firestore / Auth) |
| Build System | Gradle with Kotlin DSL (`.kts`) |
| Min SDK | Android 8.0+ (API 26) |

---

## 🚀 Getting Started

### Prerequisites

- Android Studio **Hedgehog** (2023.1.1) or later
- JDK 17+
- A Firebase project (for cloud sync features)

### Setup

1. **Clone the repository**

   ```bash
   git clone https://github.com/ItzMilitia/Madhu-Marga-Agriculture-.git
   cd Madhu-Marga-Agriculture-
   ```

2. **Connect Firebase**

   - Go to the [Firebase Console](https://console.firebase.google.com/) and create a new project.
   - Add an Android app with your package name.
   - Download `google-services.json` and place it in the `/app` directory.

3. **Open in Android Studio**

   Open the project folder in Android Studio. Let Gradle sync complete automatically.

4. **Run the app**

   Select a device or emulator and click **Run ▶**.

---

## 📋 How to Use

1. **Add a Hive** — Create a profile for each of your bee colonies.
2. **Log Observations** — After each inspection, tap *New Entry* and record what you observed:
   - Queen seen / not seen
   - Honey flow activity
   - Colony activity level (High / Medium / Low)
   - Brood pattern
   - Pest or disease signs
3. **Read the Tips** — The app processes your entries and surfaces recommendations on the dashboard, such as:
   - *"Honey stores are high and capped — consider harvesting within the next 5–7 days."*
   - *"Queen cell detected in last 2 entries — colony may be preparing to swarm. Consider splitting."*
4. **Track Over Time** — Use the diary view to review hive history and spot patterns across seasons.

---

## 🗂️ Project Structure

```
Madhu-Marga-Agriculture-/
├── app/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/          # Kotlin source files (UI, ViewModels, Repositories)
│   │   │   ├── res/           # Layouts, drawables, strings
│   │   │   └── AndroidManifest.xml
│   │   └── test/              # Unit tests
│   ├── build.gradle.kts
│   └── google-services.json   # (Not committed — add your own)
├── build.gradle.kts           # Top-level build config
├── settings.gradle.kts
└── gradle.properties
```

---

## 🤝 Contributing

Contributions are welcome! If you'd like to improve Madhu-Marga:

1. Fork the repository.
2. Create a feature branch: `git checkout -b feature/your-feature-name`
3. Commit your changes: `git commit -m "Add your feature"`
4. Push to the branch: `git push origin feature/your-feature-name`
5. Open a Pull Request.

Please make sure your code follows the existing Kotlin style conventions and that new features include relevant tests where applicable.

---


## 📄 License

This project is open source. See the [LICENSE](LICENSE) file for details.

---

## 👨‍💻 Author

**ItzMilitia**
- GitHub: [@ItzMilitia](https://github.com/ItzMilitia)

---

> *"The hive speaks — Madhu-Marga helps you listen."* 🍯
