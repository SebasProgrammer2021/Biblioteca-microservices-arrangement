FROM eclipse-temurin:17
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} libros-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java", "-jar", "/libros-0.0.1-SNAPSHOT.jar"]

