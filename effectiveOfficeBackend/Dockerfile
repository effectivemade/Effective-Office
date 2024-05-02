FROM gradle:7-jdk11 AS graalvm-install
WORKDIR /opt
ADD https://github.com/graalvm/graalvm-ce-builds/releases/download/vm-17.3.0/graalvm-ce-java17-windows-amd64-17.3.0.tar.gz graalvm.tar.gz
RUN tar -xvf graalvm.tar.gz && \
    mv graalvm-ce-java17-17.3.0 graalvm && \
    rm graalvm.tar.gz

ENV JAVA_HOME=/opt/graalvm
ENV PATH=${JAVA_HOME}/bin:${PATH}

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