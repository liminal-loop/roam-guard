# RoamGuard

Automatic roaming guard for Android — protects against unexpected roaming charges by intelligently managing network connections.

[![Build & Test](https://github.com/liminal-loop/roam-guard/actions/workflows/build.yml/badge.svg)](https://github.com/liminal-loop/roam-guard/actions/workflows/build.yml)
[![Lint](https://github.com/liminal-loop/roam-guard/actions/workflows/lint.yml/badge.svg)](https://github.com/liminal-loop/roam-guard/actions/workflows/lint.yml)
[![Coverage](https://img.shields.io/badge/coverage-pending-blue)](https://github.com/liminal-loop/roam-guard/actions/workflows/build.yml)
[![License](https://img.shields.io/badge/license-Apache%202.0-blue.svg)](LICENSE)

## Tech Stack

| Layer | Technology |
|---|---|
| UI | Jetpack Compose (Material 3) |
| Navigation | Compose Navigation |
| Architecture | MVVM + Repository pattern |
| DI | Dagger Hilt |
| Database | Room |
| Preferences | DataStore |
| Background | WorkManager, Foreground Service |
| System | Shizuku API, Root (su) |
| Testing | JUnit5, MockK, Compose UI Test |

## Modules

| Module | Responsibility |
|---|---|
| `app` | Application entry point, UI screens, navigation |
| `domain` | Use cases, repository interfaces, domain models |
| `data` | Repository implementations, Room DB, DataStore |
| `common` | Shared utilities and constants |
| `root-helper` | Root shell command execution |
| `shizuku-helper` | Shizuku-privileged API access |
| `mcc-data` | MCC/MNC country lookup dataset |

## License

Apache License 2.0
