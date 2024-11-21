plugins {
    id(Plugins.Kotlin.plugin)
    id(Plugins.MultiplatformCompose.plugin)
    id(Plugins.AndroidLib.plugin)
}
android {
    namespace = "band.effective.office.dialog"
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
                api(compose.ui)
                api(compose.foundation)
                api(compose.material)
                api(compose.animation)
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }
    }
}