plugins {
    id(Plugins.AndroidLib.plugin)
    id(Plugins.Kotlin.plugin)
}


android {
    namespace = "band.effective.office.tablet.features.domain"
    compileSdk = 33
    sourceSets["main"].apply {
        manifest.srcFile("src/androidMain/AndroidManifest.xml")
        res.srcDirs("src/androidMain/resources")
        res.srcDir("build/generated/libres/android/resources")
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

    sourceSets {
        val commonMain by getting {
            dependencies {
                // Koin
                api(Dependencies.Koin.core)

                implementation(project(":tabletApp:features:core"))
                implementation(project(":tabletApp:features:network"))
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
