FROM maven:3.6-jdk-11-slim AS build-deps
RUN mkdir -p /usr/src/app
COPY pom.xml /usr/src/app/pom.xml
WORKDIR /usr/src/app

RUN mvn -T 1C dependency:resolve dependency:resolve-plugins
RUN mvn versions:commit

COPY src /usr/src/app/src
WORKDIR /usr/src/app
ARG VERSION
ENV VERSION=$VERSION
RUN mvn versions:set -DnewVersion=$VERSION
RUN mvn -T 1C package -Dmaven.test.skip=true
#ENTRYPOINT ["tail", "-f", "/dev/null"]

# creates runtime
FROM adoptopenjdk/openjdk11:alpine-jre
ENV LANG en_US.UTF-8
#COPY src /usr/src/app/src
#WORKDIR /usr/src/app
#RUN mvn
COPY --from=build-deps /usr/src/app/target/*.jar /fuzzy-inference-systems-backend.jar
CMD java -jar -Dspring.profiles.active=prod /fuzzy-inference-systems-backend.jar