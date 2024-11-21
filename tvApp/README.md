# Effective-Office-EffectiveTV

## Goal :dart:
The main purpose of the subproject is to show information about employees (such as ah birthdays, anniversaries of work in the company, joining the team and duolingo score of each employee) in the format of stories.

## Screenshots 	:camera_flash:

### Stories types

|                         Birthdays                          |                    Anniversary of working                    |
|:----------------------------------------------------------:|:------------------------------------------------------------:|
| <img src="../../assets/birthday-example.png" height="150"> | <img src="../../assets/aniversary-example.png" height="150"> |

|                          New employee                          |                      Duolingo top on XP                       |
|:--------------------------------------------------------------:|:-------------------------------------------------------------:|
| <img src="../../assets/new-employee-example.png" height="150"> | <img src="../../assets/duolingo-xp-example.png" height="150"> |

|                 Duolingo top of the day in shock mode                 |
|:---------------------------------------------------------------------:|
| <img src="../../assets/duolingo-shock-mode-example.png" height="150"> |


# Features :fire:

## Story

The subproject contains stories screen, which fetches data from notion database and displays actual info about effective employes, which may fit the definition of one of these story types:

### Birthdays: :birthday:

Show employee name, photo and congratulatory text.

### Anniversary of working for the company: :star:

Shows employee name, photo and congratulatory text, with the number of years with the company.

### New employee: :baby_chick:

Shows employee name and congratulatory text.

## Duolingo

Since a lot of people in the company are learning languages with the [Duolingo](https://ru.duolingo.com/) app, we decided to use their API to display the statistics for the week, thus motivating more people to start paying attention to learning new languages and do it more consistently. There are currently the following types of stories for duolingo:

### Duolingo top on XP: 🥇

Shows duolingo top of employees by xp, with their names, photo, languages and all time XP.

### Duolingo top of the day in shock mode: ⚡

Shows duolingo top by number of days in stike mode.

The screen also contains buttons that can be used to interact with stories (switch, pause or resume). You can see them by pressing the down button on the remote control. The screen can be started in two formats: separate from all or be added to the autoplay to play with another screens, using autoplay menu.

## Leader ID Events :office:

The office of our company is located in the "boiling point" building, which hosts many interesting events every day. "Boiling point" has its own [api](https://apps.leader-id.ru/swagger/) for getting events. In order not to miss events that are interesting for employees, we use the boiling point api to receive and display information about events for the month. There is a qr on the screen to go to the registration site for the event.

<img src="../../assets/leader-events-screen.png" title="LeaderID Events Screen">

## Mattermost messages :speech_balloon:

Our company uses Mattermost as a corporate messenger. Employees want to display their messages on the TV screen, for this a bot was developed that reads chats to which it is added. If an employee contacts the bot, the bot will add his message to storyes. If a message is marked with [importantMessageReaction](https://github.com/effectivemade/labs-office-elevator/blob/fc7c7a67f7f0009e336dda40d8ad2c57af657b82/tv-app/effecticeTV/app/src/main/java/band/effective/office/tv/domain/botLogic/BotConfig.kt#L6) , then the message will be shown immediately and will break the content being shown. If the message is marked with [deleteMessageReaction](https://github.com/effectivemade/labs-office-elevator/blob/fc7c7a67f7f0009e336dda40d8ad2c57af657b82/tv-app/effecticeTV/app/src/main/java/band/effective/office/tv/domain/botLogic/BotConfig.kt#L7), then the message will be unsubscribed. Also, the bot can be asked to creep the message until a certain day.

<img src="../../assets/message-in-story.png" title="Message in story">
<img src="../../assets/message.png" title="Urgent message">

## Photo slide show :sunrise:

You can display your corporate photos in this app. Photos are taken from the web, namely from [Synology photo](https://www.synology.com/en-global/dsm/feature/photos). You can group photos by albums, and show only the ones you need.
You can also automate the process of adding photos to an album using our [bot for mattermost](https://github.com/effectivemade/labs-office-elevator/tree/mattermost_synology_bot/mattermost-bot)

## Autoplay :arrow_forward:

For the convenience of the display, it is possible to automatically switch screens between themselves.

## Used libraries 📚

A list of technologies used within the project:
* DI: [Hilt](https://dagger.dev/hilt/)
* Notion: [Notion-sdk-jvm](https://github.com/seratch/notion-sdk-jvm)
* Multithreading: [Kotlin Flow](https://kotlinlang.org/docs/flow.html) and [Coroutines](https://kotlinlang.org/docs/flow.html)
* Image loading: [Coil](https://coil-kt.github.io/coil/)
* Network client: [okHttp](https://square.github.io/okhttp/)
* REST API: [retrofit](https://square.github.io/retrofit/)
* JSON: [moshi](https://github.com/square/moshi)
* QR: [zxing](https://github.com/zxing/zxing)

## Authors: :writing_hand:

[Artem Gruzdev](https://github.com/gull192)
[Egor Parkomenko](https://github.com/1MPULSEONE)
[Stanislav Radchenko](https://github.com/Radch-enko)
[Maksim Mishenko](https://github.com/UserNameMax)

## Guidelines for starting up: :computer:

In order to build all parts of project, you will need JDK and Android Studio. We are using this versions:

Android Studio Electric Eel: Version 2022.1.1

JDK: Version 11.0.15

To start the project, you will need to add the fields required for build config in the local.properties file. The required values for these fields can be found in the relevant section on the project page in Notion.

## Running an application using gradle: :arrow_forward:

1. Open the terminal and go to the root directory of the project.

2. Run the ./gradlew assembleDebug command to assemble the project in debug mode.

3. To run the application on an emulator or connected device, run the ./gradlew installDebug command.

4. If you want to run tests, run the command ./gradlew test.

5. If you want to assemble the APK file for the release version, run the command ./gradlew assembleRelease.

## Third-party docs: :page_with_curl:

* [Notion sdk](https://github.com/seratch/notion-sdk-jvm)
* [LeaderId Api](https://apps.leader-id.ru/swagger/)
* [Mattermost Api](https://api.mattermost.com/)
* [Synology Documentation Api](https://global.download.synology.com/download/Document/Software/DeveloperGuide/Package/FileStation/All/enu/Synology_File_Station_API_Guide.pdf)
* [Unofficial documentation on Synology Photo Api](https://github.com/zeichensatz/SynologyPhotosAPI)


