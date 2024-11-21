plugins {
    id(Plugins.AndroidLib.plugin)
    id(Plugins.Kotlin.plugin)
}


android {
    namespace = "band.effective.office.tablet.features.di"
    compileSdk = 34
    defaultConfig {
        minSdk = 26
        targetSdk = 34
    }
//    sourceSets["main"].apply {
//        manifest.srcFile("src/androidMain/AndroidManifest.xml")
//        res.srcDirs("src/androidMain/resources")
//        res.srcDir("build/generated/libres/android/resources")
//    }
}

kotlin {
    android {
        compilations.all {
            kotlinOptions {
                jvmTarget = "1.8"
            }
        }
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                // Koin
                api(Dependencies.Koin.core)

                implementation(project(":tabletApp:features:core"))
                implementation(project(":tabletApp:features:network"))
                implementation(project(":tabletApp:features:domain"))
            }
        }
        val androidMain by getting {
            dependencies{
                // Koin
                api(Dependencies.Koin.android)
            }
        }
    }
}
