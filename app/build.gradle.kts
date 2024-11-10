@file:Suppress("SpellCheckingInspection")

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.devtools.ksp")
    id("com.google.gms.google-services")
    id("com.google.firebase.crashlytics")
    id("androidx.navigation.safeargs.kotlin")
}

android {
    namespace = "com.heinlin.thenewsapp"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.heinlin.thenewsapp"
        minSdk = 29
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        viewBinding = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.firebase.config)
    implementation(libs.firebase.messaging)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    implementation(libs.material.v161)

    // Architectural Components
    implementation(libs.androidx.lifecycle.viewmodel.ktx)

    // Room
    implementation(libs.androidx.room.runtime)
    ksp(libs.androidx.room.compiler)

    // Kotlin Extensions and Coroutines support for Room
    implementation(libs.androidx.room.ktx)

    // Coroutines
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.kotlinx.coroutines.android)

    // Coroutine Lifecycle Scopes
    implementation(libs.androidx.lifecycle.viewmodel.ktx.v262)
    implementation(libs.androidx.lifecycle.runtime.ktx)

    // Retrofit
    implementation(libs.retrofit)
    implementation(libs.converter.gson)
    implementation(libs.logging.interceptor)

    // Navigation Components
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)

    // Glide
    implementation(libs.glide.v4150) // Replace with the latest version
    annotationProcessor(libs.compiler.v4150) // Replace with the latest version

    //splashscreen
    implementation(libs.androidx.core.splashscreen)
    implementation(libs.support.annotations)

    // firebase
    implementation(platform(libs.firebase.bom))
    implementation(libs.google.firebase.analytics)

    // Crashlytics
    implementation(libs.firebase.crashlytics)
    implementation(libs.firebase.analytics)
    //noinspection UseTomlInstead
    implementation("com.google.firebase:firebase-crashlytics:19.2.1")

    // Remote Config
    //noinspection UseTomlInstead
    implementation("com.google.firebase:firebase-config:22.0.1")

    // Cloud messaging
    //noinspection UseTomlInstead
    implementation("com.google.firebase:firebase-messaging:24.0.3")


}