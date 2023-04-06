FROM maven:3.8.1-openjdk-11 AS build

WORKDIR /app

COPY pom.xml ./
COPY src ./src

RUN mvn clean package -DskipTests

FROM openjdk:11

COPY --from=build /app/target/*.jar app.jar

ENTRYPOINT ["java", "-jar", "/app.jar"]
