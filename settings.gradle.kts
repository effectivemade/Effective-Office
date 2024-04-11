plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.5.0"
}
rootProject.name = "effective-office"
include("cloud-server", "rpi-server", "gpio")
include("common")
include("composeApp")
include(":tabletApp")
include(":tabletApp:features:selectRoom")
include(":tabletApp:features:roomInfo")
include(":tabletApp:features:network")
include(":tabletApp:features:domain")
include(":tabletApp:features:core")
include("wheel-picker-compose")
include("modal_custom_dialog")
include("contract")
include("tvApp")
include(":tabletApp:features:di")
include("tvServer")
include("notion")
