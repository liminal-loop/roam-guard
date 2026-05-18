# RoamGuard

[![CI](https://github.com/liminal-loop/roam-guard/actions/workflows/ci.yml/badge.svg)](https://github.com/liminal-loop/roam-guard/actions/workflows/ci.yml)
[![E2E Tests](https://github.com/liminal-loop/roam-guard/actions/workflows/e2e.yml/badge.svg)](https://github.com/liminal-loop/roam-guard/actions/workflows/e2e.yml)
[![Unit Coverage](https://img.shields.io/badge/unit_coverage-80%25-brightgreen)](https://github.com/liminal-loop/roam-guard/actions/workflows/ci.yml)
[![E2E Coverage](https://img.shields.io/badge/e2e_coverage-50%25-yellowgreen)](https://github.com/liminal-loop/roam-guard/actions/workflows/e2e.yml)
[![License](https://img.shields.io/badge/license-Apache%202.0-blue.svg)](LICENSE)
[![SBOM](https://img.shields.io/badge/sbom-available-blue)](https://github.com/liminal-loop/roam-guard/releases)

RoamGuard is an Android application that automatically protects you from unexpected roaming charges by intelligently managing cellular network connections. It monitors the device's network state in the background, detects when roaming occurs, and can automatically disconnect from unauthorized foreign networks, reconnect to your home network or whitelisted networks, or prompt you for confirmation.

It leverages privileged system access (root or Shizuku) when available for deeper network control, or falls back to monitoring-only mode.

## Architecture

```
┌───────────────────────────────────────────────────────────────┐
│                     :app  (UI Layer)                          │
│  Compose UI ← ViewModels ← Hilt DI                           │
│  Navigation, Theme, ForegroundService, WorkManager Worker     │
│  SystemControllerModule (root→Shizuku→monitoring fallback)    │
├───────────────────────────────────────────────────────────────┤
│                     :domain  (Domain Layer)                   │
│  Models, Repository interfaces, Use Cases                     │
│  Pure Kotlin — no Android dependencies                        │
├───────────────────────────────────────────────────────────────┤
│                     :data  (Data Layer)                       │
│  Repository impls (Room, DataStore, Network)                  │
│  Room DB: WhitelistDao, HomeCountryDao                        │
│  PreferencesDataStore, MccDatasetLoader                       │
├─────────────────┬─────────────────┬───────────┬───────────────┤
│    :common      │  :root-helper   │ :shizuku  │  :mcc-data    │
│  shared utils   │  root shell su  │  helper   │  static MCC/  │
│  SystemNetwork  │  RootSystem     │ Shizuku   │  MNC dataset  │
│  Controller     │  Controller     │ System    │               │
│  interface      │                 │ Controller│               │
└─────────────────┴─────────────────┴───────────┴───────────────┘
```

## Modules

| Module | Type | Responsibility |
|--------|------|----------------|
| `app` | Application | Entry point, Compose UI (Home, Onboarding, Whitelist, NetworkScan, Settings), Navigation, Hilt DI, `RoamingForegroundService`, `RoamingCheckWorker` |
| `domain` | Library | Use cases, repository interfaces, domain models — pure Kotlin |
| `data` | Library | Repository implementations (Room, DataStore, network state) |
| `common` | Library | `SystemNetworkController` interface, `MccCountryMapper`, `MonitoringOnlyController` fallback |
| `root-helper` | Library | Root shell access via `su`, `RootSystemController` |
| `shizuku-helper` | Library | Shizuku-privileged API via reflection, `ShizukuSystemController` |
| `mcc-data` | Library | Static MCC/MNC country lookup dataset (~200 entries) |
| `e2e-tests` | Test | End-to-end instrumentation tests on emulator |

## Tech Stack

| Layer | Technology |
|-------|-----------|
| Language | Kotlin 2.0.0 |
| UI | Jetpack Compose + Material 3 (BOM 2024.06.00) |
| Navigation | Compose Navigation 2.7.7 |
| Architecture | MVVM + Repository + UseCases (Clean Architecture) |
| DI | Dagger Hilt 2.51.1 |
| Database | Room 2.6.1 |
| Preferences | DataStore 1.1.1 |
| Background | WorkManager 2.9.0, LifecycleService Foreground Service |
| System Access | Shizuku SDK 13.1.5, Root shell `su` |
| Async | Kotlin Coroutines 1.8.1 |
| Build | Gradle 8.7 + AGP 8.5.0 + Kotlin DSL + Version Catalog (TOML) |
| Min SDK / Target | API 26 (Android 8.0) / API 34 |

## Privileged Access Chain

RoamGuard uses a priority-based fallback chain to obtain network control capabilities:

1. **Root** (`root-helper`): Executes `su -c` commands for manual network selection, data roaming toggle, and network state queries via `settings`, `cmd phone`, and `dumpsys`.
2. **Shizuku** (`shizuku-helper`): Uses reflection to call hidden `TelephonyManager` methods (`setNetworkSelectionModeManual`, `setDataRoamingEnabled`) via the Shizuku process.
3. **Monitoring-only** (`common`): No privileged access — `MonitoringOnlyController` no-ops all control operations. The app still monitors and alerts about roaming, but cannot automatically switch networks.

The `app` module's `SystemControllerModule` provides the appropriate implementation based on runtime detection.

## Roaming Decision Logic

```
IsRoaming? ──NO──→ Allowed
    │
   YES
    │
OnHomeMCC? ──YES──→ Allowed
    │
    NO
    │
OnWhitelistedMCC? ──YES──→ Allowed
    │
    NO
    │
NeedsConfirmation (user prompt)
```

## Quality Gates

| Gate | Threshold | Check |
|------|-----------|-------|
| Unit test coverage (line) | >= 80% | Kover verify |
| E2E test coverage (line) | >= 50% | JaCoCo report |
| License compliance | No GPL/AGPL/LGPL | License report scan |
| Lint | No errors | Android lint |

## Requirements

- Android 8.0 (API 26) or higher
- Java 17+ (development)
- Android SDK 34

## Building

```bash
./gradlew assembleDebug
```

## Testing

```bash
# Unit tests with coverage
./gradlew testDebugUnitTest koverXmlReport koverVerify

# E2E tests (requires emulator with Google APIs API 34, x86_64)
./gradlew :e2e-tests:connectedCheck

# Lint
./gradlew lintDebug
```

### Network Simulation (E2E)

```bash
# Simulate roaming on emulator
adb shell setprop gsm.operator.isroaming "true"
adb shell setprop gsm.operator.alpha "Test Network"
adb shell setprop gsm.operator.numeric "310260"
```

Coverage: **Kover** for unit tests, **JaCoCo** for E2E tests. Results converted to CTRF format.

## CI/CD

Five GitHub Actions workflows:

| Workflow | Trigger | Purpose |
|----------|---------|---------|
| `ci.yml` | Push/PR to `main` | Build, lint, unit tests, coverage, license check, SBOM |
| `e2e.yml` | Push/PR to `main` | E2E tests on emulator, CTRF report, JaCoCo coverage |
| `release.yml` | Tag `v*` | Signed release APK, SBOM, GitHub Release |
| `opencode.yml` | Issue/PR comment `/oc` or `/opencode` | AI-assisted development via OpenCode |
| `ci-failure-handler.yml` | On CI/E2E failure | Auto-triggers fix attempts via `/opencode fix` |

## Agent-Driven Development

RoamGuard uses a multi-agent system defined in [AGENTS.md](AGENTS.md) with 7 specialized agents collaborating via GitHub Issues and PRs:

- **android-architect** — Project structure, clean architecture setup
- **android-developer** — Feature implementation (Compose, ViewModels)
- **sys-integration-specialist** — Root/Shizuku low-level system access
- **qa-engineer** — Tests, coverage, CTRF reporting
- **devops-engineer** — CI/CD, SBOM, release automation
- **dependency-updater** — Automated dependency updates
- **docs-translator** — Multilingual docs and strings (EN/DE)

## License

Apache License 2.0
