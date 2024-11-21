
plugins {
    `kotlin-dsl`
}

allprojects {
    repositories {
        mavenCentral()
        google()
        maven {
            url = uri("https://jitpack.io")
        }
    }
}


