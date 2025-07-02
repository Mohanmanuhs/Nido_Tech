FROM openjdk:22-jdk

WORKDIR /app

COPY /target/corpCartServer.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java","-jar","app.jar"]