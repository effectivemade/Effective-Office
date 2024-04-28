<p align="center">
  <img src="..\assets\logo.jpg" width=""  height="250">
</p>

# Effective-Office-EffectiveBackend

[See GFM documentation](documentation/gfm/index.md) :point_left:

# Goal :dart:

The main goal of subproject is to create a proxy backend, which will provide abilities to book workspaces and meeting rooms, edit workspaces information, authentication and more! It was important to us that the application synchronizes with Google Calendar, in order to keep the ability to perform the same tasks with the same tools, if suddenly, the employee will not be able to use the application.

# Features :fire:

## Workspace

Allows users to book workstations and meeting rooms. Through admin panel you can edit information about these places (number of charges, seating, etc).

## Users

Provides the ability to authenticate via work email, add information about employee and connect integrations.

# Architecture :hammer:

## System Context

<p align="center">
  <img src="..\assets\system_context.png" width="2000">
</p>

## Container Context

<p align="center">
  <img src="..\assets\demo_container_context.png" width="2000">
</p>

# Swagger :sparkles:

[Production server](https://d5do2upft1rficrbubot.apigw.yandexcloud.net/swagger/index.html)

[Test server](https://d5dfk1qp766h8rfsjrdl.apigw.yandexcloud.net/swagger)

## Used libraries 📚

A list of technologies used within the project:

* ktor-server
* koin
* ktorm
* postgresql drivers
* google api client
* google oauth client
* google api services calendar
* liquibase
* ktor swagger
* firebase
* kotlin tests junit
* mockito
* logback classic

# How to launch backend :running:

## Receiving code

First of all, you must get source code. <br>If you want to download it through **git**, you must type command:
> git clone git@github.com:effectivemade/labs-office-elevator.git

Or (if you don't have ssh key) 
> git clone https://github.com/effectivemade/labs-office-elevator.git

This will create folder inside your current directory

Otherwise, you have option to download source code through **ZIP** archive.

In our project, we use monorepository, so you really don't need all the folders. You only need **effectiveOfficeBackend** directory.

## Launch project

### Intellij Idea + test server database

You can run the application using test server database (see backend.local.properties in Notion). 
This allows all backend developers to use common data for testing 
and correctly receive data from the test Google calendars. **Don't use this configuration for testing migrations.**

First of all, you should create Kotlin run/debug configuration for the project in your Intellij Idea.
In the configuration, you should add values for project environment variables.
List of variables you may found in the text below. **MIGRATIONS_ENABLE variable must be false**.
Then you may run the application.

### Docker

If you have **Docker** and **Docker compose**, you may use docker-compose.yml and Dockerfile files to run project.<br>
In that case, you also should specify all environment variables. You may use .env file in the same directory, 
as docker-compose.yml located. This file must contain few variables definitions. List of variables you may found in text below. 

Syntax:
> <variable_name>=<variable_value>

As a sample, you may use **.env.example** file

To run application you need type in terminal, being inside backend project root directory (<all-repository-directory>/effectiveOfficeBackend). 
> docker compose up

Docker will download docker images of PostgreSQL and Java, 
build image with application itself and run postgres container at first place and effectiveOfficeBackend
container at the second. If build ends well, last message you will see in terminal is goind to be
> effectiveOfficeBackend  | <current_date> <current_time> [DefaultDispatcher-worker-1] INFO  ktor.application - Responding at http://0.0.0.0:8080

## Environment variables
You may use file .env.example as an example.

**DATABASE_PORT** - specify the port of database container where application will make a call to establish connection with postgres<br>
**DATABASE_NAME** - specify the name of database to connect<br>
**DATABASE_USERNAME** - specify the username of **database** user to connect<br>
**DATABASE_PASSWORD** - specify password to send to database container to connect<br>
**MIGRATIONS_ENABLE** - allows to turn on/off database migrations process. Any value instead of "true" will be recognised as "false"<br>
**VERIFICATION_PLUGIN_ENABLE** - allows to turn on/off requests authentication. Any value instead of "true" will be recognised as "false"<br>
**JSON_GOOGLE_CREDENTIALS** - json with Google credentials, needed to access Google calendar api<br>
**FIREBASE_SA_JSON** - credentials json file of Firebase service account<br>
**LOG_LEVEL** - logging level in application. Used in logback.xml. Default value: debug<br>
**DEFAULT_CALENDAR** - default Google calendar id, used for booking meeting rooms. 
If not defined value from the config file will be used instead.
Please note that meeting room bookings created via Google Calendar will not be displayed in DEFAULT_CALENDAR.<br>
**WORKSPACE_CALENDAR** - Google calendar id for booking working places (regular workspaces). If not defined value from the config file will be used instead.<br>

### Docker-only environment variables

**POSTGRES_PASSWORD** - defines a password for postgres docker container<br>
**POSTGRES_DB** - defines a database name for postgres docker container<br>
**POSTGRES_USER** - defines an username for postgres container<br>


## Authors: :writing_hand:

- [Daniil Zavyalov](https://github.com/zavyalov-daniil)
- [Danil Kiselev](https://github.com/kiselev-danil)
- [Egor Parkhomenko](https://github.com/1MPULSEONE)
