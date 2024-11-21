FROM gradle:7-jdk11 AS build
COPY . /home/gradle/src
WORKDIR /home/gradle/src
RUN ./gradlew buildFatJar --no-daemon

FROM openjdk:11
EXPOSE 8080:8080
WORKDIR /app
COPY --from=build /home/gradle/src/build/libs/*.jar app.jar
ENTRYPOINT ["java","-jar","app.jar"]
# to build: docker build -t effective_office_backend .
# to run: docker run --rm -p 8080:8080 effective_office_backend