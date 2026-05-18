\# AGENTS.md for roam-guard



\## Project Overview

Android app that prevents unwanted roaming charges by controlling which mobile networks the device can connect to outside the user's home country.



\*\*Core idea:\*\*  

\- User sets a home country (Home Location).  

\- User maintains a whitelist of allowed countries.  

\- Whenever the phone tries to connect to a network outside the home country, the app checks the whitelist.  

&#x20; - If the country is allowed → connection proceeds silently.  

&#x20; - If not allowed → a dialog asks the user for permission. Denying blocks the connection and temporarily disables data roaming until an allowed network is available.



\## Repository Structure

\- `app/` – Main Android application module (UI, DI, navigation)

\- `domain/` – Use cases, repository interfaces, models (MCC, Country)

\- `data/` – Repository implementations, Room DB, DataStore, MCC data source

\- `common/` – Extensions, constants, utility functions

\- `root-helper/` – Optional module for rooted device operations (network selection, roaming toggle via `su`)

\- `shizuku-helper/` – Optional module for Shizuku-based system API access

\- `mcc-data/` – JSON file mapping MCCs to country names, ISO codes, and flag resources



\## Technology Stack

\- \*\*Language:\*\* Kotlin (100%)

\- \*\*UI:\*\* Jetpack Compose (Material 3, dark mode support)

\- \*\*Architecture:\*\* MVVM + Repository pattern, Hilt for DI

\- \*\*Async:\*\* Coroutines + Flow

\- \*\*Persistence:\*\* Room (whitelist, home country, permanent user decisions), DataStore (preferences)

\- \*\*Network monitoring:\*\* `TelephonyManager`, `PhoneStateListener`, `NetworkScan` (API 28+)

\- \*\*System interaction:\*\*  

&#x20; - Root: `Runtime.getRuntime().exec("su")` for `cmd phone`, `settings`  

&#x20; - Shizuku: `ShizukuBinder`, reflection to hidden APIs (`setNetworkSelectionModeManual`, `setDataRoamingEnabled`)

\- \*\*Background work:\*\* Foreground service with ongoing notification, WorkManager for periodic scans

\- \*\*Testing:\*\* JUnit5, MockK, Compose UI testing, fake TelephonyManager



\## Key Functional Requirements



\### 1. Onboarding \& Configuration

\- First launch: select home country from a searchable list (MCC-based).

\- Configure whitelist: multi-select countries (by name/flag). Default: home country plus any explicitly added.

\- Option to update MCC dataset from a GitHub release asset.



\### 2. Interactive Roaming Control

\- \*\*Detection:\*\* Monitor `TelephonyManager.isNetworkRoaming()` and current MCC.

\- \*\*Home network?\*\* → never block or prompt.

\- \*\*Roaming \& MCC in whitelist?\*\* → allow silently.

\- \*\*Roaming \& MCC NOT in whitelist?\*\* → Show dialog:

&#x20; - "The device wants to connect to a network in \[Country]. Allow this connection?"

&#x20; - Buttons: \*Allow\* / \*Deny\*

&#x20; - Checkbox: "Always allow for this country" (adds to whitelist permanently).

\- \*\*Dialog timing:\*\* The app must prevent the device from actually registering on the disallowed network until the user responds. Achieve this by forcing manual network selection mode or temporarily disabling data roaming.



\### 3. Deny Handling

\- If user denies: immediately scan for available networks.  

&#x20; - Prefer a network from the home country or whitelist.  

&#x20; - If none found, disable data roaming (`ConnectivityManager.setDataRoamingEnabled(false)` or via root/Shizuku).  

\- Periodically re-check; re-enable roaming when an allowed network appears.



\### 4. Manual Network Selection

\- Dedicated screen listing currently visible networks with country flag, name, MCC/MNC, and whitelist status.

\- User can tap any network to trigger the same allow/deny flow.



\### 5. Persistent Decisions

\- "Always allow" decisions are stored in Room (added to whitelist) and can be reviewed/revoked in settings.

\- Temporary denies are not persisted; the app asks again next time.



\## Non-Functional Requirements

\- \*\*Permissions:\*\*  

&#x20; - `ACCESS\_FINE\_LOCATION` (for network scanning on Android 9+)  

&#x20; - `READ\_PHONE\_STATE` (to read network info)  

&#x20; - `FOREGROUND\_SERVICE`  

&#x20; - Root or Shizuku (explained clearly to user).  

\- \*\*Privacy:\*\* No analytics, no network calls except optional MCC data fetch. All data stays on device.

\- \*\*Localization:\*\* English and German at minimum.

\- \*\*Battery:\*\* Adaptive scan intervals (15–60 seconds), paused when screen off and connected to allowed network.

\- \*\*Fallback mode:\*\* If required system access is missing, app runs in monitoring-only mode with manual recommendations.



\## Coding Standards \& Best Practices

\- Follow Kotlin coding conventions and Android Kotlin style guide.

\- Use `ViewModel` with `StateFlow` for UI state.

\- Inject dependencies with Hilt; provide test doubles for all external services.

\- Keep modules decoupled; domain layer must have zero Android dependencies.

\- Write unit tests for all ViewModels and Use Cases, integration tests for repositories, and UI tests for critical flows.

\- Use `detekt` and `ktlint` for static analysis (configured in CI).

\- Commit messages follow conventional commits (`feat:`, `fix:`, `docs:`, `test:`).



\## CI/CD (GitHub Actions)

\- \*\*Build \& Test:\*\* Trigger on push to `main` and pull requests. Run `./gradlew test lintDebug`.

\- \*\*Release:\*\* When a tag like `v\*` is pushed, build signed APK and create a GitHub Release with the APK and changelog.

\- \*\*MCC Data Update:\*\* Scheduled workflow that checks for updates to `mcc-data/countries.json` and opens a PR if changed.



\## Agent Instructions

You are an autonomous developer agent. Your tasks are defined in GitHub Issues.



\- Read this AGENTS.md first to understand the entire project.

\- When asked to implement a feature, create a detailed plan, then produce the necessary code changes in a new branch and open a pull request.

\- Always add or update unit tests for the code you change.

\- Follow the project structure and patterns described above.

\- Do not introduce new technologies or modules without explicit approval.

\- If you encounter unclear requirements, ask clarifying questions in the issue comments before proceeding.

\- Keep the README.md up to date with build instructions and feature summary.



Initial Issue Template for this project:

\*\*Title:\*\* Implement core interactive roaming control

\*\*Description:\*\* As described in AGENTS.md, implement the complete interactive roaming whitelist system including onboarding, background service, dialog prompts, allow/deny logic, manual network selection, and settings. Set up the initial project structure with all modules, Hilt, Room, Compose, and the GitHub Actions CI pipeline.

