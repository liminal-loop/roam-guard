pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "RoamGuard"

include(":app")
include(":domain")
include(":data")
include(":common")
include(":root-helper")
include(":shizuku-helper")
include(":mcc-data")
include(":e2e-tests")
