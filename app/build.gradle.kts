plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)

    id("com.google.devtools.ksp")
    id("com.google.dagger.hilt.android")
}

android {
    namespace = "uz.egam.drinkwaterreminder"
    compileSdk {
        version = release(36)
    }

    defaultConfig {
        applicationId = "uz.egam.drinkwaterreminder"
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.compose.foundation)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)
    implementation(libs.androidx.compose.material.icons.extended)

    // Hilt
    implementation("com.google.dagger:hilt-android:2.57.2")

    //Ksp
    ksp("com.google.dagger:hilt-android-compiler:2.57.2")

    //Voyager Hilt integration
    implementation("cafe.adriel.voyager:voyager-hilt:1.1.0-beta03")

    implementation("androidx.hilt:hilt-work:1.3.0")

    // Kotlin + coroutines
    implementation("androidx.work:work-runtime-ktx:2.11.0")


    // Navigator
    implementation("cafe.adriel.voyager:voyager-navigator:1.1.0-beta03")
    // BottomSheetNavigator
    implementation("cafe.adriel.voyager:voyager-bottom-sheet-navigator:1.1.0-beta03")
    // TabNavigator
    implementation("cafe.adriel.voyager:voyager-tab-navigator:1.1.0-beta03")
    // Transitions
    implementation("cafe.adriel.voyager:voyager-transitions:1.1.0-beta03")

    // Core of Orbit, providing state management and unidirectional data flow (multiplatform)
    implementation("org.orbit-mvi:orbit-core:10.0.0")
    // Integrates Orbit with Android and Common ViewModel for lifecycle-aware state handling (Android, iOS, desktop)
    implementation("org.orbit-mvi:orbit-viewmodel:10.0.0")
    // Enables Orbit support for Jetpack Compose and Compose Multiplatform (Android, iOS, desktop)
    implementation("org.orbit-mvi:orbit-compose:10.0.0")

    //Room
    implementation("androidx.room:room-runtime:2.8.3")
    ksp("androidx.room:room-compiler:2.8.3")
}