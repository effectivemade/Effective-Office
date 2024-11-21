<p align="center">
  <img src="..\assets\logo.jpg" width="" alt="light bulb icon" height="250">
</p>

# Effective-Office-EffectiveTablet

## Goal :dart:

The main goal of our project is to create a simple and convenient application for booking meeting
rooms on a tablet. We aim to provide employees with the ability to quickly and efficiently find
available meeting rooms and book them for the desired time. Our goal is to simplify the meeting
organization process, save time, and create a comfortable working environment for our users. We
strive to provide a reliable and user-friendly system for booking meeting rooms on a tablet, in
order to improve workflow and enhance productivity.

## Screenshots    :camera_flash:

## Application

### Screens:

|                        Main                         |
|:---------------------------------------------------:|
| <img height="500" src="..\assets\main-screen.png"/> |

|                        Meeting selection                         |
|:----------------------------------------------------------------:|
| <img height="500" src="..\assets\meeting-selection-screen.png"/> |

|                        Data Change                         |
|:----------------------------------------------------------:|
| <img height="500" src="..\assets\data-change-screen.png"/> |

# Features :fire:

## Viewing available meeting rooms

Allows users to view a list of available meeting rooms with information on their availability and
other details.

## Booking a meeting room

Enables users to select a desired meeting room and book it for a specific time.

## Modifying reservations

Provides users with the ability to make changes to existing reservations, such as changing the time
or duration of the meeting.

## Managing reservations

Allows users to view and manage their existing reservations, including cancelling and changing
meeting details.

# Used libraries 📚

A list of technologies used within the project:

* DI: [Koin](https://insert-koin.io/)
* Navigation: [Decompose](https://github.com/arkivanov/Decompose)
* Resources: [Libres](https://github.com/Skeptick/libres)
* Multithreading: [Kotlin Flow](https://kotlinlang.org/docs/flow.html)
  and [Coroutines](https://kotlinlang.org/docs/flow.html)
* Network client: [Ktor](https://ktor.io/)
* JSON: [Kotlinx Serialization](https://github.com/Kotlin/kotlinx.serialization)
* UI: [MVI Kotlin](https://github.com/arkivanov/MVIKotlin)
  and [Compose](https://developer.android.com/jetpack/compose/tooling)

# Project Structure
- core - core module with models, material theme, common ui elements and common utils
- di - dependenci injection modules
- domain - use cases and other logic
- network - repositoryes and other classes for i/o information
- roomInfo - module with main screen and settings screen
- selectScreen - module with modal windows (name is old)

# Guidelines for starting up: :computer:

In order to build all parts of project, you will need JDK and Android Studio. We are using this
versions:

Android Studio Flamingo 2022.2.1 Patch 2

JDK: Version 11.0.15

## Running an application using gradle: :arrow_forward:

1. Open the terminal and go to the root directory of the project.

2. Run the ./gradlew assembleDebug command to assemble the project in debug mode or ./gradlew assembleRelease command to assemble the project in release mode.

3. To run the application on an emulator or connected device, run the ./gradlew installDebug or ./gradlew installRelease command.

4. If you want to run tests, run the command ./gradlew test.

5. If you want to assemble the APK file for the release version, run the command ./gradlew
   assembleRelease.

# Authors: :writing_hand:

- [Margarita Ginjolia](https://github.com/MargaritaDj)
- [Maksim Mishenko](https://github.com/UserNameMax)
- [Victor Konyshev](https://github.com/DireRaven-exe)
- [Angelika Semenova](https://github.com/Liker4ik26)
