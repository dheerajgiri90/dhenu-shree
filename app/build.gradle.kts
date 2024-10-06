plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-parcelize")
    id("kotlin-kapt")
}

android {
    namespace = "com.dhenu.app"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.dhenu.app"
        minSdk = 24
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

    viewBinding {
        enable = true
    }

    buildFeatures {
        buildConfig = true
        dataBinding = true
    }

    val app_name = "Dhenu Shree"
    flavorDimensions.add("environment")

    productFlavors {
        create("dev") {
            dimension = "environment"
            versionCode = 1
            versionName = "1.0.0-alpha1"
            buildConfigField("String", "BASE_URL", "\"https://dggold.site99.in/api/\"")
            buildConfigField("String", "GOOGLE_API_URL", "\"https://maps.googleapis.com/maps/api/\"")
            buildConfigField("String", "PRIVACY_POLICY_URL", "\"https://encoresky.com/about\"")

            buildConfigField("String", "GOOGLE_API_KEY", "\"ABC\"")
            resValue("string", "google_maps_key", "ABC")
            resValue("string", "app_name", app_name)
        }

        create("staging") {
            dimension = "environment"
            versionCode = 1
            versionName = "1.0.0-alpha1"
            buildConfigField("String", "BASE_URL", "\"https://dggold.site99.in/api/\"")
            buildConfigField("String", "GOOGLE_API_URL", "\"https://maps.googleapis.com/maps/api/\"")
            buildConfigField("String", "PRIVACY_POLICY_URL", "\"https://encoresky.com/about\"")

            buildConfigField("String", "GOOGLE_API_KEY", "\"ABC\"")
            resValue("string", "google_maps_key", "ABC")
            resValue("string", "app_name", app_name)
        }

        create("production") {
            dimension = "environment"
            versionCode = 1
            versionName = "1.0.0"
            buildConfigField("String", "BASE_URL", "\"https://dggold.site99.in/api/\"")
            buildConfigField("String", "GOOGLE_API_URL", "\"https://maps.googleapis.com/maps/api/\"")
            buildConfigField("String", "PRIVACY_POLICY_URL", "\"https://encoresky.com/about\"")

            buildConfigField("String", "GOOGLE_API_KEY", "\"ABC\"")
            resValue("string", "google_maps_key", "ABC")
            resValue("string", "app_name", app_name)
        }
    }

}

dependencies {

    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")

    implementation("androidx.databinding:databinding-common:8.5.0")
    implementation("androidx.databinding:databinding-runtime:8.5.0")

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    implementation("androidx.legacy:legacy-support-v4:1.0.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.squareup.okhttp3:logging-interceptor:4.9.3")

    //Encrypted Shared Preference
    implementation("androidx.security:security-crypto:1.0.0")

//    implementation("io.reactivex.rxjava2:rxjava:2.2.9")
    implementation ("io.reactivex.rxjava3:rxjava:3.1.5")

    implementation("androidx.lifecycle:lifecycle-extensions:2.2.0")
    implementation("io.reactivex.rxjava2:rxandroid:2.0.2")
    implementation("com.jakewharton.retrofit:retrofit2-rxjava2-adapter:1.0.0")

    implementation("com.github.bumptech.glide:glide:4.16.0")

}