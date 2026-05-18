# RoamGuard

Automatic roaming guard for Android — protects against unexpected roaming charges by intelligently managing network connections.

[![CI](https://github.com/liminal-loop/roam-guard/actions/workflows/ci.yml/badge.svg)](https://github.com/liminal-loop/roam-guard/actions/workflows/ci.yml)
[![E2E Tests](https://github.com/liminal-loop/roam-guard/actions/workflows/e2e.yml/badge.svg)](https://github.com/liminal-loop/roam-guard/actions/workflows/e2e.yml)
[![Unit Coverage](https://img.shields.io/badge/unit_coverage-80%25-brightgreen)](https://github.com/liminal-loop/roam-guard/actions/workflows/ci.yml)
[![E2E Coverage](https://img.shields.io/badge/e2e_coverage-50%25-yellowgreen)](https://github.com/liminal-loop/roam-guard/actions/workflows/e2e.yml)
[![License Compliance](https://img.shields.io/badge/license_compliance-passing-brightgreen)](https://github.com/liminal-loop/roam-guard/actions/workflows/ci.yml)
[![SBOM](https://img.shields.io/badge/sbom-available-blue)](https://github.com/liminal-loop/roam-guard/releases)
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

## System Requirements

- Android 8.0 (API 26) or higher
- Java 17+ (for development)
- Android Gradle Plugin 8.5+

## Running Tests

```bash
# Unit tests with coverage
./gradlew testDebugUnitTest koverXmlReport

# E2E tests (requires emulator)
./gradlew :e2e-tests:connectedCheck

# Lint
./gradlew lintDebug
```

### Simulation Environment

E2E tests use an Android emulator with Google APIs (API 34, x86_64). Network conditions can be simulated via shell commands:

```bash
# Simulate roaming
adb shell setprop gsm.operator.isroaming "true"
adb shell setprop gsm.operator.alpha "Test Network"
adb shell setprop gsm.operator.numeric "310260"
```

Coverage is collected via JaCoCo for E2E tests and Kover for unit tests.

## Quality Gates

| Gate | Threshold | Check |
|---|---|---|
| Unit test coverage (line) | >= 80% | Kover verify |
| E2E test coverage (line) | >= 50% | JaCoCo report |
| License compliance | No GPL/AGPL/LGPL allowed | License report scan |
| Lint | No errors | Android lint |

## License

Apache License 2.0
