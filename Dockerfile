ARG BUILD_HOME=/gictask

FROM gradle:jdk21-corretto AS build-image
ARG BUILD_HOME
ENV APP_HOME=$BUILD_HOME
WORKDIR $APP_HOME
COPY --chown=gradle:gradle build.gradle settings.gradle $APP_HOME/
COPY --chown=gradle:gradle src $APP_HOME/src
RUN gradle --no-daemon build

FROM amazoncorretto:21
ARG BUILD_HOME
ENV APP_HOME=$BUILD_HOME
COPY --from=build-image $APP_HOME/build/libs/gictask-0.0.1.jar /usr/app/gictask.jar
WORKDIR /usr/app
EXPOSE 8084
ENTRYPOINT ["java", "-jar", "gictask.jar"]