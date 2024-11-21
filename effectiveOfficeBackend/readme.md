<p align="center">
  <img src="..\assets\logo.jpg" width=""  height="250">
</p>

# Effective-Office-EffectiveBackend

[See documentation](documentation/gfm/index.md) :point_left:

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

[Link](https://d5do2upft1rficrbubot.apigw.yandexcloud.net/swagger/index.html)

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
* h2 database drivers (for tests)
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

### Docker

If you have **Docker** and **Docker compose**, you may use docker-compose.yml and Dockerfile files to run project.<br>
In that case, you may need to specify some environment variables. You may use .env file in the same directory, 
as docker-compose.yml located. This file must contain few variables definitions. List of variables you may found in text below. 

Syntax:
> <variable_name>=<variable_value>

As a sample, you may use **.env.example** file

To run application you need type in terminal, being inside backend project root directory (<all-repository-directory>/effectiveOfficeBackend). 
> docker compose up

Docker will download docker images of postgreSQL and java, 
build image with application itself and run postgres container at first place and effectiveOfficeBackend
container at the second. If build ends well, last message you will see in terminal is goind to be
> effectiveOfficeBackend  | <current_date> <current_time> [DefaultDispatcher-worker-1] INFO  ktor.application - Responding at http://0.0.0.0:8080

### Local machine

It is hightly recommended not to run project locally, if you doesn't want to take part in development process.

To launch locally (without docker at all), you will need postgreSQL and jre. You also need to specify 
environment variables on running .jar file or inside your IDE.

Also, it may be useful during development, to run application itsserlf on local machine, but use 
postgres container from docker. In that case you may run named container by command:
> docker compsoe up <container_name>

## Environment variables

**POSTGRES_PASSWORD** - defines password for postgres db<br>
**POSTGRES_DB** - defines name of the postgres database to connect<br>
**POSTGRES_USER** - defines username for postgres container(postgresForKtor)<br>
**DATABASE_PASSWORD** - specify password to send to database container to connect<br>
**GOOGLE_CLIENT_ID** - Deprecated <br>
**GOOGLE_CLIENT_SECRET** - Deprecated<br>
**VERIFICATION_PLUGIN_ENABLE** - allows to turn on/off need in authetnication to access api. Any value instead of "true" will be recognised as "false"<br>
**DATABASE_HOST** - the name of the postgres docker container in the same network<br>
**DATABASE_PORT** - specify the port of database container where application will make a call to establish connection with postgres<br>
**DATABASE_NAME** - specify the name of database to connect<br>
**DATABASE_USERNAME** - specify the username of **database** user to connect<br>
**MIGRATIONS_ENABLE** - allows to turn on/off database migrations process. Any value instead of "true" will be recognised as "false"<br>
**JSON_GOOGLE_CREDENTIALS** - json file with Google credentials, needed to access calendar api<br>
**FIREBASE_SA_JSON** - credentials json file from Firebase service account<br>
**APPLICATION_URL** - actual url address of application itself. Must be https url. Can't be localhost.<br>
**LOG_LEVEL** - logging level in application. Used in logback.xml. Default value: debug<br>
**DEFAULT_CALENDAR** - default Google calendar, used for booking meeting rooms. If not defined value from the config file will be used instead.
**WORKSPACE_CALENDAR** - Google calendar for booking working places. If not defined value from the config file will be used instead.

You may use file .env.example as an example.

## Authors: :writing_hand:

- [Daniil Zavyalov](https://github.com/zavyalov-daniil)
- [Danil Kiselev](https://github.com/kiselev-danil)
- [Egor Parkhomenko](https://github.com/1MPULSEONE)
