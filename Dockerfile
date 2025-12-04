FROM amazoncorretto:21-alpine-jdk
MAINTAINER asseco.de
COPY target/csv-0.0.1-SNAPSHOT.jar csv.jar
ENTRYPOINT ["java","-jar","/csv.jar"]