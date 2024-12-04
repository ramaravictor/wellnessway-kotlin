plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.kotlin.serialization)
    id("kotlin-kapt")
    id("io.realm.kotlin")
    id("com.google.dagger.hilt.android")
    id ("com.google.android.libraries.mapsplatform.secrets-gradle-plugin")


}

android {
    namespace = "com.example.wellnessway"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.wellnessway"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
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
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.support.annotations)
    implementation(libs.androidx.room.common)
    implementation(libs.androidx.room.ktx)
    implementation(libs.androidx.room.runtime.android)
    implementation ("androidx.room:room-runtime:2.5.0")
    implementation(libs.androidx.runtime.livedata)
    kapt ("androidx.room:room-compiler:2.5.0")
    implementation ("androidx.room:room-ktx:2.5.0")
    //implementation(libs.androidx.room.runtime.jvm)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)


    implementation(libs.navigation.compose)
    implementation(libs.kotlinx.serialization.json)

// Coroutines
// Coroutine Lifecycle Scopes
    implementation(libs.androidx.lifecycle.viewmodel.ktx)

    //Dagger - Hilt
    implementation(libs.hilt.android.v2511)
    kapt(libs.hilt.android.compiler.v2511)

    implementation(libs.androidx.hilt.navigation.compose)

    // Retrofit
    implementation(libs.retrofit)
    implementation(libs.converter.gson)
    implementation(libs.okhttp)
    implementation(libs.logging.interceptor)
    implementation(libs.androidx.datastore.preferences)
    implementation(libs.coil.compose)
    implementation(libs.composeIcons.fontAwesome)
    implementation(libs.androidx.core.splashscreen)
    implementation(libs.androidx.biometric)
    implementation(libs.androidx.appcompat)
    implementation(libs.play.services.location)

    implementation(platform("androidx.compose:compose-bom:2023.10.01"))
    implementation("androidx.activity:activity-compose")
    implementation("androidx.compose.foundation:foundation")
    implementation("androidx.compose.material:material")

    //    viewmodel
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation("io.realm.kotlin:library-base:1.11.0")
    implementation("com.google.accompanist:accompanist-permissions:0.30.1")
    implementation("androidx.compose.material:material-icons-extended:1.7.5")

    //animasi
    implementation("io.coil-kt:coil-compose:2.2.2")
    implementation("io.coil-kt:coil-gif:2.2.2")

}