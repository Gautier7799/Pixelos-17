plugins {
    id("com.android.application")
}

android {
    namespace = "com.pixelos.launcher"
    compileSdk = 37

    defaultConfig {
        applicationId = "com.pixelos.launcher"
        minSdk = 26
        targetSdk = 37
        versionCode = 1
        versionName = "0.1.0-foundation"
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    buildTypes {
        debug {
            applicationIdSuffix = ".debug"
            versionNameSuffix = "-debug"
        }
        release {
            isMinifyEnabled = false
        }
    }
}
