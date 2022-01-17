FROM laurihuotari/openjdk-alpine:17

COPY build/libs/corobot-1.0.jar /app.jar

EXPOSE 8080

ARG GITHUB_COMMIT_SHA=na
ARG GITHUB_BUILD_NUMBER=na
ARG RELEASE_TAG=master

ENV COMMIT_SHA=${COMMIT_SHA}
ENV BUILD_NUMBER=${BUILD_NUMBER}
ENV RELEASE_TAG=${RELEASE_TAG}

ENTRYPOINT ["java","-Xms512m","-Xmx512m","-XX:+ExitOnOutOfMemoryError","-jar","/app.jar"]
