FROM openjdk:24
MAINTAINER asseco.de
COPY target/csv-0.0.1-SNAPSHOT.jar csv.jar
ENTRYPOINT ["java","-jar","/csv.jar"]