FROM maven:3.9.9-eclipse-temurin-17 AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

FROM eclipse-temurin:17-jre-jammy
WORKDIR /app
COPY --from=build /app/target/lab1v20206.jar lab1v20206.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","lab1v20206.jar"]
