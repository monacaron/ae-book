FROM openjdk:8
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} app.jar
EXPOSE 8082
ENTRYPOINT java -jar -Dspring.profiles.active=dev /app.jar
