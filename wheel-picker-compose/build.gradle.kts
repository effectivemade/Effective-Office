plugins {
    id(Plugins.Kotlin.plugin)
    id(Plugins.MultiplatformCompose.plugin)
    id(Plugins.AndroidLib.plugin)
}

android {
    namespace = "com.commandiron.wheel_picker_composer"
    compileSdk = 33
    defaultConfig {
        minSdk = ConfigData.Android.minSdkVersion
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
                implementation(compose.runtime)
                implementation(compose.foundation)
                implementation(compose.material)
                implementation(compose.material3)

                implementation(Dependencies.KotlinxDatetime.kotlinxDatetime)
            }
        }
        val androidMain by getting
    }
}
