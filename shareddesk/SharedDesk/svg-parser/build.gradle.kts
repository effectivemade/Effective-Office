plugins {
    alias(libs.plugins.multiplatform)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.compose)
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlinx.serialization)
}

group = "band.effective.office.shareddesk.svgparser"
version = "1.0.0"

kotlin {
    jvm()
    jvmToolchain(11)
    androidTarget()
    js {
        browser()
        binaries.executable()
    }

    wasmJs {
        browser()
        binaries.executable()
    }
    iosX64()
    iosArm64()
    iosSimulatorArm64()

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(compose.runtime)
                implementation(compose.foundation)
                implementation(compose.material3)
                implementation(libs.kotlinx.serialization.json)
                implementation(libs.koin.core)
                implementation(libs.xmlutil)
                implementation(libs.xmlutil.serialization)
                implementation(libs.kermit)
            }
        }
        jvmMain.dependencies {
            dependencies {
                implementation(libs.androidx.ui.graphics)
            }
        }
        jsMain.dependencies {
            implementation(libs.xmlutil.js)
            implementation(libs.xmlutil.serialization.js)
        }
    }
}

android {
    namespace = "band.effective.office.shareddesk.svgparser"
    compileSdk = libs.versions.android.compileSdk.get().toInt()
    defaultConfig {
        minSdk = libs.versions.android.minSdk.get().toInt()
    }
}