plugins {
    alias(libs.plugins.android.test)
    alias(libs.plugins.kotlin.android)
}

android {
    namespace = "com.roamguard.e2e"
    compileSdk = 34

    defaultConfig {
        minSdk = 26
        testInstrumentationRunner = "com.roamguard.e2e.runner.E2ETestRunner"
        testInstrumentationRunnerArguments["clearPackageData"] = "true"
    }

    targetProjectPath = ":app"

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = "17"
    }

    buildFeatures {
        compose = true
    }
}

dependencies {
    implementation(project(":app"))
    implementation(project(":domain"))
    implementation(project(":data"))
    implementation(project(":common"))
    implementation(project(":mcc-data"))

    implementation(libs.androidx.test.runner)
    implementation(libs.androidx.test.rules)
    implementation(libs.androidx.test.core)
    implementation(libs.androidx.test.ext.junit)
    implementation(libs.espresso.core)

    implementation(platform(libs.compose.bom))
    implementation(libs.compose.ui.test.junit4)
    implementation(libs.compose.ui.test.manifest)

    implementation(libs.kotlinx.coroutines.test)
    implementation(libs.junit)
}
