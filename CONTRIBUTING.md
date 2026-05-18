# Contributing to RoamGuard

## Dependency Updates

This project uses [Renovate](https://renovatebot.com) to keep dependencies up-to-date automatically.

### How it works

- Renovate scans `gradle/libs.versions.toml` daily and opens pull requests for outdated dependencies.
- Dependencies are grouped by category (Compose, Hilt, Room, testing, etc.) to reduce noise.
- **Minor and patch updates** are auto-merged after all CI checks pass.
- **Major updates** require manual approval via the Renovate Dashboard — they are not auto-merged.

### CI pipeline for dependency PRs

Every Renovate PR triggers the full CI pipeline:
1. `ci.yml` — lint, build, unit tests, coverage verification, license compliance, SBOM generation.
2. `e2e.yml` — E2E instrumented tests on an emulator.

All checks must pass before auto-merge (for minor/patch) or before manual merge (for major).

### Reviewing a dependency update

1. Check the Renovate PR description for changelog links and release notes.
2. Verify that CI passes (unit tests, E2E tests, lint, license scan).
3. For major updates, review breaking changes and coordinate with the team before approving.
4. Merge once all checks are green.

### Manual dependency updates

You can also update dependencies manually:
1. Edit version references in `gradle/libs.versions.toml`.
2. Run `./gradlew testDebugUnitTest` to verify nothing is broken.
3. Commit and push — Renovate will not override manual changes.

### Renovate configuration

The Renovate configuration is in `renovate.json` at the project root. See the [Renovate docs](https://docs.renovatebot.com) for available options.

## Development Setup

1. Clone the repository.
2. Open in Android Studio (or IntelliJ with the Android plugin).
3. Run `./gradlew assembleDebug` to verify the build.
4. Run `./gradlew testDebugUnitTest` to run unit tests.

## Submitting Changes

1. Create a feature branch from `main`.
2. Make your changes with clear commit messages.
3. Ensure all tests pass and lint is clean.
4. Open a pull request against `main`.

## Code Style

- Follow Kotlin coding conventions.
- Use meaningful variable/function names.
- Keep functions small and focused.
- Write tests for new functionality.
