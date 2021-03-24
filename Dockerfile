FROM openjdk:8-jdk-alpine
COPY target/bookstore-0.0.1.jar bookstore.jar
ENTRYPOINT ["java","-jar","/bookstore.jar"]