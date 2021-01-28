plugins {
    id("com.android.application")
    kotlin("android")
    id("kotlin-android-extensions")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
    id("androidx.navigation.safeargs.kotlin")
}

android {
    compileSdkVersion(AppConfig.compileSdkVersion)
    buildToolsVersion(AppConfig.buildToolsVersion)

    defaultConfig {
        applicationId = "com.nezspencer.fairlistapp"
        minSdkVersion(AppConfig.minSdkVersion)
        targetSdkVersion(AppConfig.targetSdkVersion)
        versionCode = AppConfig.versionCode
        versionName = AppConfig.versionName

        buildConfigField("String", "BASE_URL", AppConfig.BASE_URL)

        testInstrumentationRunner = AppConfig.instrumentationRunner
    }

    buildTypes {
        getByName(AppConfig.releaseBuildType) {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }

    sourceSets {
        getByName("test").java.srcDirs("$projectDir/src/testShared")

        getByName("androidTest").java.srcDirs("$projectDir/src/testShared")
    }
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    implementation(Dependencies.mainLibs)
    kapt(Dependencies.kaptLibs)
    testImplementation(Dependencies.unitTestLibs)
    androidTestImplementation(Dependencies.uiTestLibs)
}