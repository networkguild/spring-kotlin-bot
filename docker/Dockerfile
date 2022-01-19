FROM laurihuotari/openjdk-alpine:17

RUN adduser -u 69 -D bot

COPY build/libs/spring-kotlin-bot-1.0.jar /app.jar

EXPOSE 8080

USER bot

ARG GITHUB_COMMIT_SHA=na
ARG GITHUB_BUILD_NUMBER=na
ARG RELEASE_TAG=na

ENV COMMIT_SHA=${GITHUB_COMMIT_SHA}
ENV BUILD_NUMBER=${GITHUB_BUILD_NUMBER}
ENV RELEASE_TAG=${RELEASE_TAG}

ENTRYPOINT ["java","-Xms512m","-Xmx512m","-XX:+ExitOnOutOfMemoryError","-jar","/app.jar"]
