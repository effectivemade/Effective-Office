plugins {
    id(Plugins.AndroidLib.plugin)
    id(Plugins.MultiplatformCompose.plugin)
    id(Plugins.Kotlin.plugin)
    id(Plugins.Parcelize.plugin)
    id(Plugins.Libres.plugin)
}

android {
    namespace = "band.effective.office.tablet.features.selectRoom"
    compileSdk = 33
    sourceSets["main"].apply {
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
                implementation(compose.runtime)
                implementation(compose.foundation)
                implementation(compose.material)

                // Decompose
                implementation(Dependencies.Decompose.decompose)
                implementation(Dependencies.Decompose.extensions)

                //Koin
                api(Dependencies.Koin.core)

                //Libres
                implementation(Dependencies.Libres.libresCompose)

                // MVI Kotlin
                api(Dependencies.MviKotlin.mviKotlin)
                api(Dependencies.MviKotlin.mviKotlinMain)
                api(Dependencies.MviKotlin.mviKotlinExtensionsCoroutines)
                
                //EpicDatePicker
                implementation(Dependencies.Calendar.composeDatePicker)
                implementation(Dependencies.KotlinxDatetime.kotlinxDatetime)

                implementation(project(":tabletApp:features:core"))
                implementation(project(":tabletApp:features:domain"))
            }
        }

        val androidMain by getting {
            dependencies {
                //Koin
                api(Dependencies.Koin.android)
            }
        }
    }

    libres {
        // https://github.com/Skeptick/libres#setup
        generatedClassName = "MainRes" // "Res" by default
        generateNamedArguments = true // false by default
        baseLocaleLanguageCode = "ru" // "en" by default
        camelCaseNamesForAppleFramework = true // false by default

    }

}
