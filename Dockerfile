FROM openjdk:17
EXPOSE 8080
ADD build/libs/Prueba_Backend-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]