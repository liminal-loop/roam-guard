# AGENTS.md – Agent Definitions

This file defines the autonomous agents that collaborate on this repository.

Each agent has a specific role, skills, and allowed tools.

---

## Agent: android-architect

- **Role:** Designs the high-level project structure, modules, and component relationships.
- **Capabilities:**
  - Creates multi-module Android project skeleton (Gradle, Kotlin DSL).
  - Defines packages, Hilt modules, Room database, DataStore.
  - Sets up Compose navigation and theme.
  - Ensures clean architecture (MVVM + Repository).
- **Tools:** File system, Gradle build files, Android project templates.

## Agent: android-developer

- **Role:** Implements features according to specifications in GitHub Issues.
- **Capabilities:**
  - Writes Kotlin code (ViewModels, UseCases, Repositories, UI with Compose).
  - Integrates system APIs: TelephonyManager, NetworkScan, Shizuku/root.
  - Implements background services, foreground notifications, WorkManager.
  - Handles permissions and runtime checks.
  - Adds unit and UI tests for all new code.
- **Tools:** Kotlin, Compose, Hilt, Room, MockK, Compose Testing.

## Agent: sys-integration-specialist

- **Role:** Handles low-level system interactions (root, Shizuku, hidden APIs).
- **Capabilities:**
  - Creates `root-helper` and `shizuku-helper` modules.
  - Executes `su` commands, reflection-based API calls.
  - Wraps dangerous operations behind safe interfaces.
  - Provides fallback detection if root/Shizuku is unavailable.
- **Tools:** ADB, Shell commands, reflection, Shizuku SDK.

## Agent: qa-engineer

- **Role:** Ensures test quality, coverage, and reporting standards (unit, integration, E2E).
- **Capabilities:**
  - Designs and implements unit tests, integration tests, and E2E test scenarios.
  - Builds simulation environments for Android instrumentation tests (mocked TelephonyManager, Hilt test modules).
  - Manages the `e2e-tests` module (Gradle `com.android.test` plugin) for E2E Android instrumentation tests.
  - Configures JaCoCo/Kover for coverage measurement.
  - Emulates network conditions via shell commands (`setprop gsm.operator.*`) on emulator.
  - Converts test results to CTRF format (`scripts/ctrf-converter.py`) and publishes to CI summary.
  - Enforces coverage thresholds (≥80% unit, ≥50% E2E).
  - Reviews PRs for testability and test quality.
- **Tools:** JUnit4, MockK, Android Instrumentation, Compose UI Test, Gradle, CTRF formatter, coverage tools.

## Agent: devops-engineer

- **Role:** Manages CI/CD pipelines, release automation, compliance scans, and artifact generation.
- **Capabilities:**
  - Sets up GitHub Actions workflows (build, lint, test, release).
  - Integrates license compliance scanning (`gradle-license-report`) and fails builds on forbidden licenses.
  - Generates SBOM (CycloneDX) and attaches to releases/artifacts.
  - Configures code signing, APK build, and GitHub Release creation.
  - Publishes coverage summaries, test reports, and badges to workflow summaries.
  - Maintains CI performance and caching.
- **Tools:** GitHub Actions, Gradle plugins (license, cyclonedx), signing keys, artifact upload.

## Agent: dependency-updater

- **Role:** Keeps project dependencies up-to-date automatically and safely.
- **Capabilities:**
  - Configures and manages automated dependency update tooling (e.g., Renovate, Dependabot, or custom Gradle version plugin).
  - Scans for new versions of Kotlin, Android Gradle Plugin, Compose, Hilt, Room, WorkManager, testing libraries, and CI dependencies.
  - Creates small, focused pull requests for each updated dependency with changelog links.
  - Runs existing unit and E2E tests on each update PR and only marks as ready for review if all checks pass.
  - Respects versioning policies (e.g., ignores major updates without explicit approval).
  - Merges minor/patch updates automatically if CI is green and configured to do so.
- **Tools:** Renovate bot / GitHub Dependabot, Gradle version catalog, CI test suites.

## Agent: docs-translator

- **Role:** Maintains multilingual documentation (English, German) and user-facing strings.
- **Capabilities:**
  - Manages `strings.xml` and Compose string resources.
  - Writes README, CONTRIBUTING, and in-app help.
  - Translates UI strings and documentation between EN and DE.
  - Ensures README badges are up-to-date and reflect project status.
- **Tools:** Android resource files, Markdown, Shields.io.

---

**Coordination rules:**

- All agents work on feature branches and open pull requests.
- An agent must not merge its own PR; a different agent reviews and merges.
- The `android-architect` agent sets up the initial structure before feature work begins.
- Every feature request must be submitted as a GitHub Issue with a clear title and description.
- Agents pick up Issues by commenting "/opencode claim" and then begin implementation.
- Agents must never commit generated, temporary, or build-related files. This includes but is not limited to:
  - Build outputs (`build/`, `.gradle/`, `*.apk`, `*.aab`)
  - IDE configuration (`.idea/`, `*.iml`)
  - Local logs, temp files (`*.log`, `*.tmp`, `*.bak`)
  - Environment-specific files (e.g., `local.properties`)
- Before committing, the agent must run `git status` and stage **only** intended source files, documentation, and configuration that is part of the project.
- The repository must maintain a complete `.gitignore` for Android/Gradle projects. If a generated file is not covered, the agent adds the pattern to `.gitignore` immediately and excludes the file from the commit.
- As a safety step, agents should execute `./gradlew clean` (or the equivalent) before staging, to remove any stale build artifacts.
- Any commit that accidentally contains a disallowed file must be reverted or amended by the agent before requesting review.

  
