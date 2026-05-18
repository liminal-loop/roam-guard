This file defines the autonomous agents that collaborate on this repository.

Each agent has a specific role, skills, and allowed tools.



\---



\## Agent: android-architect

\- \*\*Role:\*\* Designs the high-level project structure, modules, and component relationships.

\- \*\*Capabilities:\*\*

&#x20; - Creates multi-module Android project skeleton (Gradle, Kotlin DSL).

&#x20; - Defines packages, Hilt modules, Room database, DataStore.

&#x20; - Sets up Compose navigation and theme.

&#x20; - Ensures clean architecture (MVVM + Repository).

\- \*\*Tools:\*\* File system, Gradle build files, Android project templates.



\## Agent: android-developer

\- \*\*Role:\*\* Implements features according to specifications in GitHub Issues.

\- \*\*Capabilities:\*\*

&#x20; - Writes Kotlin code (ViewModels, UseCases, Repositories, UI with Compose).

&#x20; - Integrates system APIs: TelephonyManager, NetworkScan, Shizuku/root.

&#x20; - Implements background services, foreground notifications, WorkManager.

&#x20; - Handles permissions and runtime checks.

&#x20; - Adds unit and UI tests for all new code.

\- \*\*Tools:\*\* Kotlin, Compose, Hilt, Room, MockK, Compose Testing.



\## Agent: sys-integration-specialist

\- \*\*Role:\*\* Handles low-level system interactions (root, Shizuku, hidden APIs).

\- \*\*Capabilities:\*\*

&#x20; - Creates `root-helper` and `shizuku-helper` modules.

&#x20; - Executes `su` commands, reflection-based API calls.

&#x20; - Wraps dangerous operations behind safe interfaces.

&#x20; - Provides fallback detection if root/Shizuku is unavailable.

\- \*\*Tools:\*\* ADB, Shell commands, reflection, Shizuku SDK.



\## Agent: qa-engineer

\- \*\*Role:\*\* Ensures code quality, test coverage, and CI pipeline health.

\- \*\*Capabilities:\*\*

&#x20; - Reviews PRs for coding standards, architecture violations, security issues.

&#x20; - Runs and maintains GitHub Actions workflows (build, test, lint).

&#x20; - Configures detekt, ktlint, and code coverage reports.

&#x20; - Writes integration tests and end-to-end scenarios.

\- \*\*Tools:\*\* GitHub Actions, Gradle, static analysis tools.



\## Agent: docs-translator

\- \*\*Role:\*\* Maintains multilingual documentation (English, German) and user-facing strings.

\- \*\*Capabilities:\*\*

&#x20; - Manages `strings.xml` and Compose string resources.

&#x20; - Writes README, CONTRIBUTING, and in-app help.

&#x20; - Translates UI strings and documentation between EN and DE.

\- \*\*Tools:\*\* Android resource files, Markdown.



\---



\*\*Coordination rules:\*\*

\- All agents work on feature branches and open pull requests.

\- An agent must not merge its own PR; a different agent reviews and merges.

\- The `android-architect` agent sets up the initial structure before feature work begins.

\- Every feature request must be submitted as a GitHub Issue with a clear title and description.

\- Agents pick up Issues by commenting “/opencode claim” and then begin implementation.

