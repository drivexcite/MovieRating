FROM adoptopenjdk/openjdk11:alpine-slim as build

WORKDIR /workspace/app

COPY pom.xml .
COPY src src

RUN apk add --no-cache maven
RUN mvn dependency:go-offline

COPY src/ /build/src/
RUN mvn clean package

# Run SpringBoot App
FROM adoptopenjdk/openjdk11:alpine-slim
VOLUME /tmp

ARG DEPENDENCY=/workspace/app/target
WORKDIR /app

COPY --from=build ${DEPENDENCY}/*.jar .
ENTRYPOINT java -jar movierating.backend-1.0.0.jar -Djava.security.egd=file:/dev/./urandom