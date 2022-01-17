FROM laurihuotari/openjdk-alpine:17

COPY build/libs/corobot-1.0.jar /app.jar

COPY .env .

EXPOSE 8080

ARG COMMIT_SHA=na
ARG BUILD_NUMBER=na

ENV COMMIT_SHA=${COMMIT_SHA}
ENV BUILD_NUMBER=${BUILD_NUMBER}

ENTRYPOINT ["java","-Xms512m","-Xmx512m","-XX:+ExitOnOutOfMemoryError","-jar","/app.jar"]
