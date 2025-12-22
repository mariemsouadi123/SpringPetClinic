FROM eclipse-temurin:17-jre
WORKDIR /app
COPY target/spring-petclinic-2.1.0.BUILD-SNAPSHOT.jar app.jar
EXPOSE 8085
ENTRYPOINT ["java", "-jar", "app.jar"]

