# Effective-office

## Goal :dart:

The main goal of the project is the automation of various processes in the office and providing
interesting statistics for employees.

## Technical goal: :wrench:

The main technical task of the project is to create a multi-module application on Kotlin,
trying to focus on the most modern and relevant solutions in this language. Throughout the project,
we tried to use other languages and
technologies as little as possible.

## Project Structure: :dizzy:

Pre-requisite: [Notion](https://www.notion.so/effectiveband/613e964862f44c9faefefd7958697dd9?pvs=4).
Note: due to the incompleteness of the database, placeholders may appear in applications

[Effective-Tablet](tabletApp/README.md)

An application for booking meeting rooms on a tablet.

[Office-Elevator](composeApp/README.md)

Mobile cross-platform application for interacting with the elevator in the office. Allows you to
call the elevator remotely, after logging in through your google account

[Office-Elevator](composeApp/README.md)

Mobile cross-platform application for interacting with the elevator in the office. Allows you to
call the elevator remotely, after logging in through your google account

[EffectiveOfficeBackend](effectiveOfficeBackend/readme.md)

Backend application which will provide abilities to book workspaces and meeting rooms, edit workspaces information, authentication and more! It was important to us that the application synchronizes with Google Calendar

## Contributing :raised_hands:

Our project is open-source, so we welcome quality contributions! To make your contribution to the
project efficient and easy to check out, you can familiarize yourself with the project's git flow
and commit rules. If you want to solve an existing issue in the project, you can read the list in
the issues tab in the repository.

### Git flow

The best way to start helping with project development is to do a fork of our project, then make
changes and make a pull-request to the to the appropriate branch to which your contribution relates.

<img src="/assets/git-flow-image.png"> 

### Rules on commit messages' content

1. Use the body to explain what and why vs. how
    * Please make an extra effort to explain why changes are needed for every non-trivial
      modification.
2. If your pull request solves problem from the issues, please leave a link to it in the body of the
   pull request
3. If possible, try to avoid commits like *"Fixed after check "* and replace them with meaningful
   ones.
4. Keep the subject (the first line of the commit message) clean and readable. All additional
   information and directives for external tools
   should be moved to the body of the message.

### Rules on commit messages' style/formatting

1. Before the subject line, add a symbol to show what your commit contains

* [+] - Addition
* [-] - Delete
* [~] - Fix, edit

2. Separate subject from body with a blank line
3. Do not capitalize the subject line
4. Do not end the subject line with a period
5. Use the imperative mood in the subject line

## Guidelines for starting up: :computer:

In order to build all parts of project, you will need JDK and Android Studio. We are using this
versions:

Android Studio Flamingo 2022.2.1 Patch 2

JDK: Version 11.0.15

## How to run

* You need Android Studio Flamingo 2022.2.1 Patch 2
* [Read the backend documentation]()
* Add `apiKey=<YourKey>` in `local.properties` file, for
  example `apiKey="72288ebc5b893cc92a76c701c6b700b6"`
* Run -> Run 'app'


## Running an application using gradle: :arrow_forward:

1. Open the terminal and go to the root directory of the project.

2. Run the ./gradlew assembleDebug command to assemble the project in debug mode.

3. To run the application on an emulator or connected device, run the ./gradlew installDebug
   command.

4. If you want to run tests, run the command ./gradlew test.

5. If you want to assemble the APK file for the release version, run the command ./gradlew
   assembleRelease.

## Authors: :writing_hand:

* [Artem Gruzdev](https://github.com/gull192) (Effective-TV,Effective-office)
* [Egor Parkomenko](https://github.com/1MPULSEONE) (Effective-office)
* [Stanislav Radchenko](https://github.com/Radch-enko) (Office-Elevator, Effective-TV,
  Effective-office)
* [Maksim Mishenko](https://github.com/UserNameMax) (Effective-TV,Effective-office)
* [Margarita Ginjolia](https://github.com/MargaritaDj)(Effective-office)
* [Victor Konyshev](https://github.com/DireRaven-exe)(Effective-office)
* [Angelika Semenova](https://github.com/Liker4ik26)(Effective-office)
* [Olesia Shinkarenko](https://github.com/OlesiaShinkarenko)(Effective-office)
* [Danil Kiselev](https://github.com/kiselev-danil)(Effective-office)
* [Daniil Zavyalov](https://github.com/zavyalov-daniil)(Effective-office)
* [Roman Zyuzin](https://github.com/Roman194)(Effective-office)
* [Arseniy Savin](https://github.com/ayusavin)(Effective-office)
* [Vyacheslav Deich](https://github.com/plumsoftware)(Effective-office)
