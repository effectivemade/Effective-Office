FROM gradle:7-jdk11 AS graalvm-install
WORKDIR /opt
ADD https://download.oracle.com/graalvm/17/latest/graalvm-jdk-17_linux-x64_bin.tar.gz graalvm-jdk-17_linux-x64_bin.tar.gz
RUN tar -xvf graalvm-jdk-17_linux-x64_bin.tar.gz && \
    mv graalvm-ce-java17-17.3.0 graalvm && \
    rm graalvm-jdk-17_linux-x64_bin.tar.gz

ENV JAVA_HOME=/opt/graalvm
ENV PATH=${JAVA_HOME}/bin:${PATH}

#FROM gradle:7-jdk11 AS build
FROM graalvm-jdk-17_linux-x64_bin.tar.gz AS build
COPY --from=graalvm-install /home/gradle/src
WORKDIR /home/gradle/src
RUN ./gradlew buildFatJar --no-daemon

FROM openjdk:11
EXPOSE 8080:8080
WORKDIR /app
COPY --from=build /home/gradle/src/build/libs/*.jar app.jar
ENTRYPOINT ["java","-jar","app.jar"]
# to build: docker build -t effective_office_backend .
# to run: docker run --rm -p 8080:8080 effective_office_backend