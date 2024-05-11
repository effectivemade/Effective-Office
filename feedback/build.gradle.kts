import dev.icerock.gradle.utils.propertyString

plugins {
    kotlin("jvm")
    id(Plugins.Serialization.plugin)
    id("io.ktor.plugin")
}

group = "ru.omgtu.ivt213.mishenko.maksim"
version = "unspecified"

repositories {
    mavenCentral()
}

dependencies {
    implementation(libs.notion)

    implementation(libs.ktor.client.core)
    implementation(libs.ktor.client.cio)
    implementation(libs.ktor.client.commonLogging)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.ktor.client.contentNegotiation)
    implementation(libs.ktor.client.serialization.json)
    implementation(libs.ktor.client.auth)

    implementation(libs.ydb.sdk.bom)
    implementation(libs.ydb.sdk.coordination)
    implementation(libs.ydb.sdk.scheme)
    implementation(libs.ydb.sdk.topic)
    implementation(libs.ydb.sdk.table)
    implementation(libs.yc.auth.provider)

    implementation(libs.ktor.server.core)
    implementation(libs.ktor.server.netty)
    implementation(libs.ktor.server.logback)
    implementation(libs.ktor.server.contentNegotiation)

    implementation(libs.koin.core)
    implementation(libs.koin.ktor)

    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.test {
    useJUnitPlatform()
}

ktor {
    docker {
        jib {
            from {
                image = "openjdk:21-jdk"
            }
            to {
                //NOTE(Maksim Mishenko): put this properties in /feedback/gradle.properties
                val dockerHub = project.propertyString("registryUrl")
                val yandexPassword = project.propertyString("yandexPassword")
                image = "$dockerHub/feedback-image"
                auth {
                    username = "oauth"
                    password = yandexPassword
                }
            }
        }
    }
}