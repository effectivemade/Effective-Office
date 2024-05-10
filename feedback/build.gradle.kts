plugins {
    kotlin("jvm")
    id(Plugins.Serialization.plugin)
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
    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.test {
    useJUnitPlatform()
}