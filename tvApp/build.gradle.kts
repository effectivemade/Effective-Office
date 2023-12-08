import java.io.FileInputStream
import java.util.Properties

@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    id(libs.plugins.android.get().pluginId)
    kotlin("android")
    id("kotlin-kapt")
    id("com.google.devtools.ksp").version("1.9.20-1.0.14")
    alias(libs.plugins.hilt)
}

val localProperties = Properties()
localProperties.load(FileInputStream(rootProject.file("local.properties")))

android {
    namespace  = "band.effective.office.tv"
    compileSdk = 33

    defaultConfig {
        applicationId  = "band.effective.office.tv"
        minSdk  = 24
        targetSdk = 33

        versionCode =  1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }

        buildConfigField ("String", "apiLeaderUrl", localProperties["apiLeaderUrl"].toString())
        buildConfigField ("String", "apiMattermostUrl", localProperties["apiMattermostUrl"].toString())
        buildConfigField ("String", "mattermostBotToken", localProperties["mattermostBotToken"].toString())
        buildConfigField ("String", "apiSynologyUrl", localProperties["synologyIP"].toString())
        buildConfigField ("String", "synologyLogin", localProperties["synologyLogin"].toString())
        buildConfigField ("String", "synologyPassword", localProperties["synologyPassword"].toString())
        buildConfigField ("String", "folderPathPhotoSynology", localProperties["folderPathPhotoSynology"].toString())
        buildConfigField ("String", "uselessFactsApi", localProperties["uselessFactsApi"].toString())
        buildConfigField ("String", "mattermostBotDirectId", localProperties["mattermostBotDirectId"].toString())
        buildConfigField ("String", "duolingoUrl", localProperties["duolingoUrl"].toString())
        buildConfigField("String", "notionToken", localProperties["NOTION_TOKEN"].toString())
        buildConfigField("String", "notionDatabaseId", localProperties["NOTION_DATABASE_ID"].toString())
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_17.toString()
    }

    buildFeatures {
        compose =  true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.composeAndroid.get()
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    implementation(libs.bundles.android.core)

    //for tv
    implementation(libs.bundles.android.tv)

    //Retrofit
    implementation(libs.bundles.retrofit.core)
    implementation(libs.retrofit.moshi)

    // moshi
    implementation(libs.bundles.moshi.core)
    ksp(libs.moshi.gen)

    //hilt di
    implementation(libs.hilt)
    implementation(libs.hilt.nav)
    kapt(libs.hilt.compiller)

    //qr
    implementation(libs.zxing)

    //navigation
    implementation(libs.navigation)

    //coil
    implementation(libs.coil)
    implementation(libs.coil.svg)

    //notion
    implementation(libs.notion)

    //test
    testImplementation (libs.junit)
    androidTestImplementation (libs.androidJunit)
    androidTestImplementation (libs.espresso)

}
kapt {
    correctErrorTypes = true
}