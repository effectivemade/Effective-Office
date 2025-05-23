import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties

plugins {
    id(Plugins.AndroidLib.plugin)
    id(Plugins.Kotlin.plugin)
    id(Plugins.Parcelize.plugin)
    id(Plugins.BuildConfig.plugin)
    id(Plugins.Serialization.plugin)
    id(Plugins.CocoaPods.plugin)
    id(Plugins.Sentry.plugin) version Plugins.Sentry.version
}

android {
    namespace = "band.effective.office.contract"
    compileSdk = 34

    defaultConfig {
        minSdk = 26
        targetSdk = 34
    }
    sourceSets["main"].apply {
        manifest.srcFile("src/androidMain/AndroidManifest.xml")
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

kotlin {
    android {
        compilations.all {
            kotlinOptions {
                jvmTarget = "1.8"
            }
        }
    }

    iosX64()
    iosArm64()
    iosSimulatorArm64()

    sourceSets {
        val commonMain by getting {
            dependencies {
                api(Dependencies.Ktor.Client.Core)
                implementation(Dependencies.KotlinxSerialization.json)
                implementation(Dependencies.KotlinxDatetime.kotlinxDatetime)
                implementation(Dependencies.Ktor.Client.CIO)
                implementation(Dependencies.Ktor.Client.Auth)
                implementation(Dependencies.Ktor.Client.negotiation)
                implementation(Dependencies.Ktor.Client.jsonSerialization)
                implementation(Dependencies.Ktor.Client.logging)

            }
        }

        val androidMain by getting {
            dependencies {
                api(Dependencies.Ktor.Client.Android)
            }
        }

        val iosX64Main by getting
        val iosArm64Main by getting
        val iosSimulatorArm64Main by getting
        val iosMain by creating {
            dependsOn(commonMain)
            iosX64Main.dependsOn(this)
            iosArm64Main.dependsOn(this)
            iosSimulatorArm64Main.dependsOn(this)
            dependencies {
                implementation(Dependencies.Ktor.Client.Darwin)
            }
        }
    }

    cocoapods {
        version = "2.0.0"
    }

}

val apiKey: String = gradleLocalProperties(rootDir).getProperty("apiKey")
buildConfig {
    buildConfigField("String", "apiKey", apiKey)
    buildConfigField(
        "String",
        "releaseServerUrl",
        "\"https://d5do2upft1rficrbubot.apigw.yandexcloud.net\""
    )
    buildConfigField(
        "String",
        "debugServerUrl",
        "\"https://d5dfk1qp766h8rfsjrdl.apigw.yandexcloud.net\""
    )
}